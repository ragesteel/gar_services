package ru.gt2.gar.parse.domain;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Классификатор адресообразующих элементов.
 * Основа — AS_ADDR_OBJ_2_251_01_04_01_01.xsd

 * @param id         Уникальный идентификатор записи. Ключевое поле
 * @param objectId   Глобальный уникальный идентификатор адресного объекта (число)
 * @param objectGuid Глобальный уникальный идентификатор адресного объекта (UUID)
 * @param changeId   ID изменившей транзакции
 * @param name       Наименование адресного объекта; Длина: от 1 до 250 символов
 * @param typeName   Краткое наименование типа объекта; Длина: от 1 до 50 символов
 * @param level      Уровень адресного объект;. Формат: число от 1 до 10 цифр TODO почему String не понятно, да и regexp в схеме странный: [0-9]{1,10}
 * @param operTypeId Статус действия над записью – причина появления записи. {@link OperationType}
 * @param prevId     Идентификатор записи связывания с предыдущей исторической записью; Опциональное поле
 * @param nextId     Идентификатор записи связывания с последующей исторической записью; Опциональное поле
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActual   Статус актуальности адресного объекта ФИАС
 * @param isActive   Признак действующего адресного объекта
 */
public record AddressObject(
        long id,
        long objectId,
        UUID objectGuid,
        long changeId,
        String name,
        String typeName,
        String level,
        int operTypeId,
        Long prevId,
        Long nextId,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActual,
        boolean isActive) {
}
