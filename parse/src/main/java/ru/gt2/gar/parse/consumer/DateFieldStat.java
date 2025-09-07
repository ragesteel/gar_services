package ru.gt2.gar.parse.consumer;

import java.lang.reflect.RecordComponent;
import java.time.LocalDate;

public class DateFieldStat extends AbstractFieldStat {
    private LocalDate minValue = LocalDate.MAX;
    private LocalDate maxValue = LocalDate.MIN;

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
    }

    @Override
    public String toString() {
        return String.format("%s, date, %s … %s", name, minValue, maxValue);
    }
}
