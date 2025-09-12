package ru.gt2.gar.parse.schema;

import com.google.common.base.CaseFormat;

public final class LowerUnderscoreNamingStrategy implements NamingStrategy{

    LowerUnderscoreNamingStrategy() {
    }

    @Override
    public String getTableName(String upperUnderscore) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, upperUnderscore);
    }

    @Override
    public String getColumnName(String lowerCamel) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, lowerCamel);
    }
}
