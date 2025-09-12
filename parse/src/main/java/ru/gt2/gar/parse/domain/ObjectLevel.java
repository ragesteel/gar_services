package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Уровни адресных объектов.

 * @param level      Уникальный идентификатор записи. Ключевое поле. Номер уровня объекта
 * @param name       Наименование; Длина: от 1 до 250 символов
 * @param shortName  Краткое наименование; Опционально; Длина: от 1 до 50 символов
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActive   Признак действующего адресного объекта
 */
@SourceSchema(part = "251_12", format = "4.01", schema = "01")
public record ObjectLevel(
        int level,
        String name,
        @Nullable String shortName,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActive) {
}
