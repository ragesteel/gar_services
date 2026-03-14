package ru.gt2.gar.parse.consumer;

import org.jspecify.annotations.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;

public class LongFieldStat extends RangedFieldStat<Long> {
    public LongFieldStat(RecordComponent recordComponent) {
        this(recordComponent.getName(), recordComponent.getAccessor(), null);
    }

    private LongFieldStat(String name, Method accessor, @Nullable MinMaxStat<Long> minMax) {
        super(name, accessor, minMax, "long", ",d");
    }

    @Override
    public LongFieldStat sum(FieldStat other) {
        if (!(other instanceof LongFieldStat longField)) {
            throw new IllegalArgumentException("Sum must be called on equal types");
        }

        return new LongFieldStat(name, accessor, sumMinMax(longField));
    }
}
