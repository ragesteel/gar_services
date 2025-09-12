package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Тип помещения.

 * @param id         Идентификатор типа (ключ).
 * @param name       Наименование адресного объекта; Длина: от 1 до 100 символов
 * @param shortName  Краткое наименование; Опционально; Длина: от 0 до 50 символов
 * @param desc       Описание; Опционально; Длина: от 0 до 250 символов
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActive   Признак действующего адресного объекта
 */
@SourceSchema(part = "251_07", format = "4.01", schema = "01")
public record ApartmentType(
        int id,
        String name,
        @Nullable String shortName,
        @Nullable String desc,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActive) {
}
