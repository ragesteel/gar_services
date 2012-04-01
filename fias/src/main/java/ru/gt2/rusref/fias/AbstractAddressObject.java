package ru.gt2.rusref.fias;

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
@XmlTransient
public abstract class AbstractAddressObject implements Serializable {
    /** Почтовый индекс. */
    @Size(min = 6, max = 6)
    @XmlAttribute(name = "POSTALCODE")
    private String postalCode;

    /** Код ИФНС ФЛ. */
    @Size(min = 4, max = 4)
    @XmlAttribute(name = "IFNSFL")
    private String ifnsFl;

    /** Код территориального участка ИФНС ФЛ. */
    @Size(min = 4, max = 4)
    @XmlAttribute(name = "TERRIFNSFL")
    private String terrIfnsFl;

    /** Код ИФНС ЮЛ. */
    @Size(min = 4, max = 4)
    @XmlAttribute(name = "IFNSUL")
    private String ifnsUl;

    /** Код территориального участка ИФНС ЮЛ. */
    @Size(min = 4, max = 4)
    @XmlAttribute(name = "TERRIFNSUL")
    private String terrIfnsUl;

    /** ОКАТО. */
    @Size(min = 11, max = 11)
    @XmlAttribute(name = "OKATO")
    private String okato;

    /** ОКТМО. */
    @Size(min = 8, max = 8)
    @XmlAttribute(name = "OKATO")
    private String oktmo;

    /** Дата внесения записи. */
    @NotNull
    @Past
    @XmlAttribute(name = "UPDATEDATE", required = true)
    private Date updateDate;

    /** Начало действия записи. */
    @NotNull
    @XmlAttribute(name = "STARTDATE", required = true)
    private Date startDate;

    /** Окончание действия записи. */
    @NotNull
    @XmlAttribute(name = "ENDDATE", required = true)
    private Date endDate;

    /** Внешний ключ на нормативный документ. */
    @FiasRef(NormativeDocument.class)
    @XmlAttribute(name = "NORMDOC")
    private UUID normDoc;
}
