package ru.gt2.gar.parse.xml.stax.event;

import org.junit.jupiter.api.Test;
import ru.gt2.gar.domain.GarType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllStAXProcessorsTest {
    @Test
    public void allGarTypes() {
        assertEquals(GarType.values().length, new AllStAXProcessors(1).size());
    }
}