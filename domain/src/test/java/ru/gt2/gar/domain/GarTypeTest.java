package ru.gt2.gar.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GarTypeTest {
    @Test
    public void testGetRecordClasses() {
        Assertions.assertEquals(22, GarType.getRecordClasses().size());
    }
}