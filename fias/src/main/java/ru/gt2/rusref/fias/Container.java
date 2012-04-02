package ru.gt2.rusref.fias;

import java.util.List;

/**
 * Интерфейс для всех контейнеров.
 *
 * @param <T>
 */
public interface Container<T> {
    List<T> getList();
}
