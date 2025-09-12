package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.UUID;

@SchemaComment("Земельный участок")
@SourceSchema(part = "251_18", format = "4.01", schema = "01")
public record Stead(
        @SchemaComment("Уникальный идентификатор записи. Ключевое поле")
        long id,

        @SchemaComment("Глобальный уникальный идентификатор объекта (число)")
        long objectId,

        @SchemaComment("Глобальный уникальный идентификатор объекта (UUID)")
        UUID objectGuid,

        @SchemaComment("ID изменившей транзакции")
        long changeId,

        // В регинах 45, 89, 02, 47, 13 это поле может отсутствовать (база на 29.08.2025),
        // хотя и в XSD и судя по описанию в Word-формате — это обязательное поля,
        @SchemaComment("Номер земельного участка")
        @LengthLimit(50)
        @Nullable String number,

        @SchemaComment("Статус действия над записью – причина появления записи")
        @SchemaLink(GarType.OPERATION_TYPES)
        int operTypeId,

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

        @SchemaComment("Статус актуальности адресного объекта ФИАС")
        boolean isActual,

        @SchemaComment("Признак действующего адресного объекта")
        boolean isActive) {
}
