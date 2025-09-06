package ru.gt2.gar.parse.domain;

import java.time.LocalDate;

/**
 * Тип нормативного документа.
 * Основа — AS_NORMATIVE_DOCS_TYPES_2_251_16_04_01_01.xsd

 * @param id         Идентификатор записи
 * @param name       Наименование; Длина: от 1 до 500 символов
 * @param startDate  Дата начала действия записи
 * @param endDate    Дата окончания действия записи
 */
public record NormativeDocType(
        int id,
        String name,
        LocalDate startDate,
        LocalDate endDate) {
}


