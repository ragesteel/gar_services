package ru.gt2.gar.db.ps;

import org.junit.jupiter.api.Test;
import ru.gt2.gar.domain.GarType;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SQLQueriesTest {
    @Test
    public void testAllGarTypes() {
        for (GarType value : GarType.values()) {
            assertNotNull(SQLQueries.get(value));
        }
    }
}