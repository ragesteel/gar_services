package ru.gt2.gar.parse.consumer;

import java.lang.reflect.RecordComponent;

public class UuidFieldStat extends AbstractFieldStat {
    public UuidFieldStat(RecordComponent recordComponent) {
        super(recordComponent);
    }

    private UuidFieldStat(String name) {
        super(name);
    }

    @Override
    public void acceptValue(Object value) {
        // nothing
    }

    @Override
    public String toString() {
        return name + ", uuid";
    }

    @Override
    public FieldStat sum(FieldStat other) {
        if (!(other instanceof UuidFieldStat uuidField)) {
            throw new IllegalArgumentException("Sum must be called on equal types");
        }

        return new UuidFieldStat(name);
    }
}
