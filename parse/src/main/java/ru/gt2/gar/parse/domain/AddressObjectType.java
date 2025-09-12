package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Типы адресных объектов.

 * @param id         Идентификатор записи. Ключевое поле
 * @param level      Уровень адресного объекта
 * @param shortName  Краткое наименование типа объекта; Длина: от 1 до 50 символов
 * @param name       Полное наименование типа объекта; Длина: от 1 до 250 символов
 * @param desc       Описание. Опционально; Длина: от 0 до 250 символов
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActive   Статус активности
 */
@SourceSchema(part = "251_03", format = "4.01", schema = "01")
public record AddressObjectType(
        int id,
        int level,
        String shortName,
        String name,
        @Nullable String desc,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActive) {
}
