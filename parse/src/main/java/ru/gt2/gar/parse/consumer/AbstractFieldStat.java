package ru.gt2.gar.parse.consumer;

import lombok.Getter;
import ru.gt2.gar.domain.GarRecord;

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

    protected AbstractFieldStat(String name) {
        this.name = name;
        accessor = null;
    }

    @Override
    public void accept(GarRecord record) {
        Object value = invokeAccessor(record);
        if (null == value) {
            throw new NullPointerException("value is null, field=" + name);
        }
        acceptValue(value);
    }

    protected abstract void acceptValue(Object value);

    protected Object invokeAccessor(GarRecord record) {
        try {
            return accessor.invoke(record);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Unable to get value of entity", e);
        }
    }
}
