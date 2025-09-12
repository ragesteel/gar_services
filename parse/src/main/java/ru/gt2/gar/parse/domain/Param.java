package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Параметры адресообразующих элементов и объектов недвижимости
 * (здания, строения, земельные участки, помещения, комнаты, машино-места).

 * @param id         Идентификатор записи
 * @param objectId   Глобальный идентификатор адресного объекта
 * @param changeId   ID изменившей транзакции; Опционально
 * @param changeIdEnd ID завершившей транзакции
 * @param typeId     Типа параметра, {@link ParamType}
 * @param value      Значение параметра; Длина от 1 до 8000 символов
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Дата начала действия записи
 * @param endDate    Дата окончания действия записи
 */
@SourceSchema(part = "251_02", format = "4.01", schema = "01")
public record Param(
        long id,
        long objectId,
        @Nullable Long changeId,
        long changeIdEnd,
        int typeId,
        String value,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate) {
}
