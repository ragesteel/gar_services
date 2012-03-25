package ru.gt2.rusref.fias;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FilenameFilter;

/**
 * Основной метод импорта.
 */
public class Main {
    private static final String FILE_PREFIX = "AS_";
    private static final String FILE_SUFFIX = ".XML";
    
    public static void main(String... args) throws JAXBException {
        for (final Fias fias : Fias.values()) {
            File[] files = findFiles(fias);
            processFiles(fias, files);
        }
    }

    private static File[] findFiles(final Fias fias) {
        File xmlDir = new File("data/XML-2012-03-02");;
        return xmlDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String upperName = name.toUpperCase();
                return upperName.startsWith(FILE_PREFIX + fias.name()) && upperName.endsWith(FILE_SUFFIX);
            }
        });
    }

    private static void processFiles(Fias fias, File[] files) throws JAXBException {
        if ((null == files) || (0 == files.length)) {
            return;
        }

        JAXBContext jaxbContext = JAXBContext.newInstance(fias.wrapper);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        for (File file : files) {
            processFile(unmarshaller, file);
        }
    }

    private static void processFile(Unmarshaller unmarshaller, File file) throws JAXBException {
        String filename = file.getName();
        final ExtractResult extractResult = new ExtractResult(filename);

        unmarshaller.setListener(new Unmarshaller.Listener() {
            @Override
            public void afterUnmarshal(Object target, Object parent) {
                if (null == parent) {
                    return;
                }
                extractResult.increaceItemCount();
            }
        });
        Object unmarshal = unmarshaller.unmarshal(file);

        // FIXME Добавить валидацию!
        System.out.println(extractResult);
    }

}
