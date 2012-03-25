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
                IntervalStatuses.class, EstateStatuses.class, CenterStatuses.class,
                OperationStatuses.class);
        unmarshaller = jaxbContext.createUnmarshaller();

        unmarshal(new File(xmlDir, "AS_STRSTAT_20120307_4c5305bc-3796-4d98-a84f-bad2ef5b26be.XML"));
        unmarshal(new File(xmlDir, "AS_ACTSTAT_20120307_0d753277-1744-4a2d-a737-a710c5866137.XML"));
        unmarshal(new File(xmlDir, "AS_INTVSTAT_20120307_ff633bef-b21a-479d-8f04-705541a70b4e.XML"));
        unmarshal(new File(xmlDir, "AS_ESTSTAT_20120307_95b263a6-525b-4584-b95a-843c8a45238c.XML"));
        unmarshal(new File(xmlDir, "AS_CENTERST_20120307_1c7a189d-0460-48a7-b5f5-b358d3fbf033.XML"));
        unmarshal(new File(xmlDir, "AS_OPERSTAT_20120307_84a09639-e49f-4987-a40d-d488c0299f28.XML"));
    }
    
    private static void unmarshal(File file) throws JAXBException {
        Object unmarshal = unmarshaller.unmarshal(file);
        System.out.println(unmarshal);
    }
}
