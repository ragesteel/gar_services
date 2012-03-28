package ru.gt2.rusref.fias;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

/**
 * Основной метод импорта.
 */
public class Main {
    private static final String FILE_PREFIX = "AS_";
    private static final String FILE_SUFFIX = ".XML";
    
    public static void main(String... args) throws JAXBException {
        for (Fias fias : Fias.values()) {
            File[] files = findFiles(fias);
            processFiles(fias, files);
        }
    }

    private static File[] findFiles(final Fias fias) {
        File xmlDir = new File("data/XML-2012-03-02");
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

    private static void processFiles(Fias fias, File[] files) throws JAXBException {
        if ((null == files) || (0 == files.length)) {
            return;
        }

        JAXBContext jaxbContext = JAXBContext.newInstance(fias.wrapper);
        
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        for (File file : files) {
            processFile(fias, unmarshaller, file);
        }
    }

    private static void processFile(Fias fias, Unmarshaller unmarshaller, File file) throws JAXBException {
        String filename = file.getName();
        final ExtractResult extractResult = new ExtractResult(fias);
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
                if (parent instanceof NormativeDocumentes) {
                    list = ((NormativeDocumentes) parent).normativeDocument;
                } else if (parent instanceof AddressObjects) {
                    list = ((AddressObjects) parent).addressObject;
                } else if (parent instanceof Houses) {
                    list = ((Houses) parent).house;
                }

                if (null == list) {
                    return;
                }
                if (list.size() > 1000) {
                    list.clear();
                }
            }
        });
        Object unmarshal = unmarshaller.unmarshal(file);

        // FIXME Добавить валидацию!
        // FIXME Более «красивый» вывод статистики, каждое поле на своей строке — отдельный метод для вывода.
        System.out.println(extractResult);
    }

}
