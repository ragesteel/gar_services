package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

@SchemaComment("Иерархия в муниципальном делении")
@SourceSchema(part = "251_10", format = "4.01", schema = "01")
public record MunHierarchy(
        @SchemaComment("Уникальный идентификатор записи. Ключевое поле")
        long id,

        @SchemaComment("Глобальный уникальный идентификатор адресного объекта")
        long objectId,

        @SchemaComment("Идентификатор родительского объекта")
        @Nullable Long parentObjId,

        @SchemaComment("ID изменившей транзакции")
        long changeId,

        @SchemaComment("Код ОКТМО")
        @LengthLimit(11)
        @Nullable String oktmo,

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
        @LengthLimit(250) // В схемах нет, но в реальности не больше 79 символов
        String path) {
}


