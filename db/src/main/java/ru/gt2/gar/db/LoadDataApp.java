package ru.gt2.gar.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.parse.xml.AllXMLProcessors;
import ru.gt2.gar.parse.xml.XMLStreamProcessor;
import ru.gt2.gar.parse.zip.GarEntry;
import ru.gt2.gar.parse.zip.GarZipFile;

import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/// Загрузка данных в базу
@SpringBootApplication
@Slf4j
public class LoadDataApp implements CommandLineRunner {
    private final AllXMLProcessors xmlProcessors;
    private final DataSource dataSource;
    private final File zipFile;
    private final GarDataWriter garDataWriter;

    public LoadDataApp(AllXMLProcessors xmlProcessors, DataSource dataSource,
                       @Value("${gar.zip.full}") File zipFile, GarDataWriter garDataWriter) {

        this.xmlProcessors = xmlProcessors;
        this.dataSource = dataSource;
        this.zipFile = zipFile;
        this.garDataWriter = garDataWriter;
    }

    public static void main(String... args) {
        SpringApplication.run(LoadDataApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO Сначала сделать загрузку для одной таблицы, из корневых справочников
        try (GarZipFile garZipFile = new GarZipFile(zipFile)) {
            garZipFile.getVersion().ifPresentOrElse(
                    v -> log.info("Gar file date: {} ({} day(s) ago), version: {}",
                            v.date(), ChronoUnit.DAYS.between(v.date(), LocalDate.now()), v.number()),
                    () -> log.warn("Gar file does not contains version information"));

            garZipFile.streamEntries().forEach(garEntry -> processGarEntry(garEntry, garZipFile));
        }
            // TODO Потом — всех корневых справочников
        // TODO И наконец — всех данных по регионам
        // TODO Сохранение данных вынести в отдельный класс с интерфейсом
        //  и реализациями на BatchPreparedStatement, UNNEST и COPY
    }

    private void processGarEntry(GarEntry garEntry, GarZipFile garZipFile) {
        GarType garType = GarType.ADDR_OBJ_TYPES;
        if (!garType.name().equals(garEntry.name())) {
            return;
        }

        XMLStreamProcessor processor = xmlProcessors.getProcessor(garType);
        try (InputStream inputStream = garZipFile.getInputStream(garEntry)) {
            processor.process(inputStream, ge -> garDataWriter.writeEntities(garType, ge));
        } catch (Exception e) {
            throw new RuntimeException("Unable to process " + garEntry, e);
        }
    }
}
