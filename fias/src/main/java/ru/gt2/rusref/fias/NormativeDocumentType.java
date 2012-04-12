package ru.gt2.rusref.fias;

import ru.gt2.rusref.Description;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 * Типы номативных документов.
 * Пока есть только один источник — http://fias.nalog.ru/Public/SearchPage.aspx?SearchState=2
 * Из которого был сделан csv/fias/NormativeDocumentType.csv
 */
@Description("Тип документа")
@FiasRef(NormativeDocument.class)
public class NormativeDocumentType {
    // Где то должна быть расшифовка по типам.
    @Description("Тип документа")
    @Id
    @Column(nullable = false, scale = 10)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    private Integer docType;

}
