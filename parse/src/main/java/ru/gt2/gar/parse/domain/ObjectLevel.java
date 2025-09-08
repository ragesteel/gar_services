package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Уровни адресных объектов.
 * Основа — AS_OBJECT_LEVELS_2_251_12_04_01_01.xsd

 * @param level      Уникальный идентификатор записи. Ключевое поле. Номер уровня объекта
 * @param name       Наименование; Длина: от 1 до 250 символов
 * @param shortName  Краткое наименование; Опционально; Длина: от 1 до 50 символов
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActive   Признак действующего адресного объекта
 */
public record ObjectLevel(
        int level,
        String name,
        @Nullable String shortName,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActive) {
}
