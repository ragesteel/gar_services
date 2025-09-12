package ru.gt2.gar.parse;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gt2.gar.domain.AddressObject;
import ru.gt2.gar.domain.AddressObjectDivision;
import ru.gt2.gar.domain.AddressObjectType;
import ru.gt2.gar.domain.AdmHierarchy;
import ru.gt2.gar.domain.Apartment;
import ru.gt2.gar.domain.ApartmentType;
import ru.gt2.gar.domain.CarPlace;
import ru.gt2.gar.domain.ChangeHistory;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.domain.House;
import ru.gt2.gar.domain.HouseType;
import ru.gt2.gar.domain.MunHierarchy;
import ru.gt2.gar.domain.NormativeDoc;
import ru.gt2.gar.domain.NormativeDocKind;
import ru.gt2.gar.domain.NormativeDocType;
import ru.gt2.gar.domain.ObjectLevel;
import ru.gt2.gar.domain.OperationType;
import ru.gt2.gar.domain.Param;
import ru.gt2.gar.domain.ParamType;
import ru.gt2.gar.domain.ReestrObject;
import ru.gt2.gar.domain.Room;
import ru.gt2.gar.domain.RoomType;
import ru.gt2.gar.domain.Stead;
import ru.gt2.gar.parse.consumer.DurationFmt;
import ru.gt2.gar.parse.consumer.EntityStats;
import ru.gt2.gar.parse.rest.FileInfoService;
import ru.gt2.gar.parse.xml.XMLStreamProcessor;
import ru.gt2.gar.parse.zip.FileStats;
import ru.gt2.gar.parse.zip.GarZipFile;

@Slf4j
@SpringBootApplication
public class ParseApplication implements CommandLineRunner {
    @Value("${gar.parse.batch:1000}")
    private int batchSize;

    @Autowired
    private FileInfoService fileInfoService;

    public static void main(String... args) {
        SpringApplication.run(ParseApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        XMLStreamProcessor<AddressObject> aoProcessor = XMLStreamProcessor.forAddressObject(batchSize);
        XMLStreamProcessor<AddressObjectDivision> aodProcessor = XMLStreamProcessor.forAddressObjectDivision(batchSize);
        XMLStreamProcessor<AddressObjectType> aotProcessor = XMLStreamProcessor.forAddressObjectType(batchSize);
        XMLStreamProcessor<AdmHierarchy> ahProcessor = XMLStreamProcessor.forAdmHierarchy(batchSize);
        XMLStreamProcessor<ApartmentType> atProcessor = XMLStreamProcessor.forApartmentType(batchSize);
        XMLStreamProcessor<Apartment> aProcessor = XMLStreamProcessor.forApartment(batchSize);
        XMLStreamProcessor<OperationType> otProcessor = XMLStreamProcessor.forOperationType(batchSize);
        XMLStreamProcessor<CarPlace> cpProcessor = XMLStreamProcessor.forCarPlace(batchSize);
        XMLStreamProcessor<ChangeHistory> chProcessor = XMLStreamProcessor.forChangeHistory(batchSize);
        XMLStreamProcessor<HouseType> htProcessor = XMLStreamProcessor.forHouseType(batchSize);
        XMLStreamProcessor<House> hProcessor = XMLStreamProcessor.forHouse(batchSize);
        XMLStreamProcessor<MunHierarchy> mhProcessor = XMLStreamProcessor.forMunHierarchy(batchSize);
        XMLStreamProcessor<NormativeDocType> ndtProcessor = XMLStreamProcessor.forNormativeDocType(batchSize);
        XMLStreamProcessor<NormativeDocKind> ndkProcessor = XMLStreamProcessor.forNormativeDocKind(batchSize);
        XMLStreamProcessor<NormativeDoc> ndProcessor = XMLStreamProcessor.forNormativeDoc(batchSize);
        XMLStreamProcessor<ObjectLevel> olProcessor = XMLStreamProcessor.forObjectLevel(batchSize);
        XMLStreamProcessor<ParamType> ptProcessor = XMLStreamProcessor.forParamType(batchSize);
        XMLStreamProcessor<Param> aopProcessor = XMLStreamProcessor.forAddrObjParam(batchSize);
        XMLStreamProcessor<Param> hpProcessor = XMLStreamProcessor.forHousesParam(batchSize);
        XMLStreamProcessor<Param> apProcessor = XMLStreamProcessor.forApartmentsParam(batchSize);
        XMLStreamProcessor<Param> rpProcessor = XMLStreamProcessor.forRoomsParam(batchSize);
        XMLStreamProcessor<Param> spProcessor = XMLStreamProcessor.forSteadsParam(batchSize);
        XMLStreamProcessor<Param> cppProcessor = XMLStreamProcessor.forCarPlacesParam(batchSize);
        XMLStreamProcessor<ReestrObject> roProcessor = XMLStreamProcessor.forReestrObject(batchSize);
        XMLStreamProcessor<RoomType> rtProcessor = XMLStreamProcessor.forRoomType(batchSize);
        XMLStreamProcessor<Room> rProcessor = XMLStreamProcessor.forRoom(batchSize);
        XMLStreamProcessor<Stead> sProcessor = XMLStreamProcessor.forStead(batchSize);
        XMLStreamProcessor<HouseType> ahtProcessor = XMLStreamProcessor.forAddHouseType(batchSize);

        /*
        try (InputStream inputStream =
                     Files.newInputStream(Paths.get("C:/Tmp/AS_ADDR_OBJ_20250902_07bcc4ec-d701-4cee-8326-bc0353ae95bd.XML"))) {
            aoProcessor.process(inputStream, aoCounter);
        }*/

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
        process(garZipFile, atProcessor);
        process(garZipFile, aotProcessor);
        process(garZipFile, otProcessor);
        process(garZipFile, htProcessor);
        process(garZipFile, ndkProcessor);
        process(garZipFile, ndtProcessor);
        process(garZipFile, olProcessor);
        process(garZipFile, ptProcessor);
        process(garZipFile, rtProcessor);
        process(garZipFile, ahtProcessor);

        // Потом идём уже по регионам
        process(garZipFile, aoProcessor);
        process(garZipFile, aodProcessor);
        process(garZipFile, ahProcessor);
        process(garZipFile, aProcessor);
        process(garZipFile, cpProcessor);
        process(garZipFile, chProcessor);
        process(garZipFile, hProcessor);
        process(garZipFile, mhProcessor);
        process(garZipFile, ndProcessor);
        process(garZipFile, aopProcessor);
        process(garZipFile, hpProcessor);
        process(garZipFile, apProcessor);
        process(garZipFile, rpProcessor);
        process(garZipFile, spProcessor);
        process(garZipFile, cppProcessor);
        process(garZipFile, roProcessor);
        process(garZipFile, rProcessor);
        process(garZipFile, sProcessor);
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
        stats.getFieldStats().forEach(fs -> {
            System.out.println("  " + fs);
        });
    }
}
