package ru.gt2.gar.db.ps;

import com.google.common.collect.ImmutableMap;
import ru.gt2.gar.domain.GarType;

public class SQLQueries {
    private static final ImmutableMap<GarType, GeneratedSQL> MAP = ImmutableMap.<GarType, GeneratedSQL>builder()
            .put(GarType.NORMATIVE_DOCS_KINDS, new GeneratedSQL("INT", """
                SELECT "id", "name", "start_date", "end_date" FROM normative_docs_types WHERE "id" = ANY(?)""", """
                INSERT INTO normative_docs_types ("id", "name", "start_date", "end_date") VALUES (?, ?, ?, ?)"""))
            .build();

    public static GeneratedSQL get(GarType garType) {
        return MAP.get(garType);
    }
}
