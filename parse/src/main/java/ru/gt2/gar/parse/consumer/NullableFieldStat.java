package ru.gt2.gar.parse.consumer;

import ru.gt2.gar.domain.GarRecord;

import java.util.Formatter;

public class NullableFieldStat implements FieldStat {
    private final AbstractFieldStat fieldStat;

    private int nullCount = 0;

    public NullableFieldStat(AbstractFieldStat fieldStat) {
        this.fieldStat = fieldStat;
    }

    private NullableFieldStat(FieldStat fieldStat, int nullCount) {
        this.fieldStat = (AbstractFieldStat) fieldStat;
        this.nullCount = nullCount;
    }

    @Override
    public String getName() {
        return fieldStat.getName();
    }

    @Override
    public void accept(GarRecord record) {
        Object object = fieldStat.invokeAccessor(record);
        if (object == null) {
            nullCount++;
        } else {
            fieldStat.acceptValue(object);
        }
    }

    @Override
    public void format(Formatter formatter) {
        fieldStat.format(formatter);
        if (0 == nullCount) {
            formatter.format(", no null values");
        } else {
            formatter.format(", %,d null value(s)", nullCount);
        }
    }

    @Override
    public NullableFieldStat sum(FieldStat other) {
        if (!(other instanceof NullableFieldStat nullableField)) {
            throw new IllegalArgumentException("Sum must be called on equal types");
        }

        return new NullableFieldStat(nullableField.fieldStat.sum(fieldStat),
                nullCount + nullableField.nullCount);
    }
}
