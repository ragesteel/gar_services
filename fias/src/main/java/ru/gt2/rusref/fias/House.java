package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.UUID;

/**
 * Сведения по номерам домов улиц городов и населенных пунктов, номера земельных участков и т.п..
 *
 * @author rage
 */
@XmlType(propOrder = {"postalCode", "ifnsFl", "terrIfnsFl", "ifnsUl", "terrIfnsUl", "okato", "oktmo", "updateDate",
    "houseNum", "estStatus", "buildNum", "structNum", "strStatus", "houseId", "houseGuid", "aoGuid", "startDate",
    "endDate", "statStatus", "normDoc", "counter"})
@ToString
public class House {

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

    /** Номер дома. */
    @Size(min = 1, max = 10)
    @XmlAttribute(name = "HOUSENUM")
    private String houseNum;

    /** Признак владения. */
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "ESTSTATUS", required = true)
    private Integer estStatus;

    /** Номер корпуса. */
    @Size(min = 0, max = 10)
    @XmlAttribute(name = "BUILDNUM")
    private String buildNum;

    /** Номер строения. */
    @Size(min = 0, max = 10)
    @XmlAttribute(name = "STRUCTNUM")
    private String structNum;

    /** Признак строения. */
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "STRSTATUS", required = true)
    private Integer strStatus;

    /** Уникальный идентификатор записи дома. */
    @NotNull
    @Size(min = 36, max = 36)
    @XmlAttribute(name = "HOUSEID", required = true)
    private UUID houseId;
    
    /** Глобальный уникальный идентификатор интервала домов. */
    @NotNull
    @Size(min = 36, max = 36)
    @XmlAttribute(name = "HOUSEGUID", required = true)
    private UUID houseGuid;

    /** Уникальный идентификатор родителшьского объекта (улицы, города, населенного пункта и т.п.). */
    @NotNull
    @Size(min = 36, max = 36)
    @XmlAttribute(name = "AOGUID", required = true)
    private UUID aoGuid;

    /** Начало действия записи. */
    @NotNull
    @XmlAttribute(name = "STARTDATE", required = true)
    private Date startDate;

    /** Окончание действия записи. */
    @NotNull
    @XmlAttribute(name = "ENDDATE", required = true)
    private Date endDate;
    
    /** Состояние дома. */
    // FIXME Добавить ссылку на @XmlTransient StructureStatus
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "STATSTATUS", required = true)
    private Integer statStatus;

    /** Внешний ключ на нормативный документ. */
    @Size(min = 36, max = 36)
    @XmlAttribute(name = "NORMDOC")
    private UUID normDoc;

    /** Счётчик записей домов для КЛАДР 4. */
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "COUNTER", required = true)
    private Integer counter;
}
