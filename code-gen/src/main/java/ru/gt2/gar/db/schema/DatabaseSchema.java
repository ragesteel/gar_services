package ru.gt2.gar.db.schema;

import jakarta.annotation.Nullable;
import ru.gt2.gar.db.NamingStrategy;
import ru.gt2.gar.domain.GarRecord;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.domain.LengthLimit;
import ru.gt2.gar.domain.SchemaComment;

import java.lang.reflect.RecordComponent;
import java.time.LocalDate;
import java.util.UUID;

public class DatabaseSchema {
    private final NamingStrategy namingStrategy;

    public DatabaseSchema(NamingStrategy namingStrategy) {
        this.namingStrategy = new ReplaceReservedWordsDecorator(namingStrategy);
    }

    /// Обход таблицы и её колонок
    public void createTable(GarType garType, TableVisitor tableVisitor) {
        Class<? extends GarRecord> recordClass = garType.recordClass;
        tableVisitor.onStartTable(getTableName(garType), recordClass.getAnnotation(SchemaComment.class).value());
        boolean primaryKey = true;
        for (RecordComponent rc : recordClass.getRecordComponents()) {
            tableVisitor.onColumn(getColumnName(rc), rc.getAnnotation(SchemaComment.class).value(), getType(rc),
                    primaryKey, rc.isAnnotationPresent(Nullable.class));
            primaryKey = false;
            // TODO добавить внешние ключи, но только после проверки на то,
            //  что они будут работать для текущего набора данных
        }
        tableVisitor.onEndTable();
    }

    public void renameColumn(GarType garType, TableVisitor tableVisitor) {
        for (RecordComponent rc : garType.recordClass.getRecordComponents()) {
            String name = rc.getName();
            String replacement = ReplaceReservedWordsDecorator.REPLACEMENTS.get(name);
            if (null == replacement) {
                continue;
            }
            tableVisitor.onRenameColumn(getTableName(garType), name, replacement);
        }
    }

    public boolean quoteColumnNames() {
        return true;
    }

    private String getType(RecordComponent rc) {
        Class<?> type = rc.getType();
        if (long.class.equals(type) || Long.class.equals(type)) {
            return "BIGINT";
        } else if (int.class.equals(type) || Integer.class.equals(type)) {
            return "INT";
        } else if (String.class.equals(type)) {
            return "VARCHAR(" + rc.getAnnotation(LengthLimit.class).value() + ")";
        } else if (boolean.class.equals(type)) {
            return "BOOLEAN";
        } else if (LocalDate.class.equals(type)) {
            return "DATE";
        } else if (UUID.class.equals(type)) {
            return "UUID";
        } else {
            throw new RuntimeException("Support for type " + type + " is not implemented!");
        }
    }

    private String getTableName(GarType garType) {
        return namingStrategy.getTableName(garType.name());
    }

    private String getColumnName(RecordComponent rc) {
        return namingStrategy.getColumnName(rc.getName());
    }
}
