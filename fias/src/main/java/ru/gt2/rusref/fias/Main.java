package ru.gt2.rusref.fias;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Основной метод импорта.
 */
public class Main {
    public static void main(String... args) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(StructureStatuses.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Object unmarshal = unmarshaller.unmarshal(new File("data/XML-2012-03-02/AS_STRSTAT_20120307_4c5305bc-3796-4d98-a84f-bad2ef5b26be.XML"));
        System.out.println(unmarshal);
    }
}
