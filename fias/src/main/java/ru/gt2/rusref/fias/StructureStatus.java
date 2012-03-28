package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;

/**
 * Признак строения.
 *
 * @author rage
 */
@XmlType(propOrder = {"strStatId", "name", "shortName"})
@ToString
public class StructureStatus {
    /**
     * Признак строения
     */
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlID
    @XmlAttribute(name = "STRSTATID", required = true)
    private Integer strStatId;

    /**
     * Наименование
     */
    @NotNull
    @Size(min = 1, max = 20)
    @XmlAttribute(name = "NAME", required = true)
    private String name;

    /**
     * Краткое наименование
     */
    @Size(min = 1, max = 20)
    @XmlAttribute(name = "SHORTNAME")
    private String shortName;

}
