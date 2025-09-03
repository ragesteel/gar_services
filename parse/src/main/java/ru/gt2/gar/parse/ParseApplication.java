package ru.gt2.gar.parse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gt2.gar.parse.domain.AddressObjectDivision;
import ru.gt2.gar.parse.domain.GarTypes;
import ru.gt2.gar.parse.xml.ListCounter;
import ru.gt2.gar.parse.xml.XMLStreamMapper;
import ru.gt2.gar.parse.xml.XMLStreamParser;
import ru.gt2.gar.parse.zip.GarZipFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@SpringBootApplication
public class ParseApplication implements CommandLineRunner {
    @Autowired
    private XMLStreamParser xmlStreamParser;

    public static void main(String... args) {
        SpringApplication.run(ParseApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try (InputStream inputStream =
                     Files.newInputStream(Paths.get("C:/Tmp/AS_ADDR_OBJ_20250902_07bcc4ec-d701-4cee-8326-bc0353ae95bd.XML"))) {
            xmlStreamParser.parse(inputStream);
        }

        GarZipFile garZipFile = new GarZipFile("C:/Gar/gar_xml_2025-08-29.zip");
        garZipFile.getVersion().ifPresentOrElse(
                v -> log.info("Gar file date: {}, version: {}", v.date(), v.number()),
                () -> log.warn("Gar file does not contains version information"));

        // Однако да, один только распакованный файл будет весить 361 Гб.
        garZipFile.getStats().forEach((garTypes, fileStats) -> {
            System.out.printf("%25s %3d %14d%n", garTypes.name(), fileStats.count(), fileStats.size());
        });
        System.out.println();

        ListCounter<AddressObjectDivision> aodCounter = new ListCounter<>();
        XMLStreamMapper<AddressObjectDivision> addressObjectDivisionMapper = XMLStreamMapper.forAddressObjectDivision();
        garZipFile.stream()
                .filter(ge -> ge.name().equals(GarTypes.ADDR_OBJ_DIVISION.name()))
                .forEach(ge -> {
                    try (var is = garZipFile.getInputStream(ge)) {
                        addressObjectDivisionMapper.process(is, aodCounter);
                    } catch (Exception e) {
                        log.warn("Unable to parse entry: " + ge, e);
                    }
                });
        log.warn("Total ADDR_OBJ_DIVISION items read: {}", aodCounter.getCounter());

        garZipFile.stream()
                .filter(ge -> ge.name().equals(GarTypes.ADDR_OBJ.name()))
                .forEach(ge -> {
                    try (var is = garZipFile.getInputStream(ge)) {
                        xmlStreamParser.parse(is);
                    } catch (Exception e) {
                        log.warn("Unable to parse entry: " + ge, e);
                    }
                });
        log.info("Total ADDR_OBJ read: {}", xmlStreamParser.getTotalRead());
    }
}
