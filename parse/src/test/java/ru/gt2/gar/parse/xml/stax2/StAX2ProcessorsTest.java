package ru.gt2.gar.parse.xml.stax2;

import org.junit.jupiter.api.Test;
import ru.gt2.gar.domain.GarType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StAX2ProcessorsTest {

    @Test
    public void allGarTypes() {
        assertEquals(GarType.values().length, new StAX2Processors(1, null).size());
    }
}
