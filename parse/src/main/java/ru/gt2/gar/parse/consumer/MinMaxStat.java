package ru.gt2.gar.parse.consumer;

import java.util.Formatter;
import java.util.Optional;

/**
 * Подсчёт и преобразование в строку минимального и максимального значения.
 *
 * @param <T>
 */
public class MinMaxStat<T extends Comparable<T>> {
    private T min;
    private T max;
    private boolean hasMinMax = false;

    public MinMaxStat() {
    }

    private MinMaxStat(MinMaxStat<T> other) {
        min = other.min;
        max = other.max;
        hasMinMax = other.hasMinMax;
    }

    public void update(T value) {
        if (!hasMinMax) {
            min = value;
            max = value;
            hasMinMax = true;
            return;
        }
        if (value.compareTo(min) < 0) {
            min = value;
        }
        if (value.compareTo(max) > 0) {
            max = value;
        }
    }

    public void format(Formatter formatter, String equalsFormat, String rangeFormat) {
        if (!hasMinMax) {
            return;
        }

        if (min.compareTo(max) == 0) {
            formatter.format(equalsFormat, min);
        } else {
            formatter.format(rangeFormat, min, max);
        }
    }

    public MinMaxStat<T> sum(MinMaxStat<T> other) {
        if (!hasMinMax && !other.hasMinMax) {
            return new MinMaxStat<>();
        } else if (!hasMinMax) {
            return new MinMaxStat<>(other);
        } else if (!other.hasMinMax) {
            return new MinMaxStat<>(this);
        }

        MinMaxStat<T> result = new MinMaxStat<>();
        result.min = min.compareTo(other.min) < 0 ? min : other.min;
        result.max = max.compareTo(other.max) > 0 ? max : other.max;
        result.hasMinMax = true;

        return result;
    }

    public Optional<T> getMax() {
        return hasMinMax ? Optional.of(max) : Optional.empty();
    }
}
