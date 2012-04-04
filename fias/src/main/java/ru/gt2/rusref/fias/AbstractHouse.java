package ru.gt2.rusref.fias;

import ru.gt2.rusref.Description;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.UUID;

/**
 * Общая часть для House, HouseInterval и Landmark.
 */
@MappedSuperclass
@XmlTransient
public abstract class AbstractHouse extends AbstractAddressObject {
    @Description("Уникальный идентификатор родителшьского объекта (улицы, города, населенного пункта и т.п.)")
    @FiasRef(AddressObject.class)
    @Column(nullable = false)
    @NotNull
    @XmlAttribute(name = "AOGUID", required = true)
    private UUID aoGuid;
}
