package ru.gt2.gar.parse.consumer;

import com.google.common.annotations.VisibleForTesting;

import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Formatter;

public class IntFieldStat extends AbstractFieldStat {
    private final MinMaxStat<Integer> minMax;

    public IntFieldStat(RecordComponent recordComponent) {
        this(recordComponent.getName(), recordComponent.getAccessor(), new MinMaxStat<>());
    }

    @VisibleForTesting
    protected IntFieldStat(String name, Method accessor, MinMaxStat<Integer> minMax) {
        super(name, accessor, "int");
        this.minMax = minMax;
    }

    @Override
    public void acceptValue(Object value) {
        minMax.update((int) value);
    }

    @Override
    public void format(Formatter formatter) {
        super.format(formatter);
        minMax.format(formatter, " = %,d", ", %,d … %,d");
    }

    @Override
    public IntFieldStat sum(FieldStat other) {
        if (!(other instanceof IntFieldStat intField)) {
            throw new IllegalArgumentException("Sum must be called on equal types");
        }

        return new IntFieldStat(name, accessor, minMax.sum(intField.minMax));
    }
}
