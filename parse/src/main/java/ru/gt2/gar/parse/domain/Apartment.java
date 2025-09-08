package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Помещение.
 * Основа — AS_APARTMENTS_2_251_05_04_01_01.xsd

 * @param id         Уникальный идентификатор записи. Ключевое поле
 * @param objectId   Глобальный уникальный идентификатор адресного объекта (число)
 * @param objectGuid Глобальный уникальный идентификатор адресного объекта (UUID)
 * @param changeId   ID изменившей транзакции
 * @param number     Номер комнаты; Длина: от 1 до 50 символов
 * @param apartType  Тип комнаты; {@link ApartmentType}
 * @param operTypeId Статус действия над записью – причина появления записи; {@link OperationType}
 * @param prevId     Идентификатор записи связывания с предыдущей исторической записью; Опционально
 * @param nextId     Идентификатор записи связывания с последующей исторической записью; Опционально
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActual   Статус актуальности адресного объекта ФИАС
 * @param isActive   Признак действующего адресного объекта
 */
public record Apartment(
        long id,
        long objectId,
        UUID objectGuid,
        long changeId,
        String number,
        int apartType,
        int operTypeId,
        @Nullable Long prevId,
        @Nullable Long nextId,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActual,
        boolean isActive) {
}
