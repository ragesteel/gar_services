package ru.gt2.rusref.stat;

import com.google.common.collect.Maps;
import lombok.ToString;
import ru.gt2.rusref.fias.Fias;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * Представление результата импорта.
 */
@ToString
public class ExtractResult {
    /** Количество загруженных элементов. */
    private int itemCount;

    private int invalidCount;

    /** Статистика по полям. */
    private final Map<String, ObjectFieldStatistics> statistics;

    private final Validator validator;

    public ExtractResult(Fias fias, Validator validator) {
        statistics = Maps.newLinkedHashMap();
        for (Field field : fias.itemFields) {
            statistics.put(field.getName(), ObjectFieldStatistics.newFieldStatistics(field, validator));
        }

        this.validator = validator;
    }
    
    public void updateStatistics(Object item) {
        itemCount++;
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(item);
        if (!constraintViolations.isEmpty()) {
            invalidCount++;
        }

        for (ObjectFieldStatistics fieldStatistics : statistics.values()) {
            fieldStatistics.updateStatistics(item);
        }
    }

    public void print(PrintStream printStream) {
        printStream.print("Total item count: " + itemCount);
        if (0 != invalidCount) {
            printStream.print(", not valid: " + invalidCount);
        }
        printStream.println();
        for (ObjectFieldStatistics fieldStatistics : statistics.values()) {
            printStream.print("  " + fieldStatistics.getFieldName() + ": ");
            fieldStatistics.print(printStream);
            printStream.println();
        }
    }
}
