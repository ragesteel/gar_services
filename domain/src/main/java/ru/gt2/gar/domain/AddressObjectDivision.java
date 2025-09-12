package ru.gt2.gar.domain;

@SchemaComment("Переподчинение адресных элементов")
@SourceSchema(part = "251_19", format = "4.01", schema = "01")
public record AddressObjectDivision(
        @SchemaComment("Уникальный идентификатор записи. Ключевое поле")
        long id,

        @SchemaComment("Глобальный уникальный идентификатор родительского адресного объекта")
        long parentId,

        @SchemaComment("Глобальный уникальный идентификатор дочернего адресного объекта")
        long childId,

        @SchemaComment("ID изменившей транзакции")
        long changeId) {
}