package ru.gt2.gar.parse.domain;

import java.time.LocalDate;

/**
 * Иерархия в административном делении
 * Основа — AS_ADM_HIERARCHY_2_251_04_04_01_01.xsd
 *
 * @param id         Уникальный идентификатор записи. Ключевое поле
 * @param objectId   Глобальный уникальный идентификатор адресного объекта
 * @param parentObjId Идентификатор родительского объекта. Опциональное поле
 * @param changeId   ID изменившей транзакции
 * @param regionCode Код региона; Опциональное поле; Длина: от 1 до 4 символов, только цифры
 * @param areaCode   Код района; Опциональное поле; Длина: от 1 до 4 символов, только цифры
 * @param cityCode   Код города; Опциональное поле; Длина: от 1 до 4 символов, только цифры
 * @param placeCode  Код населенного пункта; Опциональное поле; Длина: от 1 до 4 символов, только цифры
 * @param planCode   Код ЭПС; Опциональное поле; Длина: от 1 до 4 символов, только цифры
 * @param streetCode Код улицы; Опциональное поле; Длина: от 1 до 4 символов, только цифры
 * @param previd     Идентификатор записи связывания с предыдущей исторической записью; Опциональное поле
 * @param nextId     Идентификатор записи связывания с последующей исторической записью; Опциональное поле
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActive   Признак действующего адресного объекта
 * @param path       Материализованный путь к объекту (полная иерархия)
 */
public record AdmHierarchy(
        long id,
        long objectId,
        Long parentObjId,
        long changeId,
        String regionCode,
        String areaCode,
        String cityCode,
        String placeCode,
        String planCode,
        String streetCode,
        Long previd,
        Long nextId,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActive,
        String path) {
}
