package ru.gt2.rusref.fias;

import ru.gt2.rusref.Description;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.UUID;

@Description("Сведения по номерам домов улиц городов и населенных пунктов, номера земельных участков и т.п.")
@Entity
@XmlType(propOrder = {"postalCode", "ifnsFl", "terrIfnsFl", "ifnsUl", "terrIfnsUl", "okato", "oktmo", "updateDate",
    "houseNum", "estStatus", "buildNum", "structNum", "strStatus", "houseId", "houseGuid", "aoGuid", "startDate",
    "endDate", "statStatus", "normDoc", "counter"})
public class House extends AbstractHouse {
    @Description("Номер дома")
    @Column(length = 20)
    @Size(min = 1, max = 20)
    @XmlAttribute(name = "HOUSENUM")
    private String houseNum;

    @Description("Признак владения")
    @FiasRef(EstateStatus.class)
    @Column(nullable = false, scale = 10)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "ESTSTATUS", required = true)
    private Integer estStatus;

    @Description("Номер корпуса")
    @Column(length = 10)
    @Size(min = 0, max = 10)
    @XmlAttribute(name = "BUILDNUM")
    private String buildNum;

    @Description("Номер строения")
    @Column(length = 10)
    @Size(min = 0, max = 10)
    @XmlAttribute(name = "STRUCTNUM")
    private String structNum;

    @Description("Признак строения")
    @FiasRef(StructureStatus.class)
    @Column(scale = 10)
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "STRSTATUS")
    private Integer strStatus;

    @Description("Уникальный идентификатор записи дома")
    @Id
    @Column(nullable = false)
    @NotNull
    @XmlAttribute(name = "HOUSEID", required = true)
    private UUID houseId;
    
    @Description("Глобальный уникальный идентификатор дома")
    @Column(nullable = false)
    @NotNull
    @XmlAttribute(name = "HOUSEGUID", required = true)
    private UUID houseGuid;

    @Description("Состояние дома")
    @FiasRef(HouseStateStatus.class)
    @Column(nullable = false, scale = 10)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "STATSTATUS", required = true)
    private Integer statStatus;

    @Description("Счётчик записей домов для КЛАДР 4")
    @Column(nullable = false, scale = 10)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "COUNTER", required = true)
    private Integer counter;
}
