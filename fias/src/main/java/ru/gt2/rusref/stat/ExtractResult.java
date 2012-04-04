package ru.gt2.rusref.stat;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import ru.gt2.rusref.Description;
import ru.gt2.rusref.FieldType;
import ru.gt2.rusref.Joiners;
import ru.gt2.rusref.fias.Fias;

import javax.annotation.Nullable;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * Представление результата импорта.
 *
 * Что-то включение валидации как-то серьёзно начало тормозить проект.
 */
public class ExtractResult {

    private final Fias fias;
    /** Количество загруженных элементов. */
    private int itemCount;

    private int invalidCount;

    /** Статистика по полям. */
    private final Map<String, ObjectFieldStatistics> statistics;

    private final Validator validator;

    private static final Function<ConstraintViolation<Object>, String> CONSTRAINT_VIOLATION_TO_PROPERTY_PATH =
            new Function<ConstraintViolation<Object>, String>() {
                @Override
                public String apply(@Nullable ConstraintViolation<Object> constraintViolation) {
                    if (null != constraintViolation) {
                        return String.valueOf(constraintViolation.getPropertyPath());
                    }
                    return null;
                }
            };

    public ExtractResult(Fias fias, Validator validator) {
        this.fias = fias;
        statistics = Maps.newLinkedHashMap();
        for (Field field : fias.itemFields) {
            FieldType fieldType = FieldType.FROM_TYPE.get(field.getType());
            statistics.put(field.getName(), fieldType.createFieldStatistics(field));
        }

        this.validator = validator;
    }
    
    public void updateStatistics(Object item) {
        itemCount++;
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(item);
        ImmutableSet<String> violatedFields;
        boolean notValid = !constraintViolations.isEmpty();
        if (notValid) {
            invalidCount++;
            violatedFields = ImmutableSet.copyOf(
                    Iterables.transform(constraintViolations, CONSTRAINT_VIOLATION_TO_PROPERTY_PATH));
        } else {
            violatedFields = ImmutableSet.of();
        }

        for (ObjectFieldStatistics fieldStatistics : statistics.values()) {
            boolean fieldViolated = violatedFields.contains(fieldStatistics.getFieldName());
            fieldStatistics.updateStatistics(item, fieldViolated);
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

    public void writeReport(PrintWriter printWriter) {
        Class<?> item = fias.item;
        printWriter.println(Joiners.COMMA_SEPARATED.join(
                fias.name(),
                item.getSimpleName(),
                item.getAnnotation(Description.class).value(),
                null,
                itemCount,
                null,
                null,
                null
        ));
        for (ObjectFieldStatistics fieldStatistics : statistics.values()) {
            // FieldType fieldType = FieldType.FROM_TYPE.get(field.getType());
            Object[] parts = new Object[11];
            fieldStatistics.fillReportParts(parts);
            printWriter.println(Joiners.COMMA_SEPARATED.join(parts));
        }
    }
}
