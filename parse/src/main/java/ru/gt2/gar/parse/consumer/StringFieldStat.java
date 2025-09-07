package ru.gt2.gar.parse.consumer;

import java.lang.reflect.RecordComponent;

public class StringFieldStat extends AbstractFieldStat {
    private int maxLen = 0;
    private int minLen = Integer.MAX_VALUE;

    public StringFieldStat(RecordComponent recordComponent) {
        super(recordComponent);
    }

    @Override
    public void accept(Record record) {
        String value = (String) invokeAccessor(record);
        int valueLen = value.length();
        minLen = Math.min(minLen, valueLen);
        maxLen = Math.max(maxLen, valueLen);
    }

    @Override
    public String toString() {
        return String.format("%s, string, length %d … %d", name, minLen, maxLen);
    }
}
