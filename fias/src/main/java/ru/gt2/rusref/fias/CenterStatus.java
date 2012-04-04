package ru.gt2.rusref.fias;

import ru.gt2.rusref.Description;

import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Description("Статус центра")
@XmlType(propOrder = {"centerStId", "name"})
public class CenterStatus implements Serializable {
    @Description("Идентификатор статуса (ключ)")
    @Id
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "CENTERSTID", required = true)
    private Integer centerStId;

    @Description("Наименование")
    @NotNull
    @Size(min = 1, max = 100)
    @XmlAttribute(name = "NAME", required = true)
    private String name;
}
