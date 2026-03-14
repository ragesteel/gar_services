package ru.gt2.gar.parse;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.task.AsyncTaskExecutor;
import ru.gt2.gar.domain.Gar;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.parse.consumer.DurationFmt;
import ru.gt2.gar.parse.consumer.EntityStats;
import ru.gt2.gar.parse.xml.XMLProcessors;
import ru.gt2.gar.parse.xml.XMLStreamProcessor;
import ru.gt2.gar.parse.zip.FileStats;
import ru.gt2.gar.parse.zip.GarEntry;
import ru.gt2.gar.parse.zip.GarZipFile;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/// Вывод статистики по архиву ГАР.
@Slf4j
@SpringBootApplication
public class DumpXMLStatsApp implements CommandLineRunner {

    private final XMLProcessors xmlProcessors;

    // Это будет Bean с именем applicationTaskExecutor, который создаётся Spring'ом.
    // https://docs.spring.io/spring-boot/reference/features/task-execution-and-scheduling.html
    private final AsyncTaskExecutor taskExecutor;

    private final File zipFile;
    private final int entitySizeLimit;
    private final ProcessingMode processingMode;

    private final Map<GarType, EntityStats> stats = new ConcurrentHashMap<>(); // new HashMap<>();

    public DumpXMLStatsApp(XMLProcessors xmlProcessors, AsyncTaskExecutor taskExecutor,
                           @Value("${gar.zip.full}") File file,
                           @Value("${gar.xml.entitySizeLimit:-1}") int entitySizeLimit,
                           @Value("${gar.processing.mode:PARALLEL}") ProcessingMode processingMode) {
        this.xmlProcessors = xmlProcessors;
        this.taskExecutor = taskExecutor;
        this.zipFile = file;
        this.entitySizeLimit = entitySizeLimit;
        this.processingMode = processingMode;
    }

    static void main(String... args) {
        SpringApplication.run(DumpXMLStatsApp.class, args);
    }

    @NullMarked
    @Override
    public void run(String... args) {
        try (GarZipFile garZipFile = new GarZipFile(zipFile)) {
            garZipFile.getVersion().ifPresentOrElse(
                    v -> log.info("Gar file date: {} ({} day(s) ago), version: {}",
                            v.date(), ChronoUnit.DAYS.between(v.date(), LocalDate.now()), v.number()),
                    () -> log.warn("Gar file does not contains version information"));

            Stopwatch stopwatch = Stopwatch.createStarted();
            switch (processingMode) {
                case SIMPLE -> simpleProcess(garZipFile);
                case SERIAL -> processSerial(garZipFile);
                case PARALLEL -> processParallel(garZipFile);
            }
            dumpStats(garZipFile);
            // TODO Добавить общую статистику по файлам, общий размер, количество записей и время обработки!
            // Хотя нет, вот как раз время сравнивать не нужно!
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
            if (null == garStats) {
                System.out.printf("%s: file(s) count %d; file(s) size %,d; no stats%n",
                        garType.name(), fileStats.count(), fileStats.size());
                continue;
            }
            System.out.printf("%s: file(s) count %d; file(s) size %,d; total record(s): %,d; elapsed: %s%n",
                    garType.name(), fileStats.count(), fileStats.size(), garStats.getCount(),
                    DurationFmt.format(garStats.getDuration()));
            dumpFieldStats(garStats);
        }
    }

    // Параллельная обработка файлов
    private void processParallel(GarZipFile garZipFile) {
        // IDEA возможно в одну задачу запихивать несколько файлов, пока сумма пачки не превысит 1 Мб к примеру.
        //  Но, не факт, что это поможет, всё-таки всего меньше 2000 файлов, а это не такие большие накладные расходы.
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

        XMLStreamProcessor processor = xmlProcessors.get(garType);
        EntityStats fileStats = new EntityStats();
        try (InputStream inputStream = garZipFile.getInputStream(garEntry)) {
            processor.process(inputStream, fileStats, entitySizeLimit);

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
        List<GarType> rootFirst = new ArrayList<>(GarType.ROOT_REFS);
        rootFirst.addAll(GarType.REGIONAL_DATA);
        rootFirst.forEach(gt -> simpleProcess(garZipFile, xmlProcessors.get(gt)));
    }

    private void simpleProcess(GarZipFile garZipFile, XMLStreamProcessor processor) {
        EntityStats garStats = new EntityStats();

        GarType garType = processor.getGarType();
        String garTypeName = garType.name();
        FileStats fileStats = garZipFile.getStats().get(garType);
        System.out.printf("%s, file(s) count %d, file(s) size %,d, ", garTypeName, fileStats.count(), fileStats.size());
        garZipFile.streamEntries()
                .filter(ge -> ge.name().equals(garTypeName))
                .forEach(ge -> {
                    try (var is = garZipFile.getInputStream(ge)) {
                        processor.process(is, garStats, entitySizeLimit);
                    } catch (Exception e) {
                        log.warn("Unable to parse entry: {}", ge, e);
                    }
                });
        stats.merge(garType, garStats, EntityStats::sum);

        System.out.printf("total record(s): %,d, elapsed: %s%n", garStats.getCount(),
                DurationFmt.format(garStats.getDuration()));
        dumpFieldStats(garStats);
    }

    private static void dumpFieldStats(EntityStats garStats) {
        garStats.getFieldStats().forEach(fs -> System.out.println("  " + fs.formatString()));
    }
}
