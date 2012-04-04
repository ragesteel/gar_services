package ru.gt2.rusref.fias;

import ru.gt2.rusref.Description;

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

@Description("Описание места расположения имущественных объектов")
@XmlType(propOrder = {"location", "postalCode", "ifnsFl", "terrIfnsFl", "ifnsUl", "terrIfnsUl", "okato", "oktmo",
    "updateDate", "landId", "landGuid", "aoGuid", "startDate", "endDate", "normDoc"})
public class Landmark extends AbstractHouse {
    @Description("Месторасположение ориентира")
    @NotNull
    @Size(min = 1, max = 500)
    @XmlAttribute(name = "LOCATION", required = true)
    private String location;

    @Description("Уникальный идентификатор записи ориентира")
    @NotNull
    @XmlAttribute(name = "LANDID", required = true)
    private UUID landId;
    
    @Description("Глобальный уникальный идентификатор ориентира")
    @Id
    @NotNull
    @XmlAttribute(name = "LANDGUID", required = true)
    private UUID landGuid;
}
