package ru.gt2.gar.parse.consumer;

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

    public StringBuilder addTo(StringBuilder sb, String prefix) {
        if (!hasMinMax) {
            return sb;
        }
        sb.append(prefix);
        if (min.compareTo(max) == 0) {
            sb.append('=').append(min);
        } else {
            sb.append(' ').append(min).append(" … ").append(max);
        }
        return sb;
    }
}
