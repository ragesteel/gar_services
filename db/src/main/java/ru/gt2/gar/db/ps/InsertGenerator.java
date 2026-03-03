package ru.gt2.gar.db.ps;

import lombok.RequiredArgsConstructor;
import ru.gt2.gar.db.schema.DatabaseSchema;
import ru.gt2.gar.db.schema.TableVisitor;
import ru.gt2.gar.domain.GarType;

@RequiredArgsConstructor
public class InsertGenerator implements TableVisitor {
    private final GarType garType;

    private final DatabaseSchema schema;

    private final StringBuilder selectBuilder = new StringBuilder();
    private final StringBuilder insertBuilder = new StringBuilder();

    private String tableName = "";
    private String idColumnName = "";
    private int columnCount = 0;

    public InsertData generate() {
        if (!selectBuilder.isEmpty()) {
            throw new IllegalStateException("Already generated");
        }
        schema.visitTable(garType, this);

        return new InsertData(selectBuilder.toString(), insertBuilder.toString());
    }

    @Override
    public void onStartTable(String tableName, String tableComment) {
        this.tableName = tableName;
        insertBuilder.append("INSERT INTO ").append(tableName).append(" (");
        insertBuilder.append("SELECT ");
    }

    @Override
    public void onColumn(String columnName, String columnComment, String type, boolean primaryKey, boolean nullable) {
        if (columnCount > 0) {
            selectBuilder.append(", ");
            insertBuilder.append(", ");
        }
        selectBuilder.append('"').append(columnName).append('"');
        insertBuilder.append('"').append(columnName).append('"');

        if (primaryKey) {
            idColumnName = columnName;
        }
        columnCount++;
    }

    @Override
    public void onEndTable() {
        selectBuilder.append(" FROM ").append(tableName)
                .append(" WHERE ").append('"').append(idColumnName).append('"')
                .append(" IN (?)");

        insertBuilder.append(") VALUES (");
        for (int i = 0; i < columnCount; i++) {
            if (i > 0) {
                insertBuilder.append(", ");
            }
            insertBuilder.append('?');
        }
        insertBuilder.append(");");
    }
}
