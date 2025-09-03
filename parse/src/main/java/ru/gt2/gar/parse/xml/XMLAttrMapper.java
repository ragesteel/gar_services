package ru.gt2.gar.parse.xml;

import ru.gt2.gar.parse.domain.AddressObject;

import java.util.function.BiFunction;

import static ru.gt2.gar.parse.xml.Converter.BOOL_ACTUAL_ACTIVE;

public class XMLAttrMapper<T> {
    public static XMLAttrMapper<AddressObject> ADDRESS_OBJECT =
            new XMLAttrMapper<>("ADDRESSOBJECTS", "OBJECT", AddressObject.class, BOOL_ACTUAL_ACTIVE);

    public final String rootName;
    public final String elementName;

    public final Class<T> valueClass;
    public final BiFunction<String, String, String> valueProcessing;

    private XMLAttrMapper(String rootName, String elementName, Class<T> valueClass, BiFunction<String, String, String> valueProcessing) {
        this.rootName = rootName;
        this.elementName = elementName;
        this.valueClass = valueClass;
        this.valueProcessing = valueProcessing;
    }
}
