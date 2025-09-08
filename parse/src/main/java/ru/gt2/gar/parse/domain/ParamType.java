package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Типы параметров.
 * Основа — AS_PARAM_TYPES_2_251_20_04_01_01.xsd

 * @param id         Идентификатор типа параметра
 * @param name       Наименование; Длина: от 1 до 50 символов
 * @param code       Кодовое обозначение; Длина: от 1 до 50 символов
 * @param desc       Описание; Опционально; Длина: от 0 до 120 символов
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActive   Статус активности
 */
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
