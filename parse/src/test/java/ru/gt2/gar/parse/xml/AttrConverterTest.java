package ru.gt2.gar.parse.xml;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AttrConverterTest {

    @Test
    void testJackson() {
        record RecordUnderTest(boolean isSomething) { }
        AttrConverter<RecordUnderTest> jackson = AttrConverter.jackson(RecordUnderTest.class);
        RecordUnderTest trueField = new RecordUnderTest(true);
        RecordUnderTest falseField = new RecordUnderTest(false);

        assertEquals(trueField, jackson.apply(Map.of("isSomething", "True")));
        assertEquals(falseField, jackson.apply(Map.of("isSomething", "false")));
    }
}