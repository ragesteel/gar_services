package ru.gt2.rusref.fias;

import ru.gt2.rusref.Filenames;
import ru.gt2.rusref.stat.ExtractResult;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Основной метод импорта.
 */
public class Main {
    private static final String FILE_PREFIX = "AS_";
    private static final String FILE_SUFFIX = ".XML";
    private static final String SERIALIZED_EXT = "serialized";
    private static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();
    private static final Validator VALIDATOR = VALIDATOR_FACTORY.getValidator();
    
    public static void main(String... args) throws JAXBException, IOException {
        for (Fias fias : Fias.values()) {
            File[] files = findFiles(fias);
            processFiles(fias, files);
        }
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

        JAXBContext jaxbContext = JAXBContext.newInstance(fias.wrapper);
        
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        for (File file : files) {
            processFile(fias, unmarshaller, file);
        }
    }

    private static void processFile(Fias fias, Unmarshaller unmarshaller, File file) throws JAXBException, IOException {
        String filename = file.getName();
        File serialized = new File(file.getParentFile(), Filenames.replaceExtension(filename, SERIALIZED_EXT));
        final ObjectOutputStream objectOutputStream =
                new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(serialized)));

        final ExtractResult extractResult = new ExtractResult(fias, VALIDATOR);
        System.out.println("Processing file: " + filename);
        unmarshaller.setListener(new Unmarshaller.Listener() {
            @Override
            public void afterUnmarshal(Object target, Object parent) {
                if (null == parent) {
                    return;
                }
                extractResult.updateStatistics(target);
                try {
                    objectOutputStream.writeUnshared(target);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // FIXME Грязный хак, пока мы не начали делать по правильному
                List<?> list = null;
                if (parent instanceof NormativeDocumentes) {
                    list = ((NormativeDocumentes) parent).normativeDocument;
                } else if (parent instanceof AddressObjects) {
                    list = ((AddressObjects) parent).addressObject;
                } else if (parent instanceof Houses) {
                    list = ((Houses) parent).house;
                }

                // FIXME Добавить индикацию прогресса чтения
                if (null == list) {
                    return;
                }
                if (list.size() > 1000) {
                    list.clear();
                }
            }
        });
        unmarshaller.unmarshal(file);
        objectOutputStream.close();

        extractResult.print(System.out);
    }

}
