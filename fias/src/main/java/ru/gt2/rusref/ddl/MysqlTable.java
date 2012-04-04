package ru.gt2.rusref.ddl;

import ru.gt2.rusref.fias.Fias;

import java.lang.reflect.Field;

/**
 * Создание MySQL-таблицы.
 *
 * Конечно, если это будет развиваться, нужно будет извлечь базовый класс и всякое такое.
 */
public class MysqlTable {
    private final Fias fias;

    public MysqlTable(Fias fias) {
        this.fias = fias;
    }

    public void generate() {
        startTable();
        for (Field field : fias.itemFields) {
            createField(field);
        }
        endTable();
    }

    public void startTable() {

    };

    public void createField(Field field) {

    };

    public void endTable() {

    };

}
