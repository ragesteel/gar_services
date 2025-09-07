package ru.gt2.gar.parse.consumer;

import java.lang.reflect.RecordComponent;

public class IntFieldStat extends AbstractFieldStat {
    private int minValue = Integer.MAX_VALUE;
    private int maxValue =  Integer.MIN_VALUE;

    public IntFieldStat(RecordComponent recordComponent) {
        super(recordComponent);
    }

    @Override
    public void accept(Record record) {
            int value = (int) invokeAccessor(record);
            minValue = Math.min(minValue, value);
            maxValue = Math.max(maxValue, value);
    }

    @Override
    public String toString() {
        return String.format("%s, int, %d … %d", name, minValue, maxValue);
    }
}
