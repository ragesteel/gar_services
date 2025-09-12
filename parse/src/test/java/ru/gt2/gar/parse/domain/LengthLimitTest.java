package ru.gt2.gar.parse.domain;

import org.junit.jupiter.api.Test;

import java.lang.reflect.RecordComponent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LengthLimitTest {

    // Убедиться в том, что только String содержит аннотацию @LengthLimit и что на всех String есть эта аннотация
    @Test
    public void testOnlyStringType() {
        GarType.forEach(null,
                recordComponent -> assertOnlyStringHaveLimit(recordComponent));
    }

    private static void assertOnlyStringHaveLimit(RecordComponent recordComponent) {
        if (recordComponent.getType().equals(String.class)) {
            LengthLimit lengthLimit = recordComponent.getAnnotation(LengthLimit.class);
            assertNotNull(lengthLimit, () -> "Field " + recordComponent.getName() + " has no LengthLimit annotation");
            assertTrue(lengthLimit.value() > 0, () -> "Field " + recordComponent.getName() + " must have positive value");
        } else {
            assertFalse(recordComponent.isAnnotationPresent(LengthLimit.class),
                    () -> "Field " + recordComponent.getName() + " must not have LengthLimit annotation");
        }
    }
}
