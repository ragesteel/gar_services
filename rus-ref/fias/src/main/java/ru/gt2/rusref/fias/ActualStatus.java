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
import java.io.Serializable;

@Description("Статус актуальности")
@Entity
@XmlType(propOrder = {"actStatId", "name"})
public class ActualStatus implements Serializable {
    @Description("Идентификатор статуса (ключ)")
    @Id
    @Column(nullable = false, scale = 10)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "ACTSTATID", required = true)
    private Integer actStatId;

    @Description("Наименование")
    @Column(nullable = false, length = 100)
    @NotNull
    @Size(min = 1, max = 100)
    @XmlAttribute(name = "NAME", required = true)
    private String name;
}
