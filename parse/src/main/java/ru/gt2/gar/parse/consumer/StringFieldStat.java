package ru.gt2.gar.parse.consumer;

import java.lang.reflect.RecordComponent;

public class StringFieldStat extends AbstractFieldStat {
    private final MinMaxStat<Integer> minMaxLen = new MinMaxStat<>();
    private int emptyCount = 0;

    public StringFieldStat(RecordComponent recordComponent) {
        super(recordComponent);
    }

    @Override
    public void accept(Record record) {
        String value = (String) invokeAccessor(record);
        int valueLen = value.length();
        if (0 == valueLen) {
            emptyCount++;
        } else {
            minMaxLen.update(valueLen);
        }
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder(name).append(", string");
        minMaxLen.addTo(resultBuilder, ", length");
        if (emptyCount > 0) {
            resultBuilder.append(", empty=").append(emptyCount);
        }
        return resultBuilder.toString();
    }
}
