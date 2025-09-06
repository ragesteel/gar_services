package ru.gt2.gar.parse.domain;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Реестр адресных элементов.
 * Основа — AS_REESTR_OBJECTS_2_251_22_04_01_01.xsd

 * @param objectId   Уникальный идентификатор адресного объекта
 * @param objectGuid Глобальный уникальный идентификатор адресного объекта
 * @param changeId   ID изменившей транзакции
 * @param isActive   Признак действующего объекта
 * @param levelId    Уровень объекта
 * @param createDate Дата создания
 * @param updateDate Дата изменения
 */
public record ReestrObject(
        long objectId,
        UUID objectGuid,
        long changeId,
        boolean isActive,
        int levelId,
        LocalDate createDate,
        LocalDate updateDate) {
}
