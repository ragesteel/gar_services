package ru.gt2.gar.parse.consumer;

import java.lang.reflect.RecordComponent;

public class BoolFieldStat extends AbstractFieldStat {
    private int falseCount = 0;
    private int trueCount = 0;

    public BoolFieldStat(RecordComponent recordComponent) {
        super(recordComponent);
    }

    private BoolFieldStat(String name, int falseCount, int trueCount) {
        super(name);
        this.falseCount = falseCount;
        this.trueCount = trueCount;
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

    @Override
    public BoolFieldStat sum(FieldStat other) {
        if (!(other instanceof BoolFieldStat otherBool)) {
            throw new IllegalArgumentException("Sum must be called on equal types");
        }

        return new BoolFieldStat(name, otherBool.falseCount + falseCount, otherBool.trueCount + trueCount);
    }
}
