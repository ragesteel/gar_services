package ru.gt2.gar.parse;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gt2.gar.parse.domain.GarTypes;
import ru.gt2.gar.parse.xml.XMLStreamParser;
import ru.gt2.gar.parse.zip.GarEntry;
import ru.gt2.gar.parse.zip.GarZipFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.stream.Collectors;

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

        Multiset<String> garNames = garZipFile.stream().collect(Multisets.toMultiset(GarEntry::name, ge -> 1, HashMultiset::create));
        System.out.println("Version: " + garZipFile.getVersion());

        EnumSet<GarTypes> enumSet = EnumSet.allOf(GarTypes.class);

        garNames.forEach(gn -> {
            String name = gn.substring(3);
            GarTypes garType = GarTypes.valueOf(name);
            enumSet.remove(garType);
        });
        if (!enumSet.isEmpty()) {
            System.out.println("Not used: " + enumSet);
        }
    }
}
