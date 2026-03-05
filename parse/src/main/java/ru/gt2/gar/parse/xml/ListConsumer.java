package ru.gt2.gar.parse.xml;

import ru.gt2.gar.domain.GarRecord;

import java.util.List;
import java.util.function.Consumer;

/// Потребитель списка записей, метод accept вызывается после набора пачки данных или по завершению чтения
public interface ListConsumer extends Consumer<List<GarRecord>> {
    /// Вызывается перед началом обработки
    default void before() {
    }

    /// Вызывается по окончанию обработки
    default void after() {
    }
}
