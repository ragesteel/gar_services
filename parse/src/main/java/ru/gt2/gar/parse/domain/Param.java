package ru.gt2.gar.parse.domain;

import java.time.LocalDate;

/**
 * Параметры адресообразующих элементов и объектов недвижимости
 * (здания, строения, земельные участки, помещения, комнаты, машино-места).
 * Основа — AS_PARAM_2_251_02_04_01_01.xsd

 * @param id         Идентификатор записи
 * @param objectId   Глобальный идентификатор адресного объекта
 * @param changeId   ID изменившей транзакции; Опциональное поле
 * @param changeIdEnd ID завершившей транзакции
 * @param typeId     Типа параметра, {@link ParamType}
 * @param value      Значение параметра; Длина от 1 до 8000 символов
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Дата начала действия записи
 * @param endDate    Дата окончания действия записи
 */
public record Param(
        long id,
        long objectId,
        @UseOptional Long changeId,
        long changeIdEnd,
        int typeId,
        String value,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate) {
}
