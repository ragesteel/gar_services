package ru.gt2.gar.parse.xml;

import ru.gt2.gar.parse.domain.AddressObject;
import ru.gt2.gar.parse.domain.AddressObjectDivision;
import ru.gt2.gar.parse.domain.AddressObjectType;
import ru.gt2.gar.parse.domain.AdmHierarchy;
import ru.gt2.gar.parse.domain.Apartment;
import ru.gt2.gar.parse.domain.ApartmentType;
import ru.gt2.gar.parse.domain.OperationType;
import ru.gt2.gar.parse.domain.GarType;

import java.util.function.BiFunction;

import static ru.gt2.gar.parse.xml.AttrConverter.BOOL_ACTIVE;
import static ru.gt2.gar.parse.xml.AttrConverter.BOOL_ACTUAL_ACTIVE;
import static ru.gt2.gar.parse.xml.AttrConverter.NOTHING;

// TODO Придумать более правильное название для этого класса, какая-нибудь GarParseData
public class XMLAttrMapper<T> {
    public static final XMLAttrMapper<AddressObject> ADDRESS_OBJECT = new XMLAttrMapper<>(
            "AddressObjects", "Object", AddressObject.class, GarType.ADDR_OBJ, BOOL_ACTUAL_ACTIVE);
    public static final XMLAttrMapper<AddressObjectDivision> ADDRESS_OBJECT_DIVISION = new XMLAttrMapper<>(
            "Items", "Item", AddressObjectDivision.class, GarType.ADDR_OBJ_DIVISION);
    public static final XMLAttrMapper<AddressObjectType> ADDRESS_OBJECT_TYPE = new XMLAttrMapper<>(
            "AddressObjectTypes", "AddressObjectType", AddressObjectType.class, GarType.ADDR_OBJ_TYPES);
    public static final XMLAttrMapper<AdmHierarchy> ADM_HIERARCHY = new XMLAttrMapper<>(
            "Items", "Item", AdmHierarchy.class, GarType.ADM_HIERARCHY, BOOL_ACTIVE);
    public static final XMLAttrMapper<ApartmentType> APARTMENT_TYPE = new XMLAttrMapper<>(
            "ApartmentTypes", "ApartmentType", ApartmentType.class, GarType.APARTMENT_TYPES);
    public static final XMLAttrMapper<Apartment> APARTMENT = new XMLAttrMapper<>(
            "Apartments", "Apartment", Apartment.class, GarType.APARTMENTS, BOOL_ACTUAL_ACTIVE);
    public static final XMLAttrMapper<OperationType> OPERATION_TYPE = new XMLAttrMapper<>(
            "OperationTypes", "OperationType", OperationType.class, GarType.OPERATION_TYPES);

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

    private XMLAttrMapper(String rootName, String elementName, Class<T> valueClass, GarType garType) {
        this(rootName, elementName, valueClass, garType, NOTHING);
    }
}
