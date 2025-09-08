package ru.gt2.gar.parse.consumer;

import java.lang.reflect.RecordComponent;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class DateFieldStat extends AbstractFieldStat {
    private final MinMaxStat<ChronoLocalDate> minMax = new MinMaxStat<>();

    public DateFieldStat(RecordComponent recordComponent) {
        super(recordComponent);
    }

    @Override
    public void acceptValue(Object value) {
        minMax.update((LocalDate) value);
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder(name).append(", date");
        return minMax.addTo(resultBuilder, ",").toString();
    }
}
