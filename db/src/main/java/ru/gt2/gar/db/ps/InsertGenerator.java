package ru.gt2.gar.db.ps;

import lombok.RequiredArgsConstructor;
import ru.gt2.gar.db.schema.DatabaseSchema;
import ru.gt2.gar.db.schema.TableVisitor;
import ru.gt2.gar.domain.GarType;

@RequiredArgsConstructor
public class InsertGenerator implements TableVisitor {
    private final GarType garType;

    private final DatabaseSchema schema;

    private final StringBuilder generationResult = new StringBuilder();

    private int columnCount = 0;

    public String generate() {
        if (generationResult.isEmpty()) {
            throw new IllegalStateException("Already generated");
        }
        schema.visitTable(garType, this);
        return generationResult.toString();
    }

    @Override
    public void onStartTable(String tableName, String tableComment) {
        generationResult.append("INSERT INTO ").append(tableName).append(" (");
    }

    @Override
    public void onColumn(String columnName, String columnComment, String type, boolean primaryKey, boolean nullable) {
        if (columnCount > 0) {
            generationResult.append(", ");
        }
        generationResult.append(columnName);
        columnCount++;
    }

    @Override
    public void onEndTable() {
        generationResult.append(") VALUES (");
        for (int i = 0; i < columnCount; i++) {
            if (i > 0) {
                generationResult.append(", ");
            }
            generationResult.append('?');
        }
        generationResult.append(");");
    }
}
