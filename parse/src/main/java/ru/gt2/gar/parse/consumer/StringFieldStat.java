package ru.gt2.gar.parse.consumer;

import ru.gt2.gar.domain.LengthLimit;

import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Formatter;

public class StringFieldStat extends AbstractFieldStat {
    private final Integer lengthLimit;
    private final MinMaxStat<Integer> minMaxLen;
    private int emptyCount;

    public StringFieldStat(RecordComponent recordComponent) {
        this(recordComponent.getName(), recordComponent.getAccessor(), extractLengthLimit(recordComponent),
                new MinMaxStat<>(), 0);
    }

    private StringFieldStat(String name, Method accessor, Integer lengthLimit, MinMaxStat<Integer> minMaxLen, int emptyCount) {
        super(name, accessor, "string");
        this.lengthLimit = lengthLimit;
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

        if (null == lengthLimit) {
            return;
        }
        minMaxLen.getMax()
                .filter(maxLen -> maxLen > lengthLimit)
                .ifPresent(_ ->
                        formatter.format(", WARNING: max length is greater than limit (%,d)", lengthLimit));
    }

    @Override
    public StringFieldStat sum(FieldStat other) {
        if (!(other instanceof StringFieldStat stringField)) {
            throw new IllegalArgumentException("Sum must be called on equal types");
        }

        return new StringFieldStat(name, accessor, lengthLimit,
                minMaxLen.sum(stringField.minMaxLen), emptyCount + stringField.emptyCount);
    }

    private static Integer extractLengthLimit(RecordComponent recordComponent) {
        LengthLimit annotation = recordComponent.getAnnotation(LengthLimit.class);
        if (null == annotation) {
            return null;
        }
        return annotation.value();
    }
}