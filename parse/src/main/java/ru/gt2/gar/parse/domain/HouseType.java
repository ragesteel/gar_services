package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Информация по признакам владения.
 * Основа — AS_HOUSE_TYPES_2_251_13_04_01_01.xsd

 * @param id         Идентификатор типа (ключ)
 * @param name       Наименование; Длина: от 1 до 50 символов
 * @param shortName  Краткое наименование; Опционально; Длина: от 1 до 20 символов (в XSD — до 50)
 * @param desc       Описание; Опционально; Длина: от 1 до 250 символов
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActive   Статус активности
 */
public record HouseType(
        int id,
        String name,
        @Nullable String shortName,
        @Nullable String desc,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActive) {
}


