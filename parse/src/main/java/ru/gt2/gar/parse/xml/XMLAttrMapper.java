package ru.gt2.gar.parse.xml;

import ru.gt2.gar.parse.domain.AddressObject;
import ru.gt2.gar.parse.domain.AddressObjectDivision;
import ru.gt2.gar.parse.domain.GarType;

import java.util.function.BiFunction;

import static ru.gt2.gar.parse.xml.AttrConverter.BOOL_ACTUAL_ACTIVE;
import static ru.gt2.gar.parse.xml.AttrConverter.NOTHING;

// TODO Придумать более правильное название для этого класса, какая-нибудь GarParseData
public class XMLAttrMapper<T> {
    public static final XMLAttrMapper<AddressObject> ADDRESS_OBJECT = new XMLAttrMapper<>(
            "ADDRESSOBJECTS", "OBJECT", AddressObject.class, GarType.ADDR_OBJ, BOOL_ACTUAL_ACTIVE);
    public static final XMLAttrMapper<AddressObjectDivision> ADDRESS_OBJECT_DIVISION = new XMLAttrMapper<>(
            "ITEMS", "ITEM", AddressObjectDivision.class, GarType.ADDR_OBJ_DIVISION, NOTHING);

    public final String rootName;
    public final String elementName;
    public final Class<T> valueClass;
    public final GarType garType;
    public final BiFunction<String, String, String> valueProcessing;

    private XMLAttrMapper(String rootName, String elementName, Class<T> valueClass, GarType garType,
                          BiFunction<String, String, String> valueProcessing) {
        this.rootName = rootName;
        this.elementName = elementName;
        this.valueClass = valueClass;
        this.garType = garType;
        this.valueProcessing = valueProcessing;
    }
}
