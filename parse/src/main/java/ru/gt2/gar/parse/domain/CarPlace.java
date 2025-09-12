package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Машино-место.

 * @param id         Уникальный идентификатор записи. Ключевое поле
 * @param objectId   Глобальный уникальный идентификатор объекта (число)
 * @param objectGuid Глобальный уникальный идентификатор объекта (UUID)
 * @param changeId   ID изменившей транзакции
 * @param number     Номер машино-места.
 * @param operTypeId Статус действия над записью – причина появления записи; {@link OperationType}
 * @param prevId     Идентификатор записи связывания с предыдущей исторической записью; Опционально
 * @param nextId     Идентификатор записи связывания с последующей исторической записью; Опционально
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActual   Статус актуальности адресного объекта ФИАС
 * @param isActive   Признак действующего адресного объекта
 */
@SchemaComment("Машино-место")
@SourceSchema(part = "251_06", format = "4.01", schema = "01")
public record CarPlace(
        @SchemaComment("Уникальный идентификатор записи. Ключевое поле")
        long id,

        @SchemaComment("Глобальный уникальный идентификатор объекта (число)")
        long objectId,

        @SchemaComment("Глобальный уникальный идентификатор объекта (UUID)")
        UUID objectGuid,

        @SchemaComment("ID изменившей транзакции")
        long changeId,

        @SchemaComment("Номер машино-места.")
        @LengthLimit(50)
        String number,

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
