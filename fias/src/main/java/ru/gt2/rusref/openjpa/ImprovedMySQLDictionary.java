package ru.gt2.rusref.openjpa;

import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.sql.MySQLDictionary;

/**
 * Небольшое расширение MySQLDictionary.
 */
public class ImprovedMySQLDictionary extends MySQLDictionary {
    public ImprovedMySQLDictionary() {
        supportsComments = true;
        tableType = null;
    }

    @Override
    public String[] getCreateTableSQL(Table table) {
        System.out.println(table.getComment());
        return super.getCreateTableSQL(table);
    }
}
