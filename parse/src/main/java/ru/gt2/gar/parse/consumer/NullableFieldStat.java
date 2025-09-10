package ru.gt2.gar.parse.consumer;

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
    public void accept(Record record) {
        Object object = fieldStat.invokeAccessor(record);
        if (object == null) {
            nullCount++;
        } else {
            fieldStat.acceptValue(object);
        }
    }

    @Override
    public String toString() {
        String result = fieldStat.toString();
        if (0 == nullCount) {
            return result;
        }
        return result + ", nulls=" + nullCount;
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
