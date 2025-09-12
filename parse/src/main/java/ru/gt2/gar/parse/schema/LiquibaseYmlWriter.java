package ru.gt2.gar.parse.schema;

import com.google.common.base.CaseFormat;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.YEAR;

public class LiquibaseYmlWriter {
    private static final DateTimeFormatter ID_DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
            .appendLiteral('-')
            .appendValue(MONTH_OF_YEAR, 2)
            .appendLiteral('-')
            .appendValue(DAY_OF_MONTH, 2)
            .appendLiteral('_')
            .appendValue(HOUR_OF_DAY, 2)
            .appendLiteral('-')
            .appendValue(MINUTE_OF_HOUR, 2)
            .toFormatter();

    private final PrintStream out;

    public LiquibaseYmlWriter(PrintStream out) {
        this.out = out;
    }

    // https://docs.liquibase.com/pro/user-guide/what-is-a-changeset
    public void start(String author) {
        print(0, "# Copy to src/main/resources/db/changelog/db.changelog-master.yaml");
        print(0, "# And add dependency on org.liquibase:liquibase-core to your build file");
        print(0,"databaseChangeLog:");
        print(2, "- changeSet:");
        print(6, "id: " + ID_DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        print(6, "author: " + author);
    }

    public void end() {
        // nothing
    }

    private void print(int indent, String line) {
        out.println(" ".repeat(indent) + line);
    }

    // https://docs.liquibase.com/reference-guide/change-types/create-table
    public void startTable(String name, String comment) {
        print(8, "- createTable:");
        print(12, "tableName: " + processTableName(name));
        print(12, "remarks: " + comment);
        print(12, "columns:");
    }

    public void endTable() {
        // nothing
    }

    // https://docs.liquibase.com/reference-guide/change-types/column
    public void writeColumn(String name, String comment, String type, boolean primaryKey, boolean nullable) {
        print(14, "- column:");
        print(18, "name: " + processColumnName(name));
        print(18, "remarks: " + comment);
        print(18, "type: " + type);
        print(18, "constraints:");
        if (primaryKey) {
            print(20, "primaryKey: true");
        }
        print(20, "nullable: " + nullable);
    }

    private String processTableName(String name) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, name);
    }

    private String processColumnName(String name) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
    }
}
