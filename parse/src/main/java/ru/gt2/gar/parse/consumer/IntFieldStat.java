package ru.gt2.gar.parse.consumer;

import com.google.common.annotations.VisibleForTesting;

import java.lang.reflect.RecordComponent;

public class IntFieldStat extends AbstractFieldStat {
    private final MinMaxStat<Integer> minMax;

    public IntFieldStat(RecordComponent recordComponent) {
        super(recordComponent);
        minMax = new MinMaxStat<>();
    }

    @VisibleForTesting
    protected IntFieldStat(String name, MinMaxStat<Integer> minMax) {
        super(name);
        this.minMax = minMax;
    }

    @Override
    public void acceptValue(Object value) {
        minMax.update((int) value);
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder(name).append(", int");
        return minMax.addTo(resultBuilder, " = ", ", ").toString();
    }

    @Override
    public IntFieldStat sum(FieldStat other) {
        if (!(other instanceof IntFieldStat intField)) {
            throw new IllegalArgumentException("Sum must be called on equal types");
        }

        return new IntFieldStat(name, minMax.sum(intField.minMax));
    }
}
