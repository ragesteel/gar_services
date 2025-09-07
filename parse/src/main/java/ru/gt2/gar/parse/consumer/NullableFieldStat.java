package ru.gt2.gar.parse.consumer;

public class NullableFieldStat implements FieldStat {
    private final AbstractFieldStat fieldStat;

    private int nullCount = 0;

    public NullableFieldStat(AbstractFieldStat fieldStat) {
        this.fieldStat = fieldStat;
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
            fieldStat.accept(record);
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
}
