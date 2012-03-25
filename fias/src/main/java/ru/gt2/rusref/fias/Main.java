package ru.gt2.rusref.fias;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Основной метод импорта.
 */
public class Main {
    private static Unmarshaller unmarshaller;
    
    public static void main(String... args) throws JAXBException {
        File xmlDir = new File("data/XML-2012-03-02");
        JAXBContext jaxbContext = JAXBContext.newInstance(StructureStatuses.class, ActualStatuses.class,
                IntervalStatuses.class);
        unmarshaller = jaxbContext.createUnmarshaller();

        unmarshal(new File(xmlDir, "AS_STRSTAT_20120307_4c5305bc-3796-4d98-a84f-bad2ef5b26be.XML"));
        unmarshal(new File(xmlDir, "AS_ACTSTAT_20120307_0d753277-1744-4a2d-a737-a710c5866137.XML"));
        unmarshal(new File(xmlDir, "AS_INTVSTAT_20120307_ff633bef-b21a-479d-8f04-705541a70b4e.XML"));
    }
    
    private static void unmarshal(File file) throws JAXBException {
        Object unmarshal = unmarshaller.unmarshal(file);
        System.out.println(unmarshal);
    }
}
