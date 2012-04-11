package ru.gt2.rusref.ddl;

import ru.gt2.rusref.fias.Fias;

/**
 * Запускатель «велосипеда» по генерации Ddl.
 */
public class MySqlDdl {
    public static void main(String... args) {
        for (Fias fias : Fias.orderByReferences()) {
            MySqlTable table = new MySqlTable(fias);
            table.createTable();
        }

        for (Fias fias : Fias.orderByReferences()) {
            MySqlTable table = new MySqlTable(fias);
            table.loadData();
        }
    }

}
