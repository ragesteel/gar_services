package ru.gt2.gar.parse.consumer;

import java.lang.reflect.RecordComponent;

public class StringFieldStat extends AbstractFieldStat {
    private int maxLen = 0;
    private int minLen = Integer.MAX_VALUE;
    private boolean hasMinMax = false;

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
            minLen = Math.min(minLen, valueLen);
            maxLen = Math.max(maxLen, valueLen);
            hasMinMax = true;
        }
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder(name).append(", string");
        if (hasMinMax) {
            resultBuilder.append(", length ").append(minLen).append(" … ").append(maxLen);
        }
        if (emptyCount > 0) {
            resultBuilder.append(", empty=").append(emptyCount);
        }
        return resultBuilder.toString();
    }
}
