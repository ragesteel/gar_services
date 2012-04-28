package ru.gt2.rusref.fias;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import ru.gt2.rusref.CsvWriter;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Основной метод импорта.
 */
public class Main {
    private static CsvWriter REPORT;

    public static void main(String... args) throws JAXBException, IOException {
        // FIXME Добавить Google Guice для dependency injection.
        Properties properties = new Properties();
        URL propertieResource = Thread.currentThread().getContextClassLoader().getResource("fias.properties");
        Preconditions.checkNotNull(propertieResource, "Unable to find fias.properties file");
        InputStream propertiesStream = propertieResource.openStream();
        properties.load(propertiesStream);
        String input = properties.getProperty("input");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(input), "Input property must be set");
        File inputFile = new File(input);
        Preconditions.checkArgument(inputFile.isDirectory(), "Input property must be directory, {0}", input);

        File csvFile = new File("report-stat.csv");
        REPORT = CsvWriter.createMySqlCsvWriter(csvFile);
        REPORT.writeFields(
                "Справочник",
                "Элемент",
                "Описание",
                "Тип",
                "Кол-во",
                "Мин",
                "Макс",
                "Ср.",
                "NotNull",
                "Разрядов",
                "Мин длинна",
                "Макс длинна"
        );

        for (Fias fias : Fias.orderForLoading()) {
            if (fias.intermediate) {
                continue;
            }
            FiasFilesProcessor fiasFilesProcessor = new FiasFilesProcessor(fias, REPORT);
            fiasFilesProcessor.processFiles(inputFile);
        }
        // FIXME Да, тут хорошое место для try-with-resources из JDK7, но пока обработка ошибок нам не особо и нужна.
        REPORT.close();
    }

}
