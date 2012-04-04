package ru.gt2.rusref.fias;

import ru.gt2.rusref.Description;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.UUID;

@Description("Описание места расположения имущественных объектов")
@Entity
@XmlType(propOrder = {"location", "postalCode", "ifnsFl", "terrIfnsFl", "ifnsUl", "terrIfnsUl", "okato", "oktmo",
    "updateDate", "landId", "landGuid", "aoGuid", "startDate", "endDate", "normDoc"})
public class Landmark extends AbstractHouse {
    @Description("Месторасположение ориентира")
    @Column(nullable = false, length = 500)
    @NotNull
    @Size(min = 1, max = 500)
    @XmlAttribute(name = "LOCATION", required = true)
    private String location;

    @Description("Уникальный идентификатор записи ориентира")
    @Column(nullable = false)
    @NotNull
    @XmlAttribute(name = "LANDID", required = true)
    private UUID landId;
    
    @Description("Глобальный уникальный идентификатор ориентира")
    @Id
    @Column(nullable = false)
    @NotNull
    @XmlAttribute(name = "LANDGUID", required = true)
    private UUID landGuid;
}
