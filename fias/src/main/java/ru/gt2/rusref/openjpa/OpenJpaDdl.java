package ru.gt2.rusref.openjpa;

import org.apache.openjpa.jdbc.meta.MappingTool;

import java.io.IOException;
import java.sql.SQLException;

public class OpenJpaDdl {
    public static void main(String... args) throws IOException, SQLException {
        MappingTool.main(new String[]{
                "-schemaAction", "build",
                "-sql", "create.sql",
                "-readSchema", "false",
                "-sqlEncode", "utf8"
        });
    }
}
