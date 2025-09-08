package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Типы адресных объектов.
 * Основа — AS_ADDR_OBJ_TYPES_2_251_03_04_01_01.xsd

 * @param id         Идентификатор записи. Ключевое поле
 * @param level      Уровень адресного объекта
 * @param shortName  Краткое наименование типа объекта; Длина: от 1 до 50 символов
 * @param name       Полное наименование типа объекта; Длина: от 1 до 250 символов
 * @param desc       Описание. Опциональное поле; Длина: от 0 до 250 символов
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActive   Статус активности
 */
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
