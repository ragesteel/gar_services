package ru.gt2.gar.db.sql;

import org.junit.jupiter.api.Test;
import ru.gt2.gar.db.NamingStrategy;
import ru.gt2.gar.db.schema.DatabaseSchema;
import ru.gt2.gar.domain.GarType;

import static org.junit.jupiter.api.Assertions.*;

public class QueriesGeneratorTest {
    @Test
    public void testGen() {
        QueriesGenerator queriesGenerator = new QueriesGenerator(GarType.NORMATIVE_DOCS_TYPES,
                new DatabaseSchema(NamingStrategy.LOWER_UNDERSCORE));

        assertEquals("""
                INSERT INTO normative_docs_types ("id", "name", "start_date", "end_date") VALUES (?, ?, ?, ?)""",
                queriesGenerator.getInsert());
        assertEquals("""
                SELECT "id", "name", "start_date", "end_date" FROM normative_docs_types WHERE "id" = ANY(?)""",
                queriesGenerator.getSelect());
        assertEquals("INT", queriesGenerator.getIdColumnType());
    }
}