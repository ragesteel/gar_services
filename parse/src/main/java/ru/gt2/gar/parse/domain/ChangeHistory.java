package ru.gt2.gar.parse.domain;

import java.time.LocalDate;
import java.util.UUID;

/**
 * История изменений.
 * Основа — AS_CHANGE_HISTORY_251_21_04_01_01.xsd
 *
 * @param changeId ID изменившей транзакции
 * @param objectID Глобальный уникальный идентификатор адресного объекта
 * @param adrObjectId Глобальный уникальный идентификатор ID изменившей транзакции; Соответствует полю AOID выгрузки в формате ФИАС
 * @param operTypeId ID статуса действия, м.б. {@link OperationType} ?
 * @param nDocId Идентификатор нормативного документа
 * @param changeDate Дата изменения
 */
public record ChangeHistory(
        long changeId,
        long objectID,
        UUID adrObjectId,
        int operTypeId,
        Long nDocId,
        LocalDate changeDate) {
}
