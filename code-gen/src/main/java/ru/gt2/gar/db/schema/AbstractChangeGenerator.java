package ru.gt2.gar.db.schema;

import lombok.RequiredArgsConstructor;
import ru.gt2.gar.db.NamingStrategy;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public abstract class AbstractChangeGenerator {
    private final String fileName;

    public void generate() throws IOException {
        try (PrintStream printStream = new PrintStream(fileName, StandardCharsets.UTF_8)) {
            LiquibaseYmlWriter liquibaseYmlWriter = new LiquibaseYmlWriter(printStream);
            generate(liquibaseYmlWriter, new DatabaseSchema(NamingStrategy.LOWER_UNDERSCORE));
        }
    }

    protected abstract void generate(LiquibaseYmlWriter liquibaseYmlWriter, DatabaseSchema databaseSchema);

    protected void generateStart(LiquibaseYmlWriter writer) {
        writer.start(getClass().getSimpleName() + "(" + writer.getClass().getSimpleName() + ")");
    }
}
