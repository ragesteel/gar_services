package ru.gt2.rusref.stat;

import lombok.Getter;
import ru.gt2.rusref.Joiners;

import javax.annotation.Nullable;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Date;

/**
 * Статистика по полю.
 */
public class ObjectFieldStatistics<T> {
    private final Field field;

    @Getter
    private final String fieldName;

    protected int nullCount;

    protected int notNullCount;

    protected int notValidCount;

    public ObjectFieldStatistics(Field field) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        this.field = field;
        fieldName = field.getName();
    }

    public final void updateStatistics(Object obj, boolean violated) {
        if (violated) {
            notValidCount++;
        }

        try {
            T value = (T) field.get(obj);
            doUpdateStatistics(value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to get access to field", e);
        }
    }
    
    public void print(PrintStream printStream) {
        printStream.print("notNullCount = " + notNullCount + ", nullCount = " + nullCount);
        if (notValidCount > 0) {
            printStream.print(", notValidCount = " + notValidCount);
        }
    }

    public void fillReportParts(Object[] parts) {
        parts[1] = fieldName;
        parts[2] = field.getType().getSimpleName();
        if (notNullCount > 0) {
            parts[3] = notNullCount;
        }
    }

    public void fillRanges(Object[] parts, int min, int max, BigInteger sum) {
        if (0 == notNullCount) {
            return;
        }
        parts[4] = min;
        parts[5] = max;
        parts[6] = getAverage(sum);
    }

    protected void doUpdateStatistics(T value) {
        if (null == value) {
            nullCount++;
        } else {
            notNullCount++;
        }
    }

    @Nullable
    protected BigDecimal getAverage(BigInteger sum) {
        if (0 == notNullCount) {
            return null;
        }
        return new BigDecimal(sum).divide(BigDecimal.valueOf(notNullCount), 2, RoundingMode.HALF_UP);
    }
}
