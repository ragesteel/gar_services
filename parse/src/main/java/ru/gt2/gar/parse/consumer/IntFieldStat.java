package ru.gt2.gar.parse.consumer;

import java.lang.reflect.RecordComponent;

public class IntFieldStat extends AbstractFieldStat {
    private final MinMaxStat<Integer> minMax = new MinMaxStat<>();

    public IntFieldStat(RecordComponent recordComponent) {
        super(recordComponent);
    }

    @Override
    public void accept(Record record) {
        int value = (int) invokeAccessor(record);
        minMax.update(value);
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder(name).append(", int");
        return minMax.addTo(resultBuilder, ", ").toString();
    }
}
