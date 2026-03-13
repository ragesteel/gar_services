package ru.gt2.gar.db.schema;

public interface TableVisitor {
    default void onStartTable(String tableName, String tableComment) {
    }

    void onColumn(String columnName, String columnComment, String type, boolean primaryKey, boolean nullable);

    default void onEndTable() {
    }

    default void onRenameColumn(String tableName, String oldColumnName, String newColumnName) {
    }
}
