package ru.gt2.gar.parse.consumer;

import com.google.common.annotations.VisibleForTesting;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;

public class IntFieldStat extends  RangedFieldStat<Integer> {
    public IntFieldStat(RecordComponent recordComponent) {
        this(recordComponent.getName(), recordComponent.getAccessor(), null);
    }

    @VisibleForTesting
    protected IntFieldStat(String name, Method accessor, @Nullable MinMaxStat<Integer> minMax) {
        super(name, accessor, minMax, "int", ",d");
    }

    @Override
    public IntFieldStat sum(FieldStat other) {
        if (!(other instanceof IntFieldStat intField)) {
            throw new IllegalArgumentException("Sum must be called on equal types");
        }

        return new IntFieldStat(name, accessor, sumMinMax(intField));
    }
}
