package ru.gt2.gar.parse.consumer;

import java.lang.reflect.RecordComponent;

public class LongFieldStat extends AbstractFieldStat {
    private final MinMaxStat<Long> minMax = new MinMaxStat<>();

    public LongFieldStat(RecordComponent recordComponent) {
        super(recordComponent);
    }

    @Override
    public void acceptValue(Object value) {
        minMax.update((long) value);
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder(name).append(", long");
        return minMax.addTo(resultBuilder, ", ").toString();
    }
}
