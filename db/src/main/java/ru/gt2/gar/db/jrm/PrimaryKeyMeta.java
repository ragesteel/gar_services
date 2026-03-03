package ru.gt2.gar.db.jrm;

import java.util.function.Function;

/**
 * Информация о первичном ключе
 *
 * @param fieldName Название поля в базе данных
 * @param value Функция получения значения из записи
  */
public record PrimaryKeyMeta<T> (
        String fieldName,
        Function<T, ?> value
) {
}
