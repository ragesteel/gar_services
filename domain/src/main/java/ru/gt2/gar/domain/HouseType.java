package ru.gt2.gar.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

@SchemaComment("Типы домов")
@SourceSchema(part = "251_13", format = "4.01", schema = "01")
public record HouseType(
        @SchemaComment("Идентификатор типа (ключ)")
        int id,

        @SchemaComment("Наименование")
        @LengthLimit(50)
        String name,

        @SchemaComment("Краткое наименование")
        @LengthLimit(20) // В XSD — до 50
        @Nullable String shortName,

        @SchemaComment("Описание")
        @LengthLimit(250)
        @Nullable String desc,

        @SchemaComment("Дата внесения (обновления) записи")
        LocalDate updateDate,

        @SchemaComment("Начало действия записи")
        LocalDate startDate,

        @SchemaComment("Окончание действия записи")
        LocalDate endDate,

        @SchemaComment("Статус активности")
        boolean isActive) {
}


