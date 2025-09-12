package ru.gt2.gar.parse.domain;

/**
 * Переподчинение адресных элементов.

 * @param id       Уникальный идентификатор записи. Ключевое поле
 * @param parentId Глобальный уникальный идентификатор родительского адресного объекта
 * @param childId  Глобальный уникальный идентификатор дочернего адресного объекта
 * @param changeId ID изменившей транзакции
 */
@SourceSchema(part = "251_19", format = "4.01", schema = "01")
public record AddressObjectDivision(
        long id,
        long parentId,
        long childId,
        long changeId) {
}