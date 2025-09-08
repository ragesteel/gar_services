package ru.gt2.gar.parse.consumer;

import java.util.List;
import java.util.function.Consumer;

public interface ListConsumer<T extends Record> extends Consumer<List<T>> {
    default void before() {
    }

    default void after() {
    }
}
