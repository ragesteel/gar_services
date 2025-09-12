package ru.gt2.gar.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.UUID;

@SchemaComment("Классификатор адресообразующих элементов")
@SourceSchema(part = "251_01", format = "4.01", schema = "01")
public record AddressObject(
        @SchemaComment("Уникальный идентификатор записи. Ключевое поле")
        long id,

        @SchemaComment("Глобальный уникальный идентификатор адресного объекта (число)")
        long objectId,

        @SchemaComment("Глобальный уникальный идентификатор адресного объекта (UUID)")
        UUID objectGuid,

        @SchemaComment("ID изменившей транзакции")
        long changeId,

        @SchemaComment("Наименование адресного объекта")
        @LengthLimit(250) String name,

        @SchemaComment("Краткое наименование типа объекта")
        @LengthLimit(50) String typeName,

        // TODO почему String не понятно, да и regexp в схеме странный: [0-9]{1,10}
        @SchemaComment("Уровень адресного объекта. Формат: число от 1 до 10 цифр")
        @LengthLimit(10) String level,

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
