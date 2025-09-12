package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

@SchemaComment("Типы параметров")
@SourceSchema(part = "251_20", format = "4.01", schema = "01")
public record ParamType(
        @SchemaComment("Идентификатор типа параметра")
        int id,

        @SchemaComment("Наименование")
        @LengthLimit(50)
        String name,

        @SchemaComment("Кодовое обозначение")
        @LengthLimit(50)
        String code,

        @SchemaComment("Описание")
        @LengthLimit(120)
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
