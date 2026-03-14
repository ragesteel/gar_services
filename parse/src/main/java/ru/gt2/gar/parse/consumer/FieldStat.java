package ru.gt2.gar.parse.consumer;

import ru.gt2.gar.domain.GarRecord;

import java.util.Formatter;
import java.util.function.Consumer;

public interface FieldStat extends Consumer<GarRecord> {
    String getName();

    // Сложить с другим и вернуть новый объект
    FieldStat sum(FieldStat other);

    void format(Formatter formatter);

    default String formatString() {
        StringBuilder sb = new StringBuilder();
        format(new Formatter(sb));
        return sb.toString();
    }
}
