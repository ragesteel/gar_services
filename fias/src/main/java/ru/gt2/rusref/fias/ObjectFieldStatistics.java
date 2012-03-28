package ru.gt2.rusref.fias;

import com.google.common.base.Objects;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * Статистика по полю.
 */
public class ObjectFieldStatistics<T> {
    private final Field field;
    protected int nullCount;
    protected int notNullCount;
    
    public final void updateStatistics(Object obj) {
        try {
            T value = (T) field.get(obj);
            doUpdateStatistics(value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to get access to field", e);
        }
    }
    
    public static ObjectFieldStatistics<?> newFieldStatistics(Field field) {
        Class<?> type = field.getType();
        if (Integer.class.equals(type)) {
            return new IntegerFieldStatistics(field);
        } else if (String.class.equals(type)) {
            return new StringFieldStatistics(field);
        } else if (Date.class.equals(type)) {
            return new DateFieldStatistics(field);
        }
        return new ObjectFieldStatistics<Object>(field);
    }

    @Override
    public String toString() {
        return getToStringHelper().toString();
    }

    public void print(PrintStream printStream) {
        printStream.print("notNullCount = " + notNullCount + ", nullCount = " + nullCount);
    }
    
    public String getFieldName() {
        return field.getName();
    }

    protected void doUpdateStatistics(T value) {
        if (null == value) {
            nullCount++;
        } else {
            notNullCount++;
        }
    }

    protected Objects.ToStringHelper getToStringHelper() {
        return Objects.toStringHelper(this)
                .add("nullCount", nullCount)
                .add("notNullCount", notNullCount);
    }
    
    protected ObjectFieldStatistics(Field field) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        this.field = field;
    }

    public static class IntegerFieldStatistics extends ObjectFieldStatistics<Integer> {
        private int min = Integer.MAX_VALUE;
        private int max = Integer.MIN_VALUE;

        @Override
        protected void doUpdateStatistics(Integer value) {
            super.doUpdateStatistics(value);
            
            if (null == value) {
                return;
            }
            
            min = Math.min(min, value);
            max = Math.max(max, value);
        }

        @Override
        protected Objects.ToStringHelper getToStringHelper() {
            Objects.ToStringHelper toStringHelper = super.getToStringHelper();
            if (notNullCount > 0) {
                toStringHelper = toStringHelper
                        .add("min", min)
                        .add("max", max);
            }
            return toStringHelper;
        }

        public IntegerFieldStatistics(Field field) {
            super(field);
        }

        @Override
        public void print(PrintStream printStream) {
            super.print(printStream);
            if (0 == notNullCount) {
                return;
            }
            printStream.print(", range = " + min + " … " + max);
        }
    }

    public static class StringFieldStatistics extends ObjectFieldStatistics<String> {
        private int minLen = Integer.MAX_VALUE;
        private int maxLen = Integer.MIN_VALUE;

        @Override
        protected void doUpdateStatistics(String value) {
            super.doUpdateStatistics(value);

            if (null == value) {
                return;
            }

            minLen = Math.min(minLen, value.length());
            maxLen = Math.max(maxLen, value.length());
        }

        @Override
        protected Objects.ToStringHelper getToStringHelper() {
            Objects.ToStringHelper toStringHelper = super.getToStringHelper();
            if (notNullCount > 0) {
                toStringHelper = toStringHelper
                        .add("minLen", minLen)
                        .add("maxLen", maxLen);
            }
            return toStringHelper;
        }

        public StringFieldStatistics(Field field) {
            super(field);
        }

        @Override
        public void print(PrintStream printStream) {
            super.print(printStream);
            if (0 == notNullCount) {
                return;
            }
            printStream.print(", length range = " + minLen + " … " + maxLen);
        }
    }

    public static class DateFieldStatistics extends ObjectFieldStatistics<Date> {
        private Date min;
        private Date max;

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
        }

        @Override
        protected Objects.ToStringHelper getToStringHelper() {
            Objects.ToStringHelper toStringHelper = super.getToStringHelper();
            if (notNullCount > 0) {
                toStringHelper = toStringHelper
                        .add("min", min)
                        .add("max", max);
            }
            return toStringHelper;
        }

        public DateFieldStatistics(Field field) {
            super(field);
        }

        @Override
        public void print(PrintStream printStream) {
            super.print(printStream);
            if (0 == notNullCount) {
                return;
            }
            printStream.print(", range = " + min + " … " + max);
        }
    }
}
