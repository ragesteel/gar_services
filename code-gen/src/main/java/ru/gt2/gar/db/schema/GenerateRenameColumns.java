package ru.gt2.gar.db.schema;

import ru.gt2.gar.domain.GarType;

import java.io.IOException;

/// Создание YML для переименования колонок
public class GenerateRenameColumns extends AbstractChangeGenerator {
    public GenerateRenameColumns() {
        super("db/src/main/resources/db/changelog/db.changelog-generated-rename.yml");

    }

    static void main() throws IOException {
        new GenerateRenameColumns().generate();
    }

    @Override
    protected void generate(LiquibaseYmlWriter liquibaseYmlWriter, DatabaseSchema databaseSchema) {
        generateStart(liquibaseYmlWriter);
        for (GarType value : GarType.values()) {
            databaseSchema.renameColumn(value, liquibaseYmlWriter);
        }
    }
}
