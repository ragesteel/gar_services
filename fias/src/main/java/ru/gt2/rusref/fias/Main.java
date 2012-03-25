package ru.gt2.rusref.fias;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;

/**
 * Основной метод импорта.
 *
 * FIXME Пройтись по всем классам и сделать имена, соответствующие общим (например везде использовать name и shortName).
 * FIXME Реализовать свой конвертор и валидатор для GUID.
 * FIXME Включить валидацию с использованием BeanValidation.
 * FIXME Сделать все типы сериализуемыми.
 */
public class Main {
    private static final String FILE_PREFIX = "AS_";
    private static final String FILE_SUFFIX = ".XML";
    
    public static void main(String... args) throws JAXBException {
        File xmlDir = new File("data/XML-2012-03-02");
        for (final Fias fias : Fias.values()) {
            JAXBContext jaxbContext = JAXBContext.newInstance(fias.wrapper);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            File[] files = xmlDir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    String upperName = name.toUpperCase();
                    return upperName.startsWith(FILE_PREFIX + fias.name()) && upperName.endsWith(FILE_SUFFIX);
                }
            });
            
            for (File file : files) {
                Object unmarshal = unmarshaller.unmarshal(file);
                // FIXME Добавить валидацию!
                System.out.println(unmarshal);
            }
        }
    }
    
}
