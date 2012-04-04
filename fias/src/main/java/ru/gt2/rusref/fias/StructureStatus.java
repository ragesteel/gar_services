package ru.gt2.rusref.fias;

import ru.gt2.rusref.Description;

import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Description("Признак строения")
@XmlType(propOrder = {"strStatId", "name", "shortName"})
public class StructureStatus implements Serializable {
    @Description("Признак строения")
    @Id
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "STRSTATID", required = true)
    private Integer strStatId;

    @Description("Наименование")
    @NotNull
    @Size(min = 1, max = 20)
    @XmlAttribute(name = "NAME", required = true)
    private String name;

    @Description("Краткое наименование")
    @Size(min = 1, max = 20)
    @XmlAttribute(name = "SHORTNAME")
    private String shortName;
}
