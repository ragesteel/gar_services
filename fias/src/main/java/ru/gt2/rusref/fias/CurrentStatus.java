package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Статус актуальности КЛАДР 4.0.
 *
 * В XSD также же упоминания идентификаторов, только не особо точные.
 */
@XmlType(propOrder = {"curentStId", "name"})
@ToString
public class CurrentStatus {
    /** Идентификатор статуса (ключ) */
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "CURENTSTID", required = true)
    private Integer curentStId;

    /** Наименование */
    @NotNull
    @Size(min = 1, max = 100)
    @XmlAttribute(name = "NAME", required = true)
    private String name;
}
