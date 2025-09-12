package ru.gt2.gar.parse.domain;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Реестр адресных элементов.

 * @param objectId   Уникальный идентификатор адресного объекта
 * @param objectGuid Глобальный уникальный идентификатор адресного объекта
 * @param changeId   ID изменившей транзакции
 * @param isActive   Признак действующего объекта
 * @param levelId    Уровень объекта
 * @param createDate Дата создания
 * @param updateDate Дата изменения
 */
@SourceSchema(part = "251_22", format = "4.01", schema = "01")
public record ReestrObject(
        long objectId,
        UUID objectGuid,
        long changeId,
        boolean isActive,
        int levelId,
        LocalDate createDate,
        LocalDate updateDate) {
}
