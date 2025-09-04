package ru.gt2.gar.parse.domain;

import java.time.LocalDate;

/**
 * Информация по типам помещений.
 * Основа — AS_APARTMENT_TYPES_2_251_07_04_01_01.xsd
 */
public record ApartmentType(
    /**
     * Идентификатор типа (ключ).
     */
    int id,

    /**
     * Наименование адресного объекта
     * Длина: от 1 до 100 символов
     */
    String name,

    /**
     * Краткое наименование
     * Опциональное поле
     * Длина: от 0 до 50 символов
     */
    String shortName,

    /**
     * Описание
     * Опциональное поле
     * Длина: от 0 до 250 символов
     */
    String desc,
    /**
     * Дата внесения (обновления) записи
     * Формат: дата
     */
    LocalDate updateDate,

    /**
     * Начало действия записи
     * Формат: дата
     */
    LocalDate startDate,

    /**
     * Окончание действия записи
     * Формат: дата
     */
    LocalDate endDate,

    /**
     * Признак действующего адресного объекта
     */
    boolean isActive) {
}
