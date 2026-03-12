package ru.gt2.gar.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.parse.xml.XMLProcessors;
import ru.gt2.gar.parse.xml.XMLStreamProcessor;
import ru.gt2.gar.parse.zip.GarEntry;
import ru.gt2.gar.parse.zip.GarZipFile;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/// Загрузка данных в базу
@SpringBootApplication
@Slf4j
public class LoadDataApp implements CommandLineRunner {
    private final XMLProcessors xmlProcessors;
    private final File zipFile;
    private final GarDataWriter garDataWriter;
    private final int entitySizeLimit;

    public LoadDataApp(XMLProcessors xmlProcessors,
                       @Value("${gar.zip.full}") File zipFile, GarDataWriter garDataWriter,
                       @Value("${gar.xml.entitySizeLimit:-1}") int entitySizeLimit) {

        this.xmlProcessors = xmlProcessors;
        this.zipFile = zipFile;
        this.garDataWriter = garDataWriter;
        this.entitySizeLimit = entitySizeLimit;
    }

    static void main(String... args) {
        SpringApplication.run(LoadDataApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO Вынести методы параллельной обработки в отдельные классы, чтобы тут тоже можно было их применить
        // TODO Вынести обработку архива, чтобы можно было не только архив, но и просто каталог с файлами обрабатывать
        try (GarZipFile garZipFile = new GarZipFile(zipFile)) {
            garZipFile.getVersion().ifPresentOrElse(
                    v -> log.info("Gar file date: {} ({} day(s) ago), version: {}",
                            v.date(), ChronoUnit.DAYS.between(v.date(), LocalDate.now()), v.number()),
                    () -> log.warn("Gar file does not contains version information"));

            garZipFile.streamEntries().forEach(garEntry -> processGarEntry(garEntry, garZipFile));
        }
        // TODO И наконец — всех данных по регионам
        // TODO Сохранение данных вынести в отдельный класс с интерфейсом
        //  и реализациями на BatchPreparedStatement, UNNEST и COPY
    }

    private void processGarEntry(GarEntry garEntry, GarZipFile garZipFile) {
        GarType garType = GarType.valueOf(garEntry.name());
        if (!(GarType.ROOT_REFS.contains(garType) || garEntry.dir().stream().anyMatch(s -> s.equals("87")))) {
            return;
        }
        // TODO Вернуть обработку PARAMS, сейчас там проблема из-за колонки с именем value
//        if (GarType.ADDR_OBJ_PARAMS.equals(garType) | GarType.STEADS_PARAMS.equals(garType) | GarType.HOUSES_PARAMS.equals(garType) | GarType.APARTMENTS_PARAMS.equals(garType) | GarType.ROOMS_PARAMS.equals(garType)) {
//            return;
//        }
        XMLStreamProcessor processor = xmlProcessors.get(garType);
        try (InputStream inputStream = garZipFile.getInputStream(garEntry)) {
            processor.process(inputStream, ge -> garDataWriter.writeEntities(garType, ge),
                    entitySizeLimit);
            log.info("Processed: {}", garEntry);
        } catch (Exception e) {
            throw new RuntimeException("Unable to process " + garEntry, e);
        }
    }
}
