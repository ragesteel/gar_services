package ru.gt2.gar.parse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gt2.gar.parse.xml.XmlStreamParser;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class ParseApplication implements CommandLineRunner {
    @Autowired
    private XmlStreamParser xmlStreamParser;

    public static void main(String... args) {
        SpringApplication.run(ParseApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try (InputStream inputStream =
                     Files.newInputStream(Paths.get("C:/Tmp/AS_ADDR_OBJ_20250902_07bcc4ec-d701-4cee-8326-bc0353ae95bd.XML"))) {
            xmlStreamParser.parse(inputStream);
        }
    }
}
