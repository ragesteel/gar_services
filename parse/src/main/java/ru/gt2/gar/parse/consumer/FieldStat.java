package ru.gt2.gar.parse.consumer;

import ru.gt2.gar.domain.GarRecord;

import java.util.function.Consumer;

public interface FieldStat extends Consumer<GarRecord> {
    String getName();

    // Сложить с другим и вернуть новый объект
    FieldStat sum(FieldStat other);
}
