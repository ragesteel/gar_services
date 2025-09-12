package ru.gt2.gar.parse.domain;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

@SchemaComment("Нормативный документ")
@SourceSchema(part = "251_11", format = "4.01", schema = "01")
public record NormativeDoc(
        @SchemaComment("Идентификатор нормативного документа")
        long id,

        // Хоть и в XSD и в Word-описании это поле обязательно,но в данных от 29.08.2029 куча записей без этого поля
        @SchemaComment("Наименование документа")
        @LengthLimit(8000)
        @Nullable String name,

        @SchemaComment("Дата документа")
        LocalDate date,

        @SchemaComment("Номер документа")
        @LengthLimit(150)
        String number,

        @SchemaComment("Тип документа")
        @SchemaLink(GarType.NORMATIVE_DOCS_TYPES)
        int type,

        @SchemaComment("Вид документа")
        @SchemaLink(GarType.NORMATIVE_DOCS_KINDS)
        int kind,

        @SchemaComment("Дата обновления")
        LocalDate updateDate,

        @SchemaComment("Наименование органа, создавшего нормативный документ")
        @LengthLimit(255)
        @Nullable String orgName,

        @SchemaComment("Номер государственной регистрации")
        @LengthLimit(100)
        @Nullable String regNum,

        @SchemaComment("Дата государственной регистрации")
        @Nullable LocalDate regDate,

        @SchemaComment("Дата вступления в силу нормативного документа")
        @Nullable LocalDate accDate,

        @SchemaComment("Комментарий")
        @LengthLimit(8000)
        @Nullable String comment) {
}
