package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

@SchemaComment("Параметры адресообразующих элементов и объектов недвижимости " +
        "(здания, строения, земельные участки, помещения, комнаты, машино-места)")
@SourceSchema(part = "251_02", format = "4.01", schema = "01")
public record Param(
        @SchemaComment("Идентификатор записи")
        long id,

        @SchemaComment("Глобальный идентификатор адресного объекта")
        long objectId,

        @SchemaComment("ID изменившей транзакции")
        @Nullable Long changeId,

        @SchemaComment("ID завершившей транзакции")
        long changeIdEnd,

        @SchemaComment("Тип параметра")
        @SchemaLink(GarType.PARAM_TYPES)
        int typeId,

        @SchemaComment("Значение параметра")
        @LengthLimit(8000)
        String value,

        @SchemaComment("Дата внесения (обновления) записи")
        LocalDate updateDate,

        @SchemaComment("Дата начала действия записи")
        LocalDate startDate,

        @SchemaComment("Дата окончания действия записи")
        LocalDate endDate) {
}
