package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.UUID;

/**
 * Описание места расположения имущественных объектов.
 *
 * @author rage
 */
@XmlType(propOrder = {"location", "postalCode", "ifnsFl", "terrIfnsFl", "ifnsUl", "terrIfnsUl", "okato", "oktmo",
    "updateDate", "landId", "landGuid", "aoGuid", "startDate", "endDate", "normDoc"})
@ToString
public class Landmark {
    /**
     * Месторасположение ориентира
     */
    @NotNull
    @Size(min = 1, max = 500)
    @XmlAttribute(name = "LOCATION", required = true)
    private String location;

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
    
    /** Уникальный идентификатор записи ориентира. */
    @NotNull
    @XmlAttribute(name = "LANDID", required = true)
    private UUID landId;
    
    /** Глобальный уникальный идентификатор ориентира. */
    @Id
    @NotNull
    @XmlAttribute(name = "LANDGUID", required = true)
    private UUID landGuid;

    /** Уникальный идентификатор родителшьского объекта (улицы, города, населенного пункта и т.п.). */
    @NotNull
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

    /** Внешний ключ на нормативный документ. */
    @XmlAttribute(name = "NORMDOC")
    private UUID normDoc;

}
