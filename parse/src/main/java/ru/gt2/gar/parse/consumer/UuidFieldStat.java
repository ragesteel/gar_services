package ru.gt2.gar.parse.consumer;

import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;

public class UuidFieldStat extends AbstractFieldStat {
    public UuidFieldStat(RecordComponent recordComponent) {
        this(recordComponent.getName(), recordComponent.getAccessor());
    }

    private UuidFieldStat(String name, Method accessor) {
        super(name, accessor, "uuid");
    }

    @Override
    public void acceptValue(Object value) {
        // nothing
    }

    @Override
    public FieldStat sum(FieldStat other) {
        if (!(other instanceof UuidFieldStat)) {
            throw new IllegalArgumentException("Sum must be called on equal types");
        }

        return new UuidFieldStat(name, accessor);
    }
}
