package ru.gt2.rusref.fias;

import ru.gt2.rusref.CsvWriter;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

/**
 * Основной метод импорта.
 */
public class Main {
    private static CsvWriter REPORT;

    public static void main(String... args) throws JAXBException, IOException {

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

        for (Fias fias : Fias.orderByReferences()) {
            FiasExtractor fiasExtractor = new FiasExtractor(fias, REPORT);
            fiasExtractor.findFiles();
            fiasExtractor.processFiles();
        }
        // FIXME Да, тут хорошое место для try-with-resources из JDK7, но пока обработка ошибок нам не особо и нужна.
        REPORT.close();
    }

}
