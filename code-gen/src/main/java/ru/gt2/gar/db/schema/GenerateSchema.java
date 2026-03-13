package ru.gt2.gar.db.schema;

import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.domain.SchemaLink;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/// TODO Добавить отдельные комментарии таблицам с сущностями {@link ru.gt2.gar.domain.Param}
public class GenerateSchema extends AbstractChangeGenerator {
    private final Set<GarType> remainingTypes = EnumSet.allOf(GarType.class);

    public GenerateSchema() {
        super("db/src/main/resources/db/changelog/db.changelog-generated.yml");
    }

    static void main() throws Exception {
        new GenerateSchema().generate();
    }

    @Override
    public void generate(LiquibaseYmlWriter writer, DatabaseSchema databaseSchema) {
        generateStart(writer);
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
            databaseSchema.createTable(garType, writer);
        }
        writer.end();
    }
}
