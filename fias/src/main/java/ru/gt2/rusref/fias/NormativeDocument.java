package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Cведения по нормативным документам, являющимся основанием присвоения адресному элементу наименования.
 *
 * @author rage
 */
@XmlType(propOrder = {"normDoc", "docName", "docDate", "docNum", "docType", "docImgId"})
@ToString
public class NormativeDocument implements Serializable {
    /** Внешний ключ на нормативный документ. */
    @Id
    @NotNull
    @XmlAttribute(name = "NORMDOCID", required = true)
    private UUID normDoc;

    /**
     * Наименование документа.
     * Ограничения на максимум в оригинальной схеме нет, надеюсь 500 символов хватит.
     */
    @Size(min = 1, max = 500)
    @XmlAttribute(name = "DOCNAME")
    private String docName;
    
    /** Дата документа. */
    @Past
    @XmlAttribute(name = "DOCDATE")
    private Date docDate;

    /** Номер документа. */
    @Size(min = 1, max = 20)
    @XmlAttribute(name = "DOCNUM")
    private String docNum;

    /** Тип документа. */
    // FIXME Где то должна быть расшифовка по типам.
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "DOCTYPE", required = true)
    private Integer docType;

    /** Идентификатор образа (внешний ключ). */
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "DOCIMGID")
    private Integer docImgId;
}
