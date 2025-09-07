package ru.gt2.gar.parse.consumer;

import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;

public abstract class AbstractFieldStat implements FieldStat {
    @Getter
    protected final String name;

    protected final Method accessor;

    protected AbstractFieldStat(RecordComponent recordComponent) {
        name = recordComponent.getName();
        accessor = recordComponent.getAccessor();
    }

    @Override
    public void accept(Record record) {
        acceptValue(invokeAccessor(record));
    }

    protected abstract void acceptValue(Object value);

    protected Object invokeAccessor(Record record) {
        try {
            return accessor.invoke(record);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Unable to get value of entity", e);
        }
    }
}
