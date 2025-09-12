package ru.gt2.gar.domain;

import java.time.LocalDate;
import java.util.UUID;

@SchemaComment("Реестр адресных элементов")
@SourceSchema(part = "251_22", format = "4.01", schema = "01")
public record ReestrObject(
        @SchemaComment("Уникальный идентификатор адресного объекта")
        long objectId,

        @SchemaComment("Глобальный уникальный идентификатор адресного объекта")
        UUID objectGuid,

        @SchemaComment("ID изменившей транзакции")
        long changeId,

        @SchemaComment("Признак действующего объекта")
        boolean isActive,

        @SchemaComment("Уровень объекта") // ObjectLevel?
        int levelId,

        @SchemaComment("Дата создания")
        LocalDate createDate,

        @SchemaComment("Дата изменения")
        LocalDate updateDate) {
}
