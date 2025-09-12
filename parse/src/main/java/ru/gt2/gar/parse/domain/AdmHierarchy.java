package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Иерархия в административном делении

 * @param id         Уникальный идентификатор записи. Ключевое поле
 * @param objectId   Глобальный уникальный идентификатор адресного объекта
 * @param parentObjId Идентификатор родительского объекта. Опционально
 * @param changeId   ID изменившей транзакции
 * @param regionCode Код региона; Опционально; Длина: от 1 до 4 символов, только цифры
 * @param areaCode   Код района; Опционально; Длина: от 1 до 4 символов, только цифры
 * @param cityCode   Код города; Опционально; Длина: от 1 до 4 символов, только цифры
 * @param placeCode  Код населенного пункта; Опционально; Длина: от 1 до 4 символов, только цифры
 * @param planCode   Код ЭПС; Опционально; Длина: от 1 до 4 символов, только цифры
 * @param streetCode Код улицы; Опционально; Длина: от 1 до 4 символов, только цифры
 * @param prevId     Идентификатор записи связывания с предыдущей исторической записью; Опционально
 * @param nextId     Идентификатор записи связывания с последующей исторической записью; Опционально
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActive   Признак действующего адресного объекта
 * @param path       Материализованный путь к объекту (полная иерархия)
 */
@SourceSchema(part = "251_04", format = "4.01", schema = "01")
public record AdmHierarchy(
        long id,
        long objectId,
        @Nullable Long parentObjId,
        long changeId,
        @Nullable String regionCode,
        @Nullable String areaCode,
        @Nullable String cityCode,
        @Nullable String placeCode,
        @Nullable String planCode,
        @Nullable String streetCode,
        @Nullable Long prevId,
        @Nullable Long nextId,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActive,
        String path) {
}
