package ru.gt2.gar.parse.consumer;

import java.lang.reflect.RecordComponent;

public class BoolFieldStat extends AbstractFieldStat {
    private int falseCount = 0;
    private int trueCount = 0;

    public BoolFieldStat(RecordComponent recordComponent) {
        super(recordComponent);
    }

    @Override
    public void acceptValue(Object value) {
            if ((boolean) value) {
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
