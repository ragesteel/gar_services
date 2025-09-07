package ru.gt2.gar.parse.consumer;

import java.lang.reflect.RecordComponent;
import java.time.LocalDate;

public class DateFieldStat extends AbstractFieldStat {
    private LocalDate minValue = LocalDate.MAX;
    private LocalDate maxValue = LocalDate.MIN;
    private boolean hasMinMax = false;

    public DateFieldStat(RecordComponent recordComponent) {
        super(recordComponent);
    }

    @Override
    public void accept(Record record) {
        LocalDate value = (LocalDate) invokeAccessor(record);
        if (value.isBefore(minValue)) {
            minValue = value;
        }
        if (value.isAfter(maxValue)) {
            maxValue = value;
        }
        hasMinMax = true;
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder(name).append(", date");
        if (hasMinMax) {
            resultBuilder.append(", ").append(minValue).append(" … ").append(maxValue);
        }
        return resultBuilder.toString();
    }
}
