package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для классификатора адресообразующих элементов.
 */
@XmlRootElement(name = "AddressObjects")
@ToString
public class AddressObjects {
    @XmlElement(name = "Object", required = true)
    protected List<AddressObject> addressObject;
}
