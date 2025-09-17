package ru.gt2.gar.db.schema;

import lombok.RequiredArgsConstructor;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.domain.SchemaLink;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/// Генерация Yml-файлов для Liquibase
/// TODO Добавить отдельные комментарии таблицам с сущностями {@link ru.gt2.gar.domain.Param}
@RequiredArgsConstructor
public class GenerateSchema {
    private final LiquibaseYmlWriter writer;
    private final DatabaseSchema databaseSchema;
    private final Set<GarType> remainingTypes = EnumSet.allOf(GarType.class);

    public static void main(String[] args) throws Exception {
        try (PrintStream printStream = new PrintStream(
                "db/src/main/resources/db/changelog/db.changelog-generated.yml", StandardCharsets.UTF_8)) {
            LiquibaseYmlWriter liquibaseYmlWriter = new LiquibaseYmlWriter(printStream);
            new GenerateSchema(liquibaseYmlWriter, new DatabaseSchema(NamingStrategy.LOWER_UNDERSCORE)).generate();
        }
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
            databaseSchema.visitTable(garType, writer);
        }
        writer.end();
    }
}
