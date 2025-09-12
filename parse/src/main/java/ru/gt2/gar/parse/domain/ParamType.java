package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Типы параметров.

 * @param id         Идентификатор типа параметра
 * @param name       Наименование; Длина: от 1 до 50 символов
 * @param code       Кодовое обозначение; Длина: от 1 до 50 символов
 * @param desc       Описание; Опционально; Длина: от 0 до 120 символов
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActive   Статус активности
 */
@SourceSchema(part = "251_20", format = "4.01", schema = "01")
public record ParamType(
        int id,
        String name,
        String code,
        @Nullable String desc,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActive) {
}
