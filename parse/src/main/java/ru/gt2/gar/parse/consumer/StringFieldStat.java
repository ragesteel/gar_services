package ru.gt2.gar.parse.consumer;

import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Formatter;

// TODO проверять возможность замены на число
public class StringFieldStat extends AbstractFieldStat {
    private final MinMaxStat<Integer> minMaxLen;
    private int emptyCount;

    public StringFieldStat(RecordComponent recordComponent) {
        this(recordComponent.getName(), recordComponent.getAccessor(), new MinMaxStat<>(), 0);
    }

    private StringFieldStat(String name, Method accessor, MinMaxStat<Integer> minMaxLen, int emptyCount) {
        super(name, accessor, "string");
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
    public void format(Formatter formatter) {
        super.format(formatter);
        minMaxLen.format(formatter, ", length = %,d", ", length %,d … %,d");
        if (emptyCount > 0) {
            formatter.format(", empty=%,d", emptyCount);
        }
    }

    @Override
    public StringFieldStat sum(FieldStat other) {
        if (!(other instanceof StringFieldStat stringField)) {
            throw new IllegalArgumentException("Sum must be called on equal types");
        }

        return new StringFieldStat(name, accessor, minMaxLen.sum(stringField.minMaxLen), emptyCount + stringField.emptyCount);
    }
}
