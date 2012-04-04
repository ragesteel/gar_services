package ru.gt2.rusref.stat;

import ru.gt2.rusref.FieldType;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

public class DateFieldStatistics extends ObjectFieldStatistics<Date> {
    private Date min;
    private Date max;
    private Boolean timeOnlyZero;

    @Override
    protected void doUpdateStatistics(Date value) {
        super.doUpdateStatistics(value);

        if (null == value) {
            return;
        }

        if (null == min) {
            min = value;
        } else {
            min = value.before(min) ? value : min;
        }

        if (null == max) {
            max = value;
        } else {
            max = value.after(max) ? value : max;
        }

        if (!Boolean.FALSE.equals(timeOnlyZero)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(value);
            timeOnlyZero = (0 == calendar.get(Calendar.HOUR_OF_DAY)) && (0 == calendar.get(Calendar.MINUTE)
                    && 0 == calendar.get(Calendar.SECOND));
        }
    }

    public DateFieldStatistics(Field field, FieldType fieldType) {
        super(field, fieldType);
    }

    @Override
    public void print(PrintStream printStream) {
        super.print(printStream);
        if (0 == notNullCount) {
            return;
        }
        printStream.print(", range = " + min + " … " + max);
        if (null != timeOnlyZero) {
            printStream.print(", timeOnlyZero = " + timeOnlyZero);
        }
    }
}
