package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для статуса актальности.
 */
@XmlRootElement(name = "AddressObjectTypes")
@ToString
public class AddressObjectTypes {
    @XmlElement(name = "AddressObjectType", required = true)
    private List<AddressObjectType> addressObjectType;
}
