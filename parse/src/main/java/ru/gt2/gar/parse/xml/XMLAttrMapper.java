package ru.gt2.gar.parse.xml;

import ru.gt2.gar.parse.domain.AddressObject;

public class XMLAttrMapper<T> {
    public static XMLAttrMapper<AddressObject> ADDRESS_OBJECT =
            new XMLAttrMapper<>("ADDRESSOBJECTS", "OBJECT", AddressObject.class);

    public final String rootName;
    public final String elementName;

    public final Class<T> valueClass;

    private XMLAttrMapper(String rootName, String elementName, Class<T> valueClass) {
        this.rootName = rootName;
        this.elementName = elementName;
        this.valueClass = valueClass;
    }
}
