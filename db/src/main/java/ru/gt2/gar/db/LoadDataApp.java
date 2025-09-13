package ru.gt2.gar.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gt2.gar.parse.xml.AllXMLProcessors;

import java.io.File;

/// Загрузка данных в базу
@SpringBootApplication
public class LoadDataApp implements CommandLineRunner {
    private final AllXMLProcessors xmlProcessors;

    private final File zipFile;

    public LoadDataApp(AllXMLProcessors xmlProcessors,
                       @Value("${gar.zip.full}") File zipFile) {

        this.xmlProcessors = xmlProcessors;
        this.zipFile = zipFile;
    }

    public static void main(String... args) {
        SpringApplication.run(LoadDataApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
