package ru.gt2.gar.parse;

import com.google.common.base.Stopwatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.parse.consumer.DurationFmt;
import ru.gt2.gar.parse.consumer.EntityStats;
import ru.gt2.gar.parse.rest.FileInfoService;
import ru.gt2.gar.parse.xml.AllXMLProcessors;
import ru.gt2.gar.parse.xml.XMLStreamProcessor;
import ru.gt2.gar.parse.zip.FileStats;
import ru.gt2.gar.parse.zip.GarZipFile;

// TODO Переименовать в DumpXMLStatsApp и сделать входной файл — параметром в application.yml
@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class ParseApplication implements CommandLineRunner {

    private final FileInfoService fileInfoService;

    private final AllXMLProcessors xmlProcessors;

    public static void main(String... args) {
        SpringApplication.run(ParseApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        GarZipFile garZipFile = new GarZipFile("C:/Gar/gar_xml_2025-08-29.zip");
        garZipFile.getVersion().ifPresentOrElse(
                v -> log.info("Gar file date: {}, version: {}", v.date(), v.number()),
                () -> log.warn("Gar file does not contains version information"));

        // Однако да, один только распакованный файл будет весить 361 Гб.
        garZipFile.getStats().forEach((garType, fileStats) ->
                System.out.printf("%25s %3d %14d%n", garType.name(), fileStats.count(), fileStats.size()));
        System.out.println();

        Stopwatch stopwatch = Stopwatch.createStarted();
        // Сначала разбираем файлы из корневого каталога — справочники по сути;
        process(garZipFile, xmlProcessors.apartmentType);
        process(garZipFile, xmlProcessors.addressObjectType);
        process(garZipFile, xmlProcessors.operationType);
        process(garZipFile, xmlProcessors.houseType);
        process(garZipFile, xmlProcessors.normativeDocKind);
        process(garZipFile, xmlProcessors.normativeDocType);
        process(garZipFile, xmlProcessors.objectLevel);
        process(garZipFile, xmlProcessors.paramType);
        process(garZipFile, xmlProcessors.roomType);
        process(garZipFile, xmlProcessors.houseType);

        // Потом идём уже по регионам
        process(garZipFile, xmlProcessors.addressObject);
        process(garZipFile, xmlProcessors.addressObjectDivision);
        process(garZipFile, xmlProcessors.admHierarchy);
        process(garZipFile, xmlProcessors.apartment);
        process(garZipFile, xmlProcessors.carPlace);
        process(garZipFile, xmlProcessors.changeHistory);
        process(garZipFile, xmlProcessors.house);
        process(garZipFile, xmlProcessors.munHierarchy);
        process(garZipFile, xmlProcessors.normativeDoc);
        process(garZipFile, xmlProcessors.addrObjParam);
        process(garZipFile, xmlProcessors.housesParam);
        process(garZipFile, xmlProcessors.apartmentsParam);
        process(garZipFile, xmlProcessors.roomsParam);
        process(garZipFile, xmlProcessors.steadsParam);
        process(garZipFile, xmlProcessors.carPlacesParam);
        process(garZipFile, xmlProcessors.reestrObject);
        process(garZipFile, xmlProcessors.room);
        process(garZipFile, xmlProcessors.stead);
        System.out.printf("Total time elapsed: %s%n", stopwatch);

        System.out.println(fileInfoService.getLast());
    }

    private static<T extends Record> void process(GarZipFile garZipFile, XMLStreamProcessor<T> processor) {
        EntityStats<T> stats = new EntityStats<>();

        GarType garType = processor.getGarType();
        String garTypeName = garType.name();
        FileStats fileStats = garZipFile.getStats().get(garType);
        System.out.printf("%s, file(s) count %d, file(s) size %d, ", garTypeName, fileStats.count(), fileStats.size());
        garZipFile.streamEntries()
                .filter(ge -> ge.name().equals(garTypeName))
                .forEach(ge -> {
                    try (var is = garZipFile.getInputStream(ge)) {
                        processor.process(is, stats);
                    } catch (Exception e) {
                        log.warn("Unable to parse entry: {}", ge, e);
                    }
                });

        System.out.printf("total record(s): %d, elapsed: %s%n", stats.getCount(),
                DurationFmt.format(stats.getDuration()));
        stats.getFieldStats().forEach(fs -> System.out.println("  " + fs));
    }
}
