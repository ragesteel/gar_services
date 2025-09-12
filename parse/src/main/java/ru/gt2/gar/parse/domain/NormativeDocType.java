package ru.gt2.gar.parse.domain;

import java.time.LocalDate;

@SchemaComment("Тип нормативного документа")
@SourceSchema(part = "251_16", format = "4.01", schema = "01")
public record NormativeDocType(
        @SchemaComment("Идентификатор записи")
        int id,

        @SchemaComment("Наименование")
        @LengthLimit(500)
        String name,

        @SchemaComment("Дата начала действия записи")
        LocalDate startDate,

        @SchemaComment("Дата окончания действия записи")
        LocalDate endDate) {
}


