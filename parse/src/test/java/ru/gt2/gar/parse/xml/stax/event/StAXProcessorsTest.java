package ru.gt2.gar.parse.xml.stax.event;

import org.junit.jupiter.api.Test;
import ru.gt2.gar.domain.GarType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StAXProcessorsTest {
    @Test
    public void allGarTypes() {
        assertEquals(GarType.values().length, new StAXProcessors(1).size());
    }
}