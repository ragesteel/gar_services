package ru.gt2.gar.parse.consumer;

import java.lang.reflect.RecordComponent;

public class LongFieldStat extends AbstractFieldStat {
    private final MinMaxStat<Long> minMax;

    public LongFieldStat(RecordComponent recordComponent) {
        super(recordComponent);
        minMax = new MinMaxStat<>();
    }

    private LongFieldStat(String name, MinMaxStat<Long> minMax) {
        super(name);
        this.minMax = minMax;
    }

    @Override
    public void acceptValue(Object value) {
        minMax.update((long) value);
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder(name).append(", long");
        return minMax.addTo(resultBuilder, " = ", ", ").toString();
    }

    @Override
    public LongFieldStat sum(FieldStat other) {
        if (!(other instanceof LongFieldStat longField)) {
            throw new IllegalArgumentException("Sum must be called on equal types");
        }

        return new LongFieldStat(name, minMax.sum(longField.minMax));
    }
}
