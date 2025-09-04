package ru.gt2.gar.parse.domain;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Машино-место.
 * Основа — AS_CARPLACES_2_251_06_04_01_01.xsd
 *
 * @param id Уникальный идентификатор записи. Ключевое поле
 * @param objectId Глобальный уникальный идентификатор объекта (число)
 * @param objectGuid Глобальный уникальный идентификатор объекта (UUID)
 * @param changeId ID изменившей транзакции
 * @param number Номер машино-места.
 * @param operTypeId Статус действия над записью – причина появления записи; {@link OperationType}
 * @param prevId Идентификатор записи связывания с предыдущей исторической записью; Опциональное поле
 * @param nextId Идентификатор записи связывания с последующей исторической записью; Опциональное поле
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate Начало действия записи
 * @param endDate Окончание действия записи
 * @param isActual Статус актуальности адресного объекта ФИАС
 * @param isActive Признак действующего адресного объекта
 */
public record CarPlace(
        long id,
        long objectId,
        UUID objectGuid,
        long changeId,
        String number,
        int operTypeId,
        Long prevId,
        Long nextId,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActual,
        boolean isActive) {
}
