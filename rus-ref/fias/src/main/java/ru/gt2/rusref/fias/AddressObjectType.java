package ru.gt2.rusref.fias;

import ru.gt2.rusref.Description;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Description("Тип адресного объекта")
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "AddressObjectLevelAndName", columnNames = {"level", "scName"})})
@XmlType(propOrder = {"level", "scName", "socrName", "kodTSt"})
public class AddressObjectType implements Serializable {
    @Description("Уровень адресного объекта")
    @FiasRef(AddressObjectLevel.class)
    @Column(nullable = false, scale = 10)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    @XmlAttribute(name = "LEVEL", required = true)
    private Integer level;

    @Description("Краткое наименование типа объекта")
    @Column(nullable = true, length = 10)
    @Size(min = 0, max = 10)
    @XmlAttribute(name = "SCNAME")
    private String scName;
    
    @Description("Полное наименование типа объекта")
    @Column(nullable = false, length = 50)
    @NotNull
    @Size(min = 0, max = 50)
    @XmlAttribute(name = "SOCRNAME", required = true)
    private String socrName;

    @Description("Ключевое поле")
    @Id
    @Column(nullable = false, length = 4)
    @NotNull
    @Size(min = 1, max = 4)
    @XmlAttribute(name = "KOD_T_ST", required = true)
    private String kodTSt;
}
