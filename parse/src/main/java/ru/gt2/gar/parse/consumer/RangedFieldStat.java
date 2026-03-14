package ru.gt2.gar.parse.consumer;

import java.lang.reflect.Method;
import java.util.Formatter;
import java.util.Optional;

public abstract class RangedFieldStat<T extends Comparable<T>> extends AbstractFieldStat {
    private final MinMaxStat<T> minMax;
    private final String equalsFormat;
    private final String rangeFormat;

    protected RangedFieldStat(String name, Method accessor, MinMaxStat<T> minMax,
                              String typeName, String itemFormat) {
        super(name, accessor, typeName);
        this.minMax = Optional.ofNullable(minMax).orElseGet(MinMaxStat::new);
        this.equalsFormat = " = %" + itemFormat;
        this.rangeFormat = ", %" + itemFormat + " … %" + itemFormat;
    }

    @Override
    public void acceptValue(Object value) {
        minMax.update((T) value);
    }

    @Override
    public void format(Formatter formatter) {
        super.format(formatter);
        minMax.format(formatter, equalsFormat, rangeFormat);
    }

    protected MinMaxStat<T> sumMinMax(RangedFieldStat<T> other) {
        return minMax.sum(other.minMax);
    }
}
