package ru.gt2.gar.parse.xml.stax2;

import com.ctc.wstx.sr.TypedStreamReader;
import com.ctc.wstx.stax.WstxInputFactory;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.evt.XMLEvent2;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.Input;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.domain.NormativeDocType;
import ru.gt2.gar.domain.RoomType;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Source;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Stax2ReaderTest {
    @Test
    public void testReadWithStax2() throws Exception {
        XMLInputFactory2 xmlInputFactory = new WstxInputFactory();
        Source source = Input.fromString("""
                <NDOCTYPES>
                    <NDOCTYPE ID="0" NAME="Не указан" STARTDATE="1900-01-01" ENDDATE="2016-03-31"/>
                </NDOCTYPES>
                """).build();
        TypedStreamReader xsr = (TypedStreamReader) xmlInputFactory.createXMLStreamReader(source);
        NormativeDocType normativeDocType = null;

        GarType garType = GarType.NORMATIVE_DOCS_TYPES;
        boolean expectOuter = true;
        TypedAttrReader tar = new TypedAttrReader(xsr);
        while (xsr.hasNext()) {
            int eventCode;
            switch (eventCode = xsr.next()) {
                case XMLEvent2.START_ELEMENT: {
                    String elementName = xsr.getName().getLocalPart();
                    if (expectOuter) {
                        if (elementName.equals(garType.outerTagName)) {
                            System.out.println("Got outer tag");
                            expectOuter = false;
                            continue;
                        } else {
                            throw new IllegalArgumentException("Unexpected outer tag: " + elementName);
                        }
                    } else {
                        if (!elementName.equals(garType.elementName)) {
                            System.out.println("Got inner tag");
                            throw new IllegalArgumentException("Unexpected tag: " + elementName);
                        }
                    }
                    normativeDocType = createNormativeDocType(tar);
                    break;
                }
                case XMLEvent2.END_ELEMENT: {
                    String elementName = xsr.getName().getLocalPart();
                    if (elementName.equals(garType.outerTagName)) {
                        System.out.println("Outer tag is finished");
                    }
                    break;
                }
                case XMLEvent2.END_DOCUMENT: {
                    System.out.println("Document is finished");
                    break;
                }
                case XMLEvent2.CHARACTERS: {
                    // Nothing
                    break;
                }
                default: {
                    System.out.println("Got event:" + eventCode);
                }
            }
        }
        assertEquals(new NormativeDocType(0, "Не указан",
                LocalDate.of(1900, Month.JANUARY, 1),
                LocalDate.of(2016, Month.MARCH, 31)), normativeDocType);
    }

    @NonNull
    private static NormativeDocType createNormativeDocType(TypedAttrReader tar)
            throws XMLStreamException {
        int id = tar.getInt("ID");
        String name = tar.getString("NAME");
        LocalDate startDate = tar.getLocalDate("STARTDATE");
        LocalDate endDate = tar.getLocalDate("ENDDATE");
        return new NormativeDocType(id, name, startDate, endDate);
    }

    @NonNull
    private static RoomType createRoomType(TypedAttrReader tar) throws XMLStreamException {
        int id = tar.getInt("ID");
        String name = tar.getString("NAME");
        String shortName = tar.getNullableString("SHORTNAME");
        String desc = tar.getNullableString("DESC");
        LocalDate updateDate = tar.getLocalDate("UPDATEDATE");
        LocalDate startDate = tar.getLocalDate("STARTDATE");
        LocalDate endDate = tar.getLocalDate("ENDDATE");
        boolean isActive = tar.getBoolean("ISACTIVE");
        return new RoomType(id, name, shortName, desc, updateDate, startDate, endDate, isActive);
    }
}
