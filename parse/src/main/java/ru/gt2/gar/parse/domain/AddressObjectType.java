package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

@SchemaComment("Типы адресных объектов")
@SourceSchema(part = "251_03", format = "4.01", schema = "01")
public record AddressObjectType(
        @SchemaComment("Идентификатор записи. Ключевое поле")
        int id,

        @SchemaComment("Уровень адресного объекта")
        int level,

        @SchemaComment("Краткое наименование типа объекта")
        @LengthLimit(50)
        String shortName,

        @SchemaComment("Полное наименование типа объекта")
        @LengthLimit(250)
        String name,

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
