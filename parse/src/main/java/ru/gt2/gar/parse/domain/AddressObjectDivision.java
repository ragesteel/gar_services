package ru.gt2.gar.parse.domain;

/**
 * Переподчинение адресных элементов.
 * Основа AS_ADDR_OBJ_DIVISION_2_251_19_04_01_01.xsd

 * @param id       Уникальный идентификатор записи. Ключевое поле
 * @param parentId Глобальный уникальный идентификатор родительского адресного объекта
 * @param childId  Глобальный уникальный идентификатор дочернего адресного объекта
 * @param changeId ID изменившей транзакции
 */
public record AddressObjectDivision(
        long id,
        long parentId,
        long childId,
        long changeId) {
}