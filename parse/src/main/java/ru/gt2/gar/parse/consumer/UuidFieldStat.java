package ru.gt2.gar.parse.consumer;

import java.lang.reflect.RecordComponent;

public class UuidFieldStat extends AbstractFieldStat {
    public UuidFieldStat(RecordComponent recordComponent) {
        super(recordComponent);
    }

    @Override
    public void acceptValue(Object value) {
        // nothing
    }

    public String toString() {
        return name + ", uuid";
    }
}
