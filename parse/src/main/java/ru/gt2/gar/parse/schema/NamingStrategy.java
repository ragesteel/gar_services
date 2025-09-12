package ru.gt2.gar.parse.schema;

public interface NamingStrategy {
    LowerUnderscoreNamingStrategy LOWER_UNDERSCORE = new LowerUnderscoreNamingStrategy();

    String getTableName(String upperUnderscore);

    String getColumnName(String lowerCamel);
}
