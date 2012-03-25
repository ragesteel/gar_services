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
        File xmlDir = new File("data/XML-2012-03-02");
        JAXBContext jaxbContext = JAXBContext.newInstance(StructureStatuses.class, ActualStatuses.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Object unmarshal;
        unmarshal = unmarshaller.unmarshal(new File(xmlDir, "AS_STRSTAT_20120307_4c5305bc-3796-4d98-a84f-bad2ef5b26be.XML"));
        System.out.println(unmarshal);
        unmarshal = unmarshaller.unmarshal(new File(xmlDir, "AS_ACTSTAT_20120307_0d753277-1744-4a2d-a737-a710c5866137.XML"));
        System.out.println(unmarshal);
    }
}
