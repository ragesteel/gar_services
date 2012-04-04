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

@Description("Тип адресного объекта")
@Entity
@XmlType(propOrder = {"level", "scName", "socrName", "kodTSt"})
public class AddressObjectType implements Serializable {
    @Description("Уровень адресного объекта")
    @Column(nullable = false, scale = 10)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "LEVEL", required = true)
    private Integer level;

    @Description("Краткое наименование типа объекта")
    @Column(nullable = false, length = 10)
    @NotNull
    @Size(min = 0, max = 10)
    @XmlAttribute(name = "SCNAME", required = true)
    private String scName;
    
    @Description("Полное наименование типа объекта")
    @Column(nullable = false, length = 29)
    @NotNull
    @Size(min = 0, max = 29)
    @XmlAttribute(name = "SOCRNAME", required = true)
    private String socrName;

    @Description("Ключевое поле")
    @Id
    @Column(nullable = false, length = 3)
    @NotNull
    @Size(min = 1, max = 3)
    @XmlAttribute(name = "KOD_T_ST", required = true)
    private String kodTSt;
}
