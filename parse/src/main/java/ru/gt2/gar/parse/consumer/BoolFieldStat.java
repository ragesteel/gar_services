package ru.gt2.gar.parse.consumer;

import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Formatter;

public class BoolFieldStat extends AbstractFieldStat {
    private int falseCount;
    private int trueCount;

    public BoolFieldStat(RecordComponent recordComponent) {
        this(recordComponent.getName(), recordComponent.getAccessor(), 0, 0);
    }

    private BoolFieldStat(String name, Method accessor, int falseCount, int trueCount) {
        super(name, accessor, "bool");
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
    public void format(Formatter formatter) {
        super.format(formatter);
        formatter.format(", false: %,d, true: %,d", falseCount, trueCount);
    }

    @Override
    public BoolFieldStat sum(FieldStat other) {
        if (!(other instanceof BoolFieldStat otherBool)) {
            throw new IllegalArgumentException("Sum must be called on equal types");
        }

        return new BoolFieldStat(name, accessor, otherBool.falseCount + falseCount, otherBool.trueCount + trueCount);
    }
}
