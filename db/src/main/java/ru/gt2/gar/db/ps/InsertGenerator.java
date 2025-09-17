package ru.gt2.gar.db.ps;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import ru.gt2.gar.db.schema.DatabaseSchema;
import ru.gt2.gar.db.schema.TableVisitor;
import ru.gt2.gar.domain.GarType;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class InsertGenerator implements TableVisitor {
    private final GarType garType;

    private final DatabaseSchema schema;

    private final StringBuilder generationResult = new StringBuilder();

    private final List<Method> accessors = new ArrayList<>();

    private int columnCount = 0;

    public InsertData generate() {
        if (!generationResult.isEmpty()) {
            throw new IllegalStateException("Already generated");
        }
        schema.visitTable(garType, this);

        return new InsertData(generationResult.toString(), ImmutableList.copyOf(accessors));
    }

    @Override
    public void onStartTable(String tableName, String tableComment) {
        generationResult.append("INSERT INTO ").append(tableName).append(" (");
    }

    @Override
    public void onColumn(String columnName, String columnComment, String type, boolean primaryKey, boolean nullable, Method accessor) {
        if (columnCount > 0) {
            generationResult.append(", ");
        }
        generationResult.append('"').append(columnName).append('"');
        accessors.add(accessor);
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
