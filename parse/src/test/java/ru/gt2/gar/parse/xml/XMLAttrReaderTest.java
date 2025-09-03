package ru.gt2.gar.parse.xml;

import org.junit.jupiter.api.Test;
import ru.gt2.gar.parse.domain.AddressObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.Month.AUGUST;
import static java.time.Month.JUNE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class XMLAttrReaderTest {
    @Test
    public void testAddrObj() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("""
<?xml version="1.0" encoding="utf-8"?>
<ADDRESSOBJECTS>
    <OBJECT ID="52185551" OBJECTID="174296886"
        OBJECTGUID="ef3d094e-f34a-4484-b1ee-7531de095339" CHANGEID="688238606"
        NAME="Садрыя Мингазова" TYPENAME="ул." LEVEL="8" OPERTYPEID="10" PREVID="0" NEXTID="0"
        UPDATEDATE="2025-08-28" STARTDATE="2025-08-28"
        ENDDATE="2079-06-06" ISACTUAL="1" ISACTIVE="1" />
</ADDRESSOBJECTS>
                """.getBytes(UTF_8));
        try (var xmlAttrReader = new XMLAttrReader<>(inputStream, XmlAttrMapper.ADDRESS_OBJECT, Converter.jackson(AddressObject.class), 10)) {
            List<AddressObject> next = xmlAttrReader.next();
            assertEquals(new AddressObject(52185551L, 174296886L,
                    UUID.fromString("ef3d094e-f34a-4484-b1ee-7531de095339"), 688238606L,
                    "Садрыя Мингазова", "ул.", "8", 10, 0L, 0L,
                    LocalDate.of(2025, AUGUST, 28), LocalDate.of(2025, AUGUST, 28),
                    LocalDate.of(2079, JUNE, 6), 1, 1), next.getFirst());
        }
    }
}
