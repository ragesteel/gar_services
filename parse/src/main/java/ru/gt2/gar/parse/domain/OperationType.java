package ru.gt2.gar.parse.domain;

import java.time.LocalDate;

/**
 * Статус действия.
 * Основа — AS_OPERATION_TYPES_2_251_14_04_01_01.xsd

 * @param id         Идентификатор статуса (ключ)
 * @param name       Наименование; Длина: от 1 до 100 символов
 * @param shortName  Краткое наименование; Опциональное поле; Длина: от 0 до 100 символов
 * @param desc       Описание; Опциональное поле
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActive   Статус активности
 */
public record OperationType(
        int id, 
        String name,
        String shortName,
        String desc,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActive) {
}
