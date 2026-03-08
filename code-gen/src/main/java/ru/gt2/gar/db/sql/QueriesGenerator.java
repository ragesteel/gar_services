package ru.gt2.gar.db.sql;

import lombok.Getter;
import ru.gt2.gar.db.schema.DatabaseSchema;
import ru.gt2.gar.db.schema.TableVisitor;
import ru.gt2.gar.domain.GarType;

public class QueriesGenerator implements TableVisitor {
    private final StringBuilder selectBuilder = new StringBuilder();
    private final StringBuilder insertBuilder = new StringBuilder();

    private String tableName = "";
    private String idColumnName = "";

    @Getter
    private String idColumnType = "";
    @Getter
    private String select = "";
    @Getter
    private String insert = "";

    private int columnCount = 0;

    public QueriesGenerator(GarType garType, DatabaseSchema schema) {
        schema.visitTable(garType, this);
    }

    @Override
    public void onStartTable(String tableName, String tableComment) {
        this.tableName = tableName;
        insertBuilder.append("INSERT INTO ").append(tableName).append(" (");
        selectBuilder.append("SELECT ");
    }

    @Override
    public void onColumn(String columnName, String columnComment, String type, boolean primaryKey, boolean nullable) {
        if (columnCount > 0) {
            insertBuilder.append(", ");
            selectBuilder.append(", ");
        }
        insertBuilder.append('"').append(columnName).append('"');
        selectBuilder.append('"').append(columnName).append('"');

        if (primaryKey) {
            idColumnName = columnName;
            idColumnType = type;
        }
        columnCount++;
    }

    @Override
    public void onEndTable() {
        insertBuilder.append(") VALUES (");
        for (int i = 0; i < columnCount; i++) {
            if (i > 0) {
                insertBuilder.append(", ");
            }
            insertBuilder.append('?');
        }
        insertBuilder.append(")");

        selectBuilder.append(" FROM ").append(tableName)
                .append(" WHERE ").append('"').append(idColumnName).append('"')
                .append(" = ANY(?)");

        select = selectBuilder.toString();
        insert = insertBuilder.toString();
    }
}
