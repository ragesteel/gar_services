package ru.gt2.gar.parse.consumer;

import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Formatter;

// TODO попробовать сделать RangedFieldStat<T extends Comparable<T>> в качестве базы для Date, Int и Long
public class DateFieldStat extends AbstractFieldStat {
    private final MinMaxStat<ChronoLocalDate> minMax;

    public DateFieldStat(RecordComponent recordComponent) {
        this(recordComponent.getName(), recordComponent.getAccessor(), new MinMaxStat<>());
    }

    private DateFieldStat(String name, Method accessor, MinMaxStat<ChronoLocalDate> minMax) {
        super(name, accessor, "date");
        this.minMax = minMax;
    }

    @Override
    public void acceptValue(Object value) {
        minMax.update((LocalDate) value);
    }

    @Override
    public void format(Formatter formatter) {
        super.format(formatter);
        minMax.format(formatter, " = %s", ", %s … %s");
    }

    @Override
    public DateFieldStat sum(FieldStat other) {
        if (!(other instanceof DateFieldStat dateField)) {
            throw new IllegalArgumentException("Sum must be called on equal types");
        }

        return new DateFieldStat(name, accessor, minMax.sum(dateField.minMax));
    }
}
