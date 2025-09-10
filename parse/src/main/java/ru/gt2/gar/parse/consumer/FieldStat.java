package ru.gt2.gar.parse.consumer;

import java.util.function.Consumer;

public interface FieldStat extends Consumer<Record> {
    String getName();

    // Сложить с другим и вернуть новый объект
    FieldStat sum(FieldStat other);
}
