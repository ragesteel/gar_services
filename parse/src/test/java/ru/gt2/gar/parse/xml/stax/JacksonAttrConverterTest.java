package ru.gt2.gar.parse.xml.stax;

import org.junit.jupiter.api.Test;
import ru.gt2.gar.domain.GarRecord;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JacksonAttrConverterTest {

    @Test
    void testJackson() {
        record RecordUnderTest(boolean isSomething) implements GarRecord { }
        JacksonAttrConverter<RecordUnderTest> jackson = JacksonAttrConverter.jackson(RecordUnderTest.class);
        RecordUnderTest trueField = new RecordUnderTest(true);
        RecordUnderTest falseField = new RecordUnderTest(false);

        assertEquals(trueField, jackson.apply(Map.of("isSomething", "True")));
        assertEquals(falseField, jackson.apply(Map.of("isSomething", "false")));
    }
}