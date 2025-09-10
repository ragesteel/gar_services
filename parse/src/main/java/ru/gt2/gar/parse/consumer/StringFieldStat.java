package ru.gt2.gar.parse.consumer;

import java.lang.reflect.RecordComponent;

public class StringFieldStat extends AbstractFieldStat {
    private final MinMaxStat<Integer> minMaxLen;
    private int emptyCount = 0;

    public StringFieldStat(RecordComponent recordComponent) {
        super(recordComponent);
        minMaxLen = new MinMaxStat<>();
    }

    private StringFieldStat(String name, MinMaxStat<Integer> minMaxLen, int emptyCount) {
        super(name);
        this.minMaxLen = minMaxLen;
        this.emptyCount = emptyCount;
    }

    @Override
    public void acceptValue(Object value) {
        String string = (String) value;
        int valueLen = string.length();
        if (0 == valueLen) {
            emptyCount++;
        } else {
            minMaxLen.update(valueLen);
        }
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder(name).append(", string");
        minMaxLen.addTo(resultBuilder, ", length=", ", length ");
        if (emptyCount > 0) {
            resultBuilder.append(", empty=").append(emptyCount);
        }
        return resultBuilder.toString();
    }

    @Override
    public StringFieldStat sum(FieldStat other) {
        if (!(other instanceof StringFieldStat stringField)) {
            throw new IllegalArgumentException("Sum must be called on equal types");
        }

        return new StringFieldStat(name, minMaxLen.sum(stringField.minMaxLen), emptyCount + stringField.emptyCount);
    }
}
