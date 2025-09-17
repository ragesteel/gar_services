package ru.gt2.gar.db.schema;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.domain.LengthLimit;
import ru.gt2.gar.domain.SchemaComment;

import java.lang.reflect.RecordComponent;
import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DatabaseSchema {
    private final NamingStrategy namingStrategy;

    /// Обход таблицы и её колонок
    public void visitTable(GarType garType, TableVisitor tableVisitor) {
        Class<? extends Record> recordClass = garType.recordClass;
        tableVisitor.onStartTable(namingStrategy.getTableName(garType.name()), recordClass.getAnnotation(SchemaComment.class).value());
        boolean primaryKey = true;
        for (RecordComponent rc : recordClass.getRecordComponents()) {
            tableVisitor.onColumn(namingStrategy.getColumnName(rc.getName()), rc.getAnnotation(SchemaComment.class).value(), getType(rc),
                    primaryKey, rc.isAnnotationPresent(Nullable.class));
            primaryKey = false;
            // TODO добавить внешние ключи, но только после проверки на то,
            //  что они будут работать для текущего набора данных
        }
        tableVisitor.onEndTable();
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
}
