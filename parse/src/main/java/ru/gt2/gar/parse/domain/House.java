package ru.gt2.gar.parse.domain;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Жилые строения (дома/сооружения).
 * Основа — AS_HOUSES_2_251_08_04_01_01.xsd
 *
 * @param id         Уникальный идентификатор записи. Ключевое поле
 * @param objectId   Глобальный уникальный идентификатор объекта (число)
 * @param objectGuid Глобальный уникальный идентификатор объекта (UUID)
 * @param changeId   ID изменившей транзакции
 * @param houseNum   Номер дома; Опциональное поле; Длина: от 1 до 50 символов
 * @param addNum1    Дополнительный номер 1; Опциональное поле; Длина: от 1 до 50 символов
 * @param addNum2    Дополнительный номер 2; Опциональное поле; Длина: от 1 до 50 символов
 * @param houseType  Тип дома; {@link HouseType}
 * @param addType1   Тип дополнительного номера 1; {@link HouseType}
 * @param addType2   Тип дополнительного номера 2; {@link HouseType}
 * @param operTypeId Статус действия над записью – причина появления записи; {@link OperationType}
 * @param prevId     Идентификатор записи связывания с предыдущей исторической записью; Опциональное поле
 * @param nextId     Идентификатор записи связывания с последующей исторической записью; Опциональное поле
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActual   Статус актуальности адресного объекта ФИАС
 * @param isActive   Статус активности
 */
public record House(
        long id,
        long objectId,
        UUID objectGuid,
        long changeId,
        String houseNum,
        String addNum1,
        String addNum2,
        int houseType,
        Integer addType1,
        Integer addType2,
        int operTypeId,
        Long prevId,
        Long nextId,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActual,
        boolean isActive) {
}


