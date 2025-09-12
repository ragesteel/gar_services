package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Иерархия в муниципальном делении.

 * @param id         Уникальный идентификатор записи. Ключевое поле
 * @param objectId   Глобальный уникальный идентификатор адресного объекта
 * @param parentObjId Идентификатор родительского объекта; Опционально
 * @param changeId   ID изменившей транзакции
 * @param oktmo      Код ОКТМО; Опционально; Длина: от 8 до 11 символов, только цифры
 * @param prevId     Идентификатор записи связывания с предыдущей исторической записью; Опционально
 * @param nextId     Идентификатор записи связывания с последующей исторической записью; Опционально
 * @param updateDate Дата внесения (обновления) записи
 * @param startDate  Начало действия записи
 * @param endDate    Окончание действия записи
 * @param isActive   Признак действующего адресного объекта
 * @param path       Материализованный путь к объекту (полная иерархия)
 */
@SourceSchema(part = "251_10", format = "4.01", schema = "01")
public record MunHierarchy(
        long id,
        long objectId,
        @Nullable Long parentObjId,
        long changeId,
        @Nullable String oktmo,
        @Nullable Long prevId,
        @Nullable Long nextId,
        LocalDate updateDate,
        LocalDate startDate,
        LocalDate endDate,
        boolean isActive,
        String path) {
}


