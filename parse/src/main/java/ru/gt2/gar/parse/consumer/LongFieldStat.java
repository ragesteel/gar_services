package ru.gt2.gar.parse.consumer;

import java.lang.reflect.RecordComponent;

public class LongFieldStat extends AbstractFieldStat {

    private long minValue = Long.MAX_VALUE;
    private long maxValue = Long.MIN_VALUE;

    public LongFieldStat(RecordComponent recordComponent) {
        super(recordComponent);
    }

    @Override
    public void accept(Record record) {
        long value = (long) invokeAccessor(record);
        minValue = Math.min(minValue, value);
        maxValue = Math.max(maxValue, value);
    }

    @Override
    public String toString() {
        return String.format("%s, long, %d … %d", name, minValue, maxValue);
    }
}
