package ru.gt2.gar.parse.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GarTypeTest {
    @Test
    public void testGetRecordClasses() {
        assertEquals(22, GarType.getRecordClasses().size());
    }
}