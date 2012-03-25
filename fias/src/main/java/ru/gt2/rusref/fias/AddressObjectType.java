package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Тип адресного объекта.
 */
@ToString
public class AddressObjectType {
    /** Уровень адресного объекта. */
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "LEVEL", required = true)
    private Integer level;

    /** Краткое наименование типа объекта. */
    @NotNull
    @Size(min = 0, max = 10)
    @XmlAttribute(name = "SCNAME", required = true)
    private String scName;
    
    /** Полное наименование типа объекта. */
    @NotNull
    @Size(min = 0, max = 29)
    @XmlAttribute(name = "SOCRNAME", required = true)
    private String socrName;

    /** Ключевое поле. */
    @NotNull
    @Size(min = 1, max = 3)
    @XmlAttribute(name = "KOD_T_ST", required = true)
    private String kodTSt;
}
