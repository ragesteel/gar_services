package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Жилые строения (дома/сооружения).

 * @param id         Уникальный идентификатор записи. Ключевое поле
 * @param objectId   Глобальный уникальный идентификатор объекта (число)
 * @param objectGuid Глобальный уникальный идентификатор объекта (UUID)
 * @param changeId   ID изменившей транзакции
 * @param houseNum   Номер дома; Опционально; Длина: от 1 до 50 символов
 * @param addNum1    Дополнительный номер 1; Опционально; Длина: от 1 до 50 символов
 * @param addNum2    Дополнительный номер 2; Опционально; Длина: от 1 до 50 символов
 * @param houseType  Тип дома; {@link HouseType}
 * @param addType1   Тип дополнительного номера 1; {@link HouseType} видимо из ADD_HOUSE_TYPES
 * @param addType2   Тип дополнительного номера 2; {@link HouseType} видимо из ADD_HOUSE_TYPES
 * @param operTypeId Статус действия над записью – причина появления записи; {@link OperationType}
 * @param prevId     Идентификатор записи связывания с предыдущей исторической записью; Опционально
 * @param nextId     Идентификатор записи связывания с последующей исторической записью; Опционально
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActual   Статус актуальности адресного объекта ФИАС
 * @param isActive   Статус активности
 */
@SourceSchema(part = "251_08", format = "4.01", schema = "01")
public record House(
        long id,
        long objectId,
        UUID objectGuid,
        long changeId,
        @Nullable String houseNum,
        @Nullable String addNum1,
        @Nullable String addNum2,
        @Nullable Integer houseType,
        @Nullable Integer addType1,
        @Nullable Integer addType2,
        int operTypeId,
        @Nullable Long prevId,
        @Nullable Long nextId,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActual,
        boolean isActive) {
}


