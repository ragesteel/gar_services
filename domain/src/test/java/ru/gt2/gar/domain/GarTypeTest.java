package ru.gt2.gar.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GarTypeTest {
    @Test
    public void testGetRecordClasses() {
        Assertions.assertEquals(22, GarType.getRecordClasses().size());
    }

    @Test
    public void getMaxNameLength() {
        OptionalInt max = Arrays.stream(GarType.values()).map(Enum::name).mapToInt(String::length).max();
        assertTrue(max.isPresent());
        assertEquals(20, max.getAsInt());
    }
}