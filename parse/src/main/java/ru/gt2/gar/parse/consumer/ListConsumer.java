package ru.gt2.gar.parse.consumer;

import ru.gt2.gar.domain.GarRecord;

import java.util.List;
import java.util.function.Consumer;

public interface ListConsumer extends Consumer<List<GarRecord>> {
    default void before() {
    }

    default void after() {
    }
}
