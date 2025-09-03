package ru.gt2.gar.parse.xml;

import ru.gt2.gar.parse.domain.AddressObject;

public class XmlAttrMapper<T> {
    public static XmlAttrMapper<AddressObject> ADDRESS_OBJECT =
            new XmlAttrMapper<>("OBJECT", AddressObject.class);

    public final String elementName;

    public final Class<T> valueClass;

    private XmlAttrMapper(String elementName, Class<T> valueClass) {
        this.elementName = elementName;
        this.valueClass = valueClass;
    }
}
