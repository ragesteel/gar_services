package ru.gt2.gar.parse.domain;

import java.time.LocalDate;

/**
 * Тип помещения.
 * Основа — AS_APARTMENT_TYPES_2_251_07_04_01_01.xsd

 * @param id         Идентификатор типа (ключ).
 * @param name       Наименование; Длина: от 1 до 100 символов
 * @param shortName  Краткое наименование; Опциональное поле; Длина: от 0 до 50 символов
 * @param desc       Описание; Опциональное поле; Длина: от 0 до 250 символов
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActive   Статус активности
 */
public record RoomType(
        int id,
        String name,
        String shortName,
        String desc,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActive) {
}
