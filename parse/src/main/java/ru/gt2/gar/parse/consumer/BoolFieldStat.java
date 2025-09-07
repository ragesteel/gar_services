package ru.gt2.gar.parse.consumer;

import java.lang.reflect.RecordComponent;

public class BoolFieldStat extends AbstractFieldStat {
    private int falseCount = 0;
    private int trueCount = 0;

    public BoolFieldStat(RecordComponent recordComponent) {
        super(recordComponent);
    }

    @Override
    public void accept(Record record) {
            boolean value = (boolean) invokeAccessor(record);
            if (value) {
                trueCount++;
            } else {
                falseCount++;
            }
    }

    @Override
    public String toString() {
        return String.format("%s, bool, false: %d, true: %d", name, falseCount, trueCount);
    }
}
