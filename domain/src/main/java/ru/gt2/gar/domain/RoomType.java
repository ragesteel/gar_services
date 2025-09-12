package ru.gt2.gar.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

@SchemaComment("Тип помещения")
@SourceSchema(part = "251_17", format = "4.01", schema = "01")
public record RoomType(
        @SchemaComment("Идентификатор типа (ключ)")
        int id,

        @SchemaComment("Наименование")
        @LengthLimit(100)
        String name,

        @SchemaComment("Краткое наименование")
        @LengthLimit(50)
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
