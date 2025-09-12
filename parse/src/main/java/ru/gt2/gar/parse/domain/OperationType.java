package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Статус действия.

 * @param id         Идентификатор статуса (ключ)
 * @param name       Наименование; Длина: от 1 до 100 символов
 * @param shortName  Краткое наименование; Опционально; Длина: от 0 до 100 символов
 * @param desc       Описание; Опционально; Длина: от 0 до 250 символов
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActive   Статус активности
 */
@SourceSchema(part = "251_14", format = "4.01", schema = "01")
public record OperationType(
        int id, 
        String name,
        @Nullable String shortName,
        @Nullable String desc,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActive) {
}
