package ru.gt2.gar.parse.zip;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntryNameMatcherTest {

    @Test
    void tryParse() {
        assertEquals(
            new GarEntry("AS_ADDR_OBJ_TYPES", LocalDate.of(2025, Month.AUGUST, 29),
                UUID.fromString("729c6849-8629-4ba6-9271-7be3045287ae"), Optional.empty()),
            EntryNameMatcher.tryParse("AS_ADDR_OBJ_TYPES_20250829_729c6849-8629-4ba6-9271-7be3045287ae.XML"));
    }
}