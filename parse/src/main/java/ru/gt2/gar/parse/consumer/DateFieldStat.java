package ru.gt2.gar.parse.consumer;

import org.jspecify.annotations.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.time.chrono.ChronoLocalDate;

public class DateFieldStat extends RangedFieldStat<ChronoLocalDate> {

    public DateFieldStat(RecordComponent recordComponent) {
        this(recordComponent.getName(), recordComponent.getAccessor(), null);
    }

    private DateFieldStat(String name, Method accessor, @Nullable MinMaxStat<ChronoLocalDate> minMax) {
        super(name, accessor, minMax, "date", "s");
    }

    @Override
    public DateFieldStat sum(FieldStat other) {
        if (!(other instanceof DateFieldStat dateField)) {
            throw new IllegalArgumentException("Sum must be called on equal types");
        }
        return new DateFieldStat(name, accessor, sumMinMax(dateField));
    }
}
