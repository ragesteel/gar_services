package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Тип помещения.
 * Основа — AS_APARTMENT_TYPES_2_251_07_04_01_01.xsd

 * @param id         Идентификатор типа (ключ).
 * @param name       Наименование адресного объекта; Длина: от 1 до 100 символов
 * @param shortName  Краткое наименование; Опциональное поле; Длина: от 0 до 50 символов
 * @param desc       Описание; Опциональное поле; Длина: от 0 до 250 символов
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActive   Признак действующего адресного объекта
 */
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
