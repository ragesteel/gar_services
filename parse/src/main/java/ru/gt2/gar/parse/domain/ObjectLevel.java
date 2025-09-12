package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

@SchemaComment("Уровни адресных объектов")
@SourceSchema(part = "251_12", format = "4.01", schema = "01")
public record ObjectLevel(
        @SchemaComment("Уникальный идентификатор записи. Ключевое поле. Номер уровня объекта")
        int level,

        @SchemaComment("Наименование")
        @LengthLimit(250)
        String name,

        @SchemaComment("Краткое наименование")
        @LengthLimit(50)
        @Nullable String shortName,

        @SchemaComment("Дата внесения (обновления) записи")
        LocalDate updateDate,

        @SchemaComment("Начало действия записи")
        LocalDate startDate,

        @SchemaComment("Окончание действия записи")
        LocalDate endDate,

        @SchemaComment("Признак действующего адресного объекта")
        boolean isActive) {
}
