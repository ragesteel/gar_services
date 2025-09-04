package ru.gt2.gar.parse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gt2.gar.parse.domain.AddressObject;
import ru.gt2.gar.parse.domain.AddressObjectDivision;
import ru.gt2.gar.parse.domain.AddressObjectType;
import ru.gt2.gar.parse.domain.AdmHierarchy;
import ru.gt2.gar.parse.domain.Apartment;
import ru.gt2.gar.parse.domain.ApartmentType;
import ru.gt2.gar.parse.domain.OperationType;
import ru.gt2.gar.parse.rest.FileInfoService;
import ru.gt2.gar.parse.xml.ListCounter;
import ru.gt2.gar.parse.xml.XMLStreamProcessor;
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
        ListCounter<AddressObject> aoCounter = new ListCounter<>();
        XMLStreamProcessor<AddressObject> aoProcessor = XMLStreamProcessor.forAddressObject(batchSize);

        ListCounter<AddressObjectDivision> aodCounter = new ListCounter<>();
        XMLStreamProcessor<AddressObjectDivision> aodProcessor = XMLStreamProcessor.forAddressObjectDivision(batchSize);

        ListCounter<AddressObjectType> aotCounter = new ListCounter<>();
        XMLStreamProcessor<AddressObjectType> aotProcessor = XMLStreamProcessor.forAddressObjectType(batchSize);

        ListCounter<AdmHierarchy> ahCounter = new ListCounter<>();
        XMLStreamProcessor<AdmHierarchy> ahProcessor = XMLStreamProcessor.forAdmHierarchy(batchSize);

        ListCounter<ApartmentType> atCounter = new ListCounter<>();
        XMLStreamProcessor<ApartmentType> atProcessor = XMLStreamProcessor.forApartmentType(batchSize);

        ListCounter<Apartment> aCounter = new ListCounter<>();
        XMLStreamProcessor<Apartment> aProcessor = XMLStreamProcessor.forApartment(batchSize);

        ListCounter<OperationType> otCounter = new ListCounter<>();
        XMLStreamProcessor<OperationType> otProcessor = XMLStreamProcessor.forOperationType(batchSize);

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

        System.out.println(fileInfoService.getLast());

        // Сначала разбираем файлы из корневого каталога — справочники по сути;
        process(garZipFile, atProcessor, atCounter);
        process(garZipFile, aotProcessor, aotCounter);
        process(garZipFile, otProcessor, otCounter);

        // Потом идём уже по регионам
        process(garZipFile, aoProcessor, aoCounter);
        process(garZipFile, aodProcessor, aodCounter);
        process(garZipFile, ahProcessor, ahCounter);
        process(garZipFile, aProcessor, aCounter);
    }

    private static<T> void process(GarZipFile garZipFile, XMLStreamProcessor<T> aodProcesser, ListCounter<T> aodCounter) {
        String garTypeName = aodProcesser.getGarType().name();
        garZipFile.stream()
                .filter(ge -> ge.name().equals(garTypeName))
                .forEach(ge -> {
                    try (var is = garZipFile.getInputStream(ge)) {
                        aodProcesser.process(is, aodCounter);
                    } catch (Exception e) {
                        log.warn("Unable to parse entry: " + ge, e);
                    }
                });
        log.info("Total {} items read: {}", garTypeName, aodCounter.getCounter());
    }
}
