package ru.gt2.gar.parse;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.task.AsyncTaskExecutor;
import ru.gt2.gar.domain.Gar;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.parse.consumer.DurationFmt;
import ru.gt2.gar.parse.consumer.EntityStats;
import ru.gt2.gar.parse.xml.AllXMLProcessors;
import ru.gt2.gar.parse.xml.XMLStreamProcessor;
import ru.gt2.gar.parse.zip.FileStats;
import ru.gt2.gar.parse.zip.GarEntry;
import ru.gt2.gar.parse.zip.GarZipFile;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/// Вывод статистики по архиву ГАР.
@Slf4j
@SpringBootApplication
public class DumpXMLStatsApp implements CommandLineRunner {

    private final AllXMLProcessors xmlProcessors;

    private final AsyncTaskExecutor taskExecutor;

    private final File zipFile;

    private final Map<GarType, EntityStats> stats = new ConcurrentHashMap<>(); // new HashMap<>();

    public DumpXMLStatsApp(AllXMLProcessors xmlProcessors, AsyncTaskExecutor taskExecutor,
                           @Value("${gar.zip.full}") File file) {
        this.xmlProcessors = xmlProcessors;
        this.taskExecutor = taskExecutor;
        this.zipFile = file;
    }

    public static void main(String... args) {
        SpringApplication.run(DumpXMLStatsApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try (GarZipFile garZipFile = new GarZipFile(zipFile)) {
            garZipFile.getVersion().ifPresentOrElse(
                    v -> log.info("Gar file date: {} ({} day(s) ago), version: {}",
                            v.date(), ChronoUnit.DAYS.between(v.date(), LocalDate.now()), v.number()),
                    () -> log.warn("Gar file does not contains version information"));

            // Однако да, один только распакованный файл будет весить 361 Гб.
            /* garZipFile.getStats().forEach((garType, fileStats) ->
                    System.out.printf("%25s %3d %14d%n", garType.name(), fileStats.count(), fileStats.size()));
            System.out.println();*/

            Stopwatch stopwatch = Stopwatch.createStarted();
            // simpleProcess(garZipFile);
            // processSerial(garZipFile);
            processParallel(garZipFile);
            dumpStats(garZipFile);
            System.out.printf("Total time elapsed: %s%n", stopwatch);
        } catch (Exception e) {
            log.warn("Unable to parse gar zip file: {}", zipFile, e);
        }
    }

    // Последовательная обработка каждого индивидуального файла
    private void processSerial(GarZipFile garZipFile) {
        garZipFile.streamEntries().forEach(garEntry -> processGarEntry(garEntry, garZipFile));
    }

    private void dumpStats(GarZipFile garZipFile) {
        // Печать итоговой статистики
        for (GarType garType : GarType.values()) {
            FileStats fileStats = garZipFile.getStats().get(garType);
            EntityStats garStats = stats.get(garType);
            System.out.printf("%s, file(s) count %d, file(s) size %,d, total record(s): %,d, elapsed: %s%n",
                    garType.name(), fileStats.count(), fileStats.size(), garStats.getCount(),
                    DurationFmt.format(garStats.getDuration()));
            garStats.getFieldStats().forEach(fs -> System.out.println("  " + fs));
        }
    }

    // Параллельная обработка файлов
    private void processParallel(GarZipFile garZipFile) {
        List<CompletableFuture<Void>> list = garZipFile.streamEntries()
                .map(garEntry -> taskExecutor.submitCompletable(() -> processGarEntry(garEntry, garZipFile)))
                .toList();
        log.info("Finished adding tasks");
        list.forEach(CompletableFuture::join);
        log.info("Finished join on all tasks");
    }

    private void processGarEntry(GarEntry garEntry, GarZipFile garZipFile) {
        log.info("Starting processing of file: {}", garEntry);
        GarType garType;
        try {
            garType = GarType.valueOf(garEntry.name());
        } catch (IllegalArgumentException e) {
            log.warn("Unknown gar type: {}", garEntry.name());
            return;
        }

        XMLStreamProcessor processor = xmlProcessors.getProcessor(garType);
        EntityStats fileStats = new EntityStats();
        try (InputStream inputStream = garZipFile.getInputStream(garEntry)) {
            processor.process(inputStream, fileStats);

            String nameWithDir = garEntry.nameWithDir();
            int pad = Gar.MAX_NAME_LENGTH + 3 - nameWithDir.length();
            if (pad > 0) {
                nameWithDir += " ".repeat(pad);
            }
            int recordCount = fileStats.getCount();
            String duration = DurationFmt.format(fileStats.getDuration());
            if (recordCount > 0) {
                stats.merge(garType, fileStats, EntityStats::sum);
                log.info("Finished processing of file: {} elapsed: {}, record(s): {}", //  (~ {} byte(s) per record)
                        nameWithDir, duration, recordCount); // , garEntry.size() / recordCount
            } else {
                log.info("Finished processing of file: {} elapsed: {}, no records",
                        nameWithDir, duration);
            }
        } catch (Exception e) {
            log.warn("Unable to parse entry: {}", garEntry, e);
        }
    }

    private void simpleProcess(GarZipFile garZipFile) {
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
    }

    private static<T extends Record> void process(GarZipFile garZipFile, XMLStreamProcessor processor) {
        EntityStats garStats = new EntityStats();

        GarType garType = processor.getGarType();
        String garTypeName = garType.name();
        FileStats fileStats = garZipFile.getStats().get(garType);
        System.out.printf("%s, file(s) count %d, file(s) size %,d, ", garTypeName, fileStats.count(), fileStats.size());
        garZipFile.streamEntries()
                .filter(ge -> ge.name().equals(garTypeName))
                .forEach(ge -> {
                    try (var is = garZipFile.getInputStream(ge)) {
                        processor.process(is, garStats);
                    } catch (Exception e) {
                        log.warn("Unable to parse entry: {}", ge, e);
                    }
                });

        System.out.printf("total record(s): %,d, elapsed: %s%n", garStats.getCount(),
                DurationFmt.format(garStats.getDuration()));
        garStats.getFieldStats().forEach(fs -> System.out.println("  " + fs));
    }
}
