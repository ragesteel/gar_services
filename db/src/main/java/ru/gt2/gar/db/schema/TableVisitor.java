package ru.gt2.gar.db.schema;

import java.lang.reflect.Method;

public interface TableVisitor {
    void onStartTable(String tableName, String tableComment);
    void onColumn(String columnName, String columnComment, String type, boolean primaryKey, boolean nullable, Method accessor);
    void onEndTable();
}
