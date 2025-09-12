package ru.gt2.gar.parse.domain;

@SchemaComment("Вид нормативного документа")
@SourceSchema(part = "251_09", format = "4.01", schema = "01")
public record NormativeDocKind(
        @SchemaComment("Идентификатор вида нормативного документа")
        int id,

        @SchemaComment("Наименование")
        @LengthLimit(500)
        String name) {
}


