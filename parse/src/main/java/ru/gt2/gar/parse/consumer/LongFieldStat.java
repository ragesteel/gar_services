package ru.gt2.gar.parse.consumer;

import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Formatter;

public class LongFieldStat extends AbstractFieldStat {
    private final MinMaxStat<Long> minMax;

    public LongFieldStat(RecordComponent recordComponent) {
        this(recordComponent.getName(), recordComponent.getAccessor(), new MinMaxStat<>());
    }

    private LongFieldStat(String name, Method accessor, MinMaxStat<Long> minMax) {
        super(name, accessor, "long");
        this.minMax = minMax;
    }

    @Override
    public void acceptValue(Object value) {
        minMax.update((long) value);
    }

    @Override
    public void format(Formatter formatter) {
        super.format(formatter);
        minMax.format(formatter, " = %,d", ", %,d … %,d");
    }

    @Override
    public LongFieldStat sum(FieldStat other) {
        if (!(other instanceof LongFieldStat longField)) {
            throw new IllegalArgumentException("Sum must be called on equal types");
        }

        return new LongFieldStat(name, accessor, minMax.sum(longField.minMax));
    }
}
