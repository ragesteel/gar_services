package ru.gt2.rusref.fias;

import ru.gt2.rusref.Description;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.UUID;

@Description("Интервал домов")
@Entity
@XmlType(propOrder = {"postalCode", "ifnsFl", "terrIfnsFl", "ifnsUl", "terrIfnsUl", "okato", "oktmo", "updateDate",
    "intStart", "intEnd", "houseIntId", "intGuid", "aoGuid", "startDate", "endDate", "intStatus", "normDoc"})
public class HouseInterval extends AbstractHouse {
    @Description("Значение начала интервала")
    @Column(nullable = false, scale = 10)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "INTSTART", required = true)
    private Integer intStart;

    @Description("Значение окончания интервала")
    @Column(nullable = false, scale = 10)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "INTEND", required = true)
    private Integer intEnd;

    @Description("Иидентификатор записи интервала домов")
    @Column(nullable = false)
    @NotNull
    @XmlAttribute(name = "HOUSEINTID", required = true)
    private UUID houseIntId;
    
    @Description("Глобальный уникальный идентификатор интервала домов")
    @Id
    @Column(nullable = false)
    @NotNull
    @XmlAttribute(name = "INTGUID", required = true)
    private UUID intGuid;

    @Description("Статус интервала (обычный, четный, нечетный)")
    @FiasRef(IntervalStatus.class)
    @Column(nullable = false, scale = 10)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "INTSTATUS", required = true)
    private Integer intStatus;
}
