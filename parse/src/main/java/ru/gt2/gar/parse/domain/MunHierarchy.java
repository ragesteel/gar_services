package ru.gt2.gar.parse.domain;

import java.time.LocalDate;

/**
 * Иерархия в муниципальном делении.
 * Основа — AS_MUN_HIERARCHY_2_251_04_04_01_01.xsd

 * @param id         Уникальный идентификатор записи. Ключевое поле
 * @param objectId   Глобальный уникальный идентификатор адресного объекта
 * @param parentObjId Идентификатор родительского объекта; Опциональное поле
 * @param changeId   ID изменившей транзакции
 * @param oktmo      Код ОКТМО; Опциональное поле; Длина: от 8 до 11 символов, только цифры
 * @param prevId     Идентификатор записи связывания с предыдущей исторической записью; Опциональное поле
 * @param nextId     Идентификатор записи связывания с последующей исторической записью; Опциональное поле
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActive   Признак действующего адресного объекта
 * @param path       Материализованный путь к объекту (полная иерархия)
 */
public record MunHierarchy(
        long id,
        long objectId,
        @UseOptional Long parentObjId,
        long changeId,
        @UseOptional String oktmo,
        @UseOptional Long prevId,
        @UseOptional Long nextId,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActive,
        String path) {
}


