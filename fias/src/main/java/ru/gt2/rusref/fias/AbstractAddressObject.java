package ru.gt2.rusref.fias;

import ru.gt2.rusref.Description;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Общая часть для AddressObject и AbstractHouse.
 */
@MappedSuperclass
@XmlTransient
public abstract class AbstractAddressObject implements Serializable {
    @Description("Почтовый индекс")
    @Column(length = 6)
    @Size(min = 6, max = 6)
    @XmlAttribute(name = "POSTALCODE")
    private String postalCode;

    @Description("Код ИФНС ФЛ")
    @Column(length = 4)
    @Size(min = 4, max = 4)
    @XmlAttribute(name = "IFNSFL")
    private String ifnsFl;

    @Description("Код территориального участка ИФНС ФЛ")
    @Column(length = 4)
    @Size(min = 4, max = 4)
    @XmlAttribute(name = "TERRIFNSFL")
    private String terrIfnsFl;

    @Description("Код ИФНС ЮЛ")
    @Column(length = 4)
    @Size(min = 4, max = 4)
    @XmlAttribute(name = "IFNSUL")
    private String ifnsUl;

    @Description("Код территориального участка ИФНС ЮЛ")
    @Column(length = 4)
    @Size(min = 4, max = 4)
    @XmlAttribute(name = "TERRIFNSUL")
    private String terrIfnsUl;

    @Description("ОКАТО")
    @Column(length = 11)
    @Size(min = 11, max = 11)
    @XmlAttribute(name = "OKATO")
    private String okato;

    @Description("ОКТМО")
    @Column(length = 8)
    @Size(min = 8, max = 8)
    @XmlAttribute(name = "OKATO")
    private String oktmo;

    @Description("Дата внесения записи")
    @Column(nullable = false)
    @NotNull
    @Past
    @XmlAttribute(name = "UPDATEDATE", required = true)
    private Date updateDate;

    @Description("Начало действия записи")
    @Column(nullable = false)
    @NotNull
    @XmlAttribute(name = "STARTDATE", required = true)
    private Date startDate;

    @Description("Окончание действия записи")
    @Column(nullable = false)
    @NotNull
    @XmlAttribute(name = "ENDDATE", required = true)
    private Date endDate;

    @Description("Внешний ключ на нормативный документ")
    @FiasRef(NormativeDocument.class)
    @Column
    @XmlAttribute(name = "NORMDOC")
    private UUID normDoc;
}
