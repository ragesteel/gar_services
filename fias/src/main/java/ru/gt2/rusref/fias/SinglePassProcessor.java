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
import java.io.IOException;
import java.util.List;

/**
 * Однопроходный обработчик.
 *
 * Просто сразу пишет все обрабатываемые данные в файл.
 */
public class SinglePassProcessor {

    private static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();

    private static final Validator VALIDATOR = VALIDATOR_FACTORY.getValidator();

    protected final Fias fias;

    private final File[] files;

    private final CsvWriter report;

    protected CsvWriter csv;

    private Unmarshaller unmarshaller;

    private Container<?> container;

    public SinglePassProcessor(Fias fias, File[] files, CsvWriter report) {
        this.fias = fias;
        this.files = files;
        this.report = report;
    }

    public void process() throws JAXBException, IOException {
        beforeProcessing();
        processFiles();
        afterProcessing();
    }

    protected void processFiles() throws JAXBException, IOException {
        for (File file : files) {
            processFile(file);
        }
    }

    protected void writeProcessFileReport(ExtractResult extractResult) throws IOException {
        extractResult.print(System.out);
        extractResult.writeReport(report);
    }

    protected void writeEntity(Object entity) throws Exception {
        csv.writeFields(fias.getFieldValues(entity));
    }

    private void beforeProcessing() throws IOException, JAXBException {
        File csvFile = new File(fias.item.getSimpleName() + ".csv");
        csv = CsvWriter.createMySqlCsvWriter(csvFile);
        JAXBContext jaxbContext = JAXBContext.newInstance(fias.wrapper);
        unmarshaller = jaxbContext.createUnmarshaller();
    }

    private void afterProcessing() throws IOException {
        csv.close();
    }

    private void processFile(File file) throws JAXBException, IOException {
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

        writeProcessFileReport(extractResult);
    }

    private void processContainerEntities() {
        // FIXME Оказывается мы не можем просто брать и писать сущность AddressObject,
        // т.к. в ней есть ссылки на саму себя. Соотв. мы должны как-то накапливать эту информацию
        // и передавать дальше только в том случае, если такой ключ мы уже записали.

        List<?> list = container.getList();
        try {
            for (Object entity : list) {
                writeEntity(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}