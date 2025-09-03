package ru.gt2.gar.parse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gt2.gar.parse.xml.XMLStreamParser;
import ru.gt2.gar.parse.zip.ZipFileReader;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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

        ZipFileReader zipFileReader = new ZipFileReader("C:/Gar/gar_xml_2025-08-29.zip");
        zipFileReader.process();
        System.out.println(zipFileReader.getVersion());
        System.out.println(zipFileReader.getGarNames());
    }
}
