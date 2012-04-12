package ru.gt2.rusref.fias;

import ru.gt2.rusref.Description;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Description("Уровень адресного объекта")
@FiasRef(AddressObjectType.class)
@Entity
@XmlType(propOrder = {"level"})
public class AddressObjectLevel implements Serializable {
    @Description("Уровень адресного объекта")
    @Id
    @Column(nullable = false, scale = 10)
    @NotNull
    @Digits(integer = 10, fraction = 0)
    private Integer level;

}
