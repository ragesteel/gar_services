package ru.gt2.gar.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.UUID;

@SchemaComment("Жилые строения (дома/сооружения)")
@SourceSchema(part = "251_08", format = "4.01", schema = "01")
public record House(
        @SchemaComment("Уникальный идентификатор записи. Ключевое поле")
        long id,

        @SchemaComment("Глобальный уникальный идентификатор объекта (число)")
        long objectId,

        @SchemaComment("Глобальный уникальный идентификатор объекта (UUID)")
        UUID objectGuid,

        @SchemaComment("ID изменившей транзакции")
        long changeId,

        @SchemaComment("Номер дома")
        @LengthLimit(50)
        @Nullable String houseNum,

        @SchemaComment("Дополнительный номер 1")
        @LengthLimit(50)
        @Nullable String addNum1,

        @SchemaComment("Дополнительный номер 2")
        @LengthLimit(50)
        @Nullable String addNum2,

        @SchemaComment("Тип дома")
        @SchemaLink(GarType.HOUSE_TYPES)
        @Nullable Integer houseType,

        @SchemaComment("Тип дополнительного номера 1")
        @SchemaLink(GarType.ADDHOUSE_TYPES)
        @Nullable Integer addType1,

        @SchemaComment("Тип дополнительного номера 2")
        @SchemaLink(GarType.ADDHOUSE_TYPES)
        @Nullable Integer addType2,

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

        @SchemaComment("Статус активности")
        boolean isActive) {
}
