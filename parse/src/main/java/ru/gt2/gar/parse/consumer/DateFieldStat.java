package ru.gt2.gar.parse.consumer;

import java.lang.reflect.RecordComponent;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class DateFieldStat extends AbstractFieldStat {
    private final MinMaxStat<ChronoLocalDate> minMax;

    public DateFieldStat(RecordComponent recordComponent) {
        super(recordComponent);
        minMax = new MinMaxStat<>();
    }

    private DateFieldStat(String name, MinMaxStat<ChronoLocalDate> minMax) {
        super(name);
        this.minMax = minMax;
    }

    @Override
    public void acceptValue(Object value) {
        minMax.update((LocalDate) value);
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder(name).append(", date");
        return minMax.addTo(resultBuilder, " = ", ", ").toString();
    }

    @Override
    public DateFieldStat sum(FieldStat other) {
        if (!(other instanceof DateFieldStat dateField)) {
            throw new IllegalArgumentException("Sum must be called on equal types");
        }

        return new DateFieldStat(name, minMax.sum(dateField.minMax));
    }
}
