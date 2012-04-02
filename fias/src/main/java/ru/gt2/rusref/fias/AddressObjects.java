package ru.gt2.rusref.fias;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для классификатора адресообразующих элементов.
 */
@XmlRootElement(name = "AddressObjects")
@Getter
public class AddressObjects implements Container<AddressObject> {
    @XmlElement(name = "Object", required = true)
    protected List<AddressObject> list;
}
