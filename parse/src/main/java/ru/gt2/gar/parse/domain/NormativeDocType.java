package ru.gt2.gar.parse.domain;

import java.time.LocalDate;

/**
 * Тип нормативного документа.

 * @param id         Идентификатор записи
 * @param name       Наименование; Длина: от 1 до 500 символов
 * @param startDate  Дата начала действия записи
 * @param endDate    Дата окончания действия записи
 */
@SourceSchema(part = "251_16", format = "4.01", schema = "01")
public record NormativeDocType(
        int id,
        String name,
        LocalDate startDate,
        LocalDate endDate) {
}


