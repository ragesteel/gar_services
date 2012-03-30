package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.persistence.Id;
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
public class House extends AbstractHouse {

    /** Номер дома. */
    @Size(min = 1, max = 10)
    @XmlAttribute(name = "HOUSENUM")
    private String houseNum;

    /** Признак владения. */
    @FiasRef(EstateStatus.class)
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
    @FiasRef(StructureStatus.class)
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "STRSTATUS")
    private Integer strStatus;

    /** Уникальный идентификатор записи дома. */
    @NotNull
    @XmlAttribute(name = "HOUSEID", required = true)
    private UUID houseId;
    
    /** Глобальный уникальный идентификатор дома. */
    @Id
    @NotNull
    @XmlAttribute(name = "HOUSEGUID", required = true)
    private UUID houseGuid;

    /** Состояние дома. */
    @FiasRef(HouseStateStatus.class)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "STATSTATUS", required = true)
    private Integer statStatus;

    /** Счётчик записей домов для КЛАДР 4. */
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "COUNTER", required = true)
    private Integer counter;
}
