package ru.gt2.rusref.fias;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для статуса актальности.
 */
@XmlRootElement(name = "AddressObjectTypes")
@Getter
public class AddressObjectTypes implements Container<AddressObjectType> {
    @XmlElement(name = "AddressObjectType", required = true)
    private List<AddressObjectType> list;
}
