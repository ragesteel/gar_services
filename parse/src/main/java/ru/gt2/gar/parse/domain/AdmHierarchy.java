package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

@SourceSchema(part = "251_04", format = "4.01", schema = "01")
public record AdmHierarchy(
        @SchemaComment("Уникальный идентификатор записи. Ключевое поле")
        long id,

        @SchemaComment("Глобальный уникальный идентификатор адресного объекта")
        long objectId,

        @SchemaComment("Идентификатор родительского объекта")
        @Nullable Long parentObjId,

        @SchemaComment("ID изменившей транзакции")
        long changeId,

        @SchemaComment("Код региона")
        @LengthLimit(4)
        @Nullable String regionCode,

        @SchemaComment("Код района")
        @LengthLimit(4)
        @Nullable String areaCode,

        @SchemaComment("Код города")
        @LengthLimit(4)
        @Nullable String cityCode,

        @SchemaComment("Код населенного пункта")
        @LengthLimit(4)
        @Nullable String placeCode,

        @SchemaComment("Код ЭПС")
        @LengthLimit(4)
        @Nullable String planCode,

        @SchemaComment("Код улицы")
        @LengthLimit(4)
        @Nullable String streetCode,

        @SchemaComment("Идентификатор записи связывания с предыдущей исторической записью")
        @Nullable Long prevId,

        @SchemaComment("Идентификатор записи связывания с последующей исторической записью")
        @Nullable Long nextId,

        @SchemaComment("Дата внесения (обновления) записи")
        LocalDate updateDate,

        @SchemaComment("Начало действия записи")
        LocalDate startDate,

        @SchemaComment("Окончание действия записи")
        LocalDate endDate,

        @SchemaComment("Признак действующего адресного объекта")
        boolean isActive,

        @SchemaComment("Материализованный путь к объекту (полная иерархия)")
        String path) {
}
