package ru.gt2.rusref.fias;

import ru.gt2.rusref.Description;

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

@Description("Cведения по нормативным документам, являющимся основанием присвоения адресному элементу наименования")
@XmlType(propOrder = {"normDoc", "docName", "docDate", "docNum", "docType", "docImgId"})
public class NormativeDocument implements Serializable {
    @Description("Внешний ключ на нормативный документ")
    @Id
    @NotNull
    @XmlAttribute(name = "NORMDOCID", required = true)
    private UUID normDoc;

    @Description("Наименование документа")
    @Size(min = 1)
    @XmlAttribute(name = "DOCNAME")
    private String docName;
    
    @Description("Дата документа")
    @Past
    @XmlAttribute(name = "DOCDATE")
    private Date docDate;

    // Где то должна быть расшифовка по типам.
    @Description("Тип документа")
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "DOCTYPE", required = true)
    private Integer docType;

    @Description("Номер документа")
    @Size(min = 1, max = 20)
    @XmlAttribute(name = "DOCNUM")
    private String docNum;

    @Description("Идентификатор образа (внешний ключ)")
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "DOCIMGID")
    private Integer docImgId;
}
