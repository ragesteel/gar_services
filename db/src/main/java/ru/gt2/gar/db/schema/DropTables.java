package ru.gt2.gar.db.schema;

import ru.gt2.gar.domain.GarType;

import java.util.Arrays;
import java.util.stream.Collectors;

/// Создание SQL для удаления всех таблиц, пока просто по порядку
public class DropTables {
    static void main() {
        System.out.println("DROP TABLE " + Arrays.stream(GarType.values()).map(GarType::name)
                .map(NamingStrategy.LOWER_UNDERSCORE::getTableName)
                .collect(Collectors.joining(", ")));
    }
}
