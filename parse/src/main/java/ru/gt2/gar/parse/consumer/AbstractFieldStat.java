package ru.gt2.gar.parse.consumer;

import lombok.Getter;
import ru.gt2.gar.domain.GarRecord;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Formatter;

public abstract class AbstractFieldStat implements FieldStat {
    @Getter
    protected final String name;

    protected final Method accessor;

    private final String typeName;

    protected AbstractFieldStat(RecordComponent recordComponent, String typeName) {
        name = recordComponent.getName();
        accessor = recordComponent.getAccessor();
        this.typeName = typeName;
    }

    protected AbstractFieldStat(String name, String typeName) {
        this.name = name;
        accessor = null;
        this.typeName = typeName;
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

    @Override
    public void format(Formatter formatter) {
        formatter.format("%s: %s", name, typeName);
    }
}
