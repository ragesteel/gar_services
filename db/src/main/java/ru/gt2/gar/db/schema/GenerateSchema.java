package ru.gt2.gar.db.schema;

import jakarta.annotation.Nullable;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.domain.LengthLimit;
import ru.gt2.gar.domain.SchemaComment;
import ru.gt2.gar.domain.SchemaLink;

import java.lang.reflect.RecordComponent;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/// Генерация Yml-файлов для Liquibase
public class GenerateSchema {
    private final LiquibaseYmlWriter writer = new LiquibaseYmlWriter(System.out, NamingStrategy.LOWER_UNDERSCORE);
    private final Set<GarType> remainingTypes = EnumSet.allOf(GarType.class);

    public static void main(String[] args) {
        new GenerateSchema().generate();
    }

    private void generate() {
        writer.start(getClass().getSimpleName() + "(" + writer.getClass().getSimpleName() + ")");
        while (!remainingTypes.isEmpty()) {
            GarType garType = null;
            for (Iterator<GarType> remainingIterator = remainingTypes.iterator(); remainingIterator.hasNext(); ) {
                garType = remainingIterator.next();
                // Убедиться что все сущности на которые есть ссылки уже обработаны
                Optional<GarType> notProcessed = Arrays.stream(garType.recordClass.getRecordComponents())
                        .map(rc -> rc.getAnnotation(SchemaLink.class))
                        .filter(Objects::nonNull)
                        .map(SchemaLink::value)
                        .filter(remainingTypes::contains)
                        .findAny();
                if (notProcessed.isEmpty()) {
                    remainingIterator.remove();
                    break;
                }
            }
            generateTable(garType);
        }
        writer.end();
    }

    private void generateTable(GarType garType) {
        Class<? extends Record> recordClass = garType.recordClass;
        writer.startTable(garType.name(), recordClass.getAnnotation(SchemaComment.class).value());
        boolean primaryKey = true;
        for (RecordComponent rc : recordClass.getRecordComponents()) {
            writer.writeColumn(rc.getName(), rc.getAnnotation(SchemaComment.class).value(), getType(rc),
                    primaryKey, rc.isAnnotationPresent(Nullable.class));
            primaryKey = false;
            // TODO добавить внешние ключи, но только после проверки на то,
            //  что они будут работать для текущего набора данных
        }
        writer.endTable();
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
            return null;
        }
    }
}
