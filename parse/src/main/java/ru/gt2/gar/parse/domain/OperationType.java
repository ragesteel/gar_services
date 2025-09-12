package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

@SchemaComment("Статус действия")
@SourceSchema(part = "251_14", format = "4.01", schema = "01")
public record OperationType(
        @SchemaComment("Идентификатор статуса (ключ)")
        int id,

        @SchemaComment("Наименование")
        @LengthLimit(100)
        String name,

        @SchemaComment("Краткое наименование")
        @LengthLimit(100)
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
