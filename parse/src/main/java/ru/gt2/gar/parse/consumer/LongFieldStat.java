package ru.gt2.gar.parse.consumer;

import java.lang.reflect.RecordComponent;
import java.util.Formatter;

public class LongFieldStat extends AbstractFieldStat {
    private final MinMaxStat<Long> minMax;

    public LongFieldStat(RecordComponent recordComponent) {
        super(recordComponent, "long");
        minMax = new MinMaxStat<>();
    }

    private LongFieldStat(String name, MinMaxStat<Long> minMax) {
        super(name, "long");
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

        return new LongFieldStat(name, minMax.sum(longField.minMax));
    }
}
