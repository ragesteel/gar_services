package ru.gt2.gar.parse.xml;

import com.ctc.wstx.sr.TypedStreamReader;
import com.ctc.wstx.stax.WstxInputFactory;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.evt.XMLEvent2;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.Input;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.domain.NormativeDocType;
import ru.gt2.gar.parse.xml.stax2.LocalDateValueDecoder;

import javax.xml.transform.Source;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StaxReaderTest {
    @Test
    public void testReadWithStax() throws Exception {
        XMLInputFactory2 xmlInputFactory = new WstxInputFactory();
//        Source
        Source source = Input.fromString("""
                <NDOCTYPES>
                    <NDOCTYPE ID="0" NAME="Не указан" STARTDATE="1900-01-01" ENDDATE="2016-03-31"/>
                </NDOCTYPES>
                """).build();
        TypedStreamReader xsr = (TypedStreamReader) xmlInputFactory.createXMLStreamReader(source);
        System.out.println(xsr.getClass());
        NormativeDocType normativeDocType = null;

        GarType garType = GarType.NORMATIVE_DOCS_TYPES;
        boolean expectOuter = true;
        LocalDateValueDecoder ldvd = new LocalDateValueDecoder();
        while (xsr.hasNext()) {
            switch (xsr.next()) {
                case XMLEvent2.START_ELEMENT: {
                    String elementName = xsr.getName().getLocalPart();
                    if (expectOuter) {
                        if (elementName.equals("NDOCTYPES")) {
                            expectOuter = false;
                            continue;
                        } else {
                            throw new IllegalArgumentException();
                        }
                    } else {
                        if (!elementName.equals("NDOCTYPE")) {
                            throw new IllegalArgumentException();
                        }
                    }
                    int id = xsr.getAttributeAsInt(xsr.getAttributeIndex(null, "ID"));
                    String name = xsr.getAttributeValue(null, "NAME");
                    // Да есть такое, но кажется проще вручную распарсить, хотя в том случае может и меньше проблем с памятью.
                    // xsr.getAttributeAs(xsr.getAttributeIndex(null, "STARTDATE"));
                    LocalDate startDate = ldvd.parse(xsr, "STARTDATE");
                    LocalDate endDate = ldvd.parse(xsr, "ENDDATE");
                    normativeDocType = new NormativeDocType(id, name, startDate, endDate);
                }
                case XMLEvent2.END_ELEMENT: {
                    String elementName = xsr.getName().getLocalPart();
                    if (elementName.equals("NDOCTYPES")) {
                        System.out.println("Finished");
                    }
                }
            }
        }
        assertEquals(new NormativeDocType(0, "Не указан",
                LocalDate.of(1900, Month.JANUARY, 1),
                LocalDate.of(2016, Month.MARCH, 31)), normativeDocType);

//        xsr.getAttributeIndex(/)
    }

}
