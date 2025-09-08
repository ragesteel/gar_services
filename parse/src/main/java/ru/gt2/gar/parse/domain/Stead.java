package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Земельный участок.
 * Основа — AS_STEADS_2_251_18_04_01_01.xsd

 * @param id         Уникальный идентификатор записи. Ключевое поле
 * @param objectId   Глобальный уникальный идентификатор объекта (число)
 * @param objectGuid Глобальный уникальный идентификатор объекта (UUID)
 * @param changeId   ID изменившей транзакции
 * @param number     Номер земельного участка; Опциональное поле; Длина: от 1 до 50 символов
 *                   В регинах 45, 89, 02, 47, 13 это поле может отсутствовать (база на 29.08.2025),
 *                   хотя и в XSD и судя по описанию в Word-формате — это обязательное поля,
 * @param operTypeId Статус действия над записью – причина появления записи; {@link OperationType}
 * @param prevId     Идентификатор записи связывания с предыдущей исторической записью; Опциональное поле
 * @param nextId     Идентификатор записи связывания с последующей исторической записью; Опциональное поле
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActual   Статус актуальности адресного объекта ФИАС
 * @param isActive   Признак действующего адресного объекта
 *
 */
public record Stead(
        long id,
        long objectId,
        UUID objectGuid,
        long changeId,
        @Nullable String number,
        int operTypeId,
        @Nullable Long prevId,
        @Nullable Long nextId,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActual,
        boolean isActive) {
}
