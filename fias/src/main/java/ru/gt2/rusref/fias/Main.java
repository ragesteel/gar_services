package ru.gt2.rusref.fias;

import ru.gt2.rusref.CsvWriter;
import ru.gt2.rusref.stat.ExtractResult;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

/**
 * Основной метод импорта.
 */
public class Main {
    private static final String FILE_PREFIX = "AS_";
    private static final String FILE_SUFFIX = ".XML";
    private static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();
    private static final Validator VALIDATOR = VALIDATOR_FACTORY.getValidator();

    private static CsvWriter REPORT;

    private static CsvWriter CSV;

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

        for (Fias fias : new Fias[]{ Fias.NORMDOC, Fias.ADDROBJ, Fias.HOUSEINT, Fias.HOUSE, Fias.LANDMARK}) {
            File[] files = findFiles(fias);
            processFiles(fias, files);
        }
        // FIXME Да, тут хорошое место для try-with-resources из JDK7, но пока обработка ошибок нам не особо и нужна.
        REPORT.close();
    }

    private static File[] findFiles(final Fias fias) {
        File xmlDir = new File("data/2012-03-22-xml");
        final String prefix = FILE_PREFIX + fias.name() + "_";
        final int nameLen = prefix.length() + FILE_SUFFIX.length() + 36 + 8 + 1;
        return xmlDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (nameLen != name.length()) {
                    return false;
                }
                String upperName = name.toUpperCase();
                return upperName.startsWith(prefix) && upperName.endsWith(FILE_SUFFIX);
            }
        });
    }

    private static void processFiles(Fias fias, File[] files) throws JAXBException, IOException {
        if ((null == files) || (0 == files.length)) {
            return;
        }

        File csvFile = new File(fias.item.getSimpleName() + ".csv");
        CSV = CsvWriter.createMySqlCsvWriter(csvFile);
        JAXBContext jaxbContext = JAXBContext.newInstance(fias.wrapper);
        
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        for (File file : files) {
            processFile(fias, unmarshaller, file);
        }
        CSV.close();
    }

    private static void processFile(final Fias fias, Unmarshaller unmarshaller, File file) throws JAXBException, IOException {
        String filename = file.getName();

        final ExtractResult extractResult = new ExtractResult(fias, VALIDATOR);
        System.out.println("Processing file: " + filename);
        unmarshaller.setListener(new Unmarshaller.Listener() {
            @Override
            public void afterUnmarshal(Object target, Object parent) {
                if (null == parent) {
                    return;
                }
                extractResult.updateStatistics(target);

                // FIXME Грязный хак, пока мы не начали делать по правильному
                List<?> list = null;
                if (parent instanceof Container<?>) {
                    list = ((Container<?>) parent).getList();
                }

                // FIXME Добавить индикацию прогресса чтения
                if (null == list) {
                    return;
                }

                if (list.size() > 1000) {
                    processContainerEntities(fias, (Container<?>) parent);
                    list.clear();
                }
            }
        });

        Container<?> container = (Container<?>) unmarshaller.unmarshal(file);
        processContainerEntities(fias, container);

        extractResult.print(System.out);
        extractResult.writeReport(REPORT);
    }

    private static void processContainerEntities(Fias fias, Container<?> container) {
        List<?> list = container.getList();
        try {
            for (Object entity : list) {
                CSV.writeFields(fias.getFieldValues(entity));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
