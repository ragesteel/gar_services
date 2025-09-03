package ru.gt2.gar.parse.domain;

import java.time.LocalDate;

/**
 * Типы адресных объектов.
 * Основа — AS_ADDR_OBJ_TYPES_2_251_03_04_01_01.xsd
 */
public record AddressObjectType(
        /**
         * Идентификатор записи. Ключевое поле
         */
        int id,

        /**
         * Уровень адресного объекта
         */
        int level,

        /**
         * Краткое наименование типа объекта
         * Длина: от 1 до 50 символов
         */
        String shortName,

        /**
         * Полное наименование типа объекта
         * Длина: от 1 до 250 символов
         */
        String name,

        /**
         * Описание
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
         * Статус активности
         */
        boolean isActive) {
}
