package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.UUID;

@SchemaComment("История изменений")
@SourceSchema(suffix = "", part = "251_21", format = "4.01", schema = "01")
public record ChangeHistory(
        @SchemaComment("ID изменившей транзакции")
        long changeId,

        @SchemaComment("Глобальный уникальный идентификатор адресного объекта")
        long objectID,

        @SchemaComment("Глобальный уникальный идентификатор ID изменившей транзакции (UUID); Соответствует полю AOID выгрузки в формате ФИАС")
        UUID adrObjectId,

        @SchemaComment("ID статуса действия")
        @SchemaLink(GarType.OPERATION_TYPES) // Под вопросом
        int operTypeId,

        @SchemaComment("Идентификатор нормативного документа")
        @Nullable Long nDocId,

        @SchemaComment("Дата изменения")
        LocalDate changeDate) {
}
