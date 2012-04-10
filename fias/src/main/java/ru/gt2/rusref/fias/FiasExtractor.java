package ru.gt2.rusref.fias;

import lombok.RequiredArgsConstructor;
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
 * Извлекатель данных из справочников ФИАС.
 */
@RequiredArgsConstructor
public class FiasExtractor {

    private static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();

    private static final Validator VALIDATOR = VALIDATOR_FACTORY.getValidator();

    private static final String FILE_PREFIX = "AS_";

    private static final String FILE_SUFFIX = ".XML";

    private final Fias fias;

    private final CsvWriter report;

    private File[] files;

    private CsvWriter csv;

    private Unmarshaller unmarshaller;

    private File file;

    private Container<?> container;

    public void findFiles() {
        File xmlDir = new File("data/2012-03-22-xml");
        final String prefix = FILE_PREFIX + fias.name() + "_";
        final int nameLen = prefix.length() + FILE_SUFFIX.length() + 36 + 8 + 1;
        files = xmlDir.listFiles(new FilenameFilter() {
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

    public void processFiles() throws JAXBException, IOException {
        if ((null == files) || (0 == files.length)) {
            return;
        }

        File csvFile = new File(fias.item.getSimpleName() + ".csv");
        csv = CsvWriter.createMySqlCsvWriter(csvFile);
        JAXBContext jaxbContext = JAXBContext.newInstance(fias.wrapper);

        unmarshaller = jaxbContext.createUnmarshaller();

        for (File file : files) {
            this.file = file;
            processFile();
        }
        csv.close();
    }

    private void processFile() throws JAXBException, IOException {
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
                    container = (Container<?>) parent;
                    processContainerEntities();
                    list.clear();
                }
            }
        });

        container = (Container<?>) unmarshaller.unmarshal(file);
        processContainerEntities();

        extractResult.print(System.out);
        extractResult.writeReport(report);
    }

    private void processContainerEntities() {
        // FIXME Оказывается мы не можем просто брать и писать сущность AddressObject,
        // т.к. в ней есть ссылки на саму себя. Соотв. мы должны как-то накапливать эту информацию
        // и передавать дальше только в том случае, если такой ключ мы уже записали.

        List<?> list = container.getList();
        try {
            for (Object entity : list) {
                csv.writeFields(fias.getFieldValues(entity));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
