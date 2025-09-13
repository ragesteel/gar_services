package ru.gt2.gar.parse.consumer;

import java.util.List;
import java.util.function.Consumer;

public interface ListConsumer extends Consumer<List<Record>> {
    default void before() {
    }

    default void after() {
    }
}
