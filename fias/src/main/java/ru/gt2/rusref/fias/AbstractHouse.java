package ru.gt2.rusref.fias;

import ru.gt2.rusref.Description;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.UUID;

/**
 * Общая часть для House, HouseInterval и Landmark.
 */
@XmlTransient
public abstract class AbstractHouse extends AbstractAddressObject {
    @Description("Уникальный идентификатор родителшьского объекта (улицы, города, населенного пункта и т.п.)")
    @FiasRef(AddressObject.class)
    @NotNull
    @XmlAttribute(name = "AOGUID", required = true)
    private UUID aoGuid;
}
