package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.Date;

/**
 * Описание место расположения имущественных объектов.
 *
 * @author rage
 */
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
    @Size(min = 36, max = 36)
    @XmlAttribute(name = "LANDID", required = true)
    private String landId;
    
    /** Глобальный уникальный идентификатор ориентира. */
    @NotNull
    @Size(min = 36, max = 36)
    @XmlAttribute(name = "LANDGUID", required = true)
    private String landGuid;

    /** Уникальный идентификатор родителшьского объекта (улицы, города, населенного пункта и т.п.). */
    @NotNull
    @Size(min = 36, max = 36)
    @XmlAttribute(name = "AOGUID", required = true)
    private String aoGuid;

    /** Начало действия записи. */
    @NotNull
    @XmlAttribute(name = "STARTDATE", required = true)
    private Date startDate;

    /** Окончание действия записи. */
    @NotNull
    @XmlAttribute(name = "ENDDATE", required = true)
    private Date endDate;

    /** Внешний ключ на нормативный документ. */
    @Size(min = 36, max = 36)
    @XmlAttribute(name = "NORMDOC")
    private String normDoc;

}
