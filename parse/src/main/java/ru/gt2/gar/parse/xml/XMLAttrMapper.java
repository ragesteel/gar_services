package ru.gt2.gar.parse.xml;

import ru.gt2.gar.parse.domain.AddressObject;
import ru.gt2.gar.parse.domain.AddressObjectDivision;
import ru.gt2.gar.parse.domain.AddressObjectType;
import ru.gt2.gar.parse.domain.AdmHierarchy;
import ru.gt2.gar.parse.domain.Apartment;
import ru.gt2.gar.parse.domain.ApartmentType;
import ru.gt2.gar.parse.domain.OperationType;
import ru.gt2.gar.parse.domain.GarType;
import ru.gt2.gar.parse.domain.CarPlace;
import ru.gt2.gar.parse.domain.ChangeHistory;
import ru.gt2.gar.parse.domain.HouseType;
import ru.gt2.gar.parse.domain.House;
import ru.gt2.gar.parse.domain.MunHierarchy;
import ru.gt2.gar.parse.domain.NormativeDocType;
import ru.gt2.gar.parse.domain.NormativeDocKind;
import ru.gt2.gar.parse.domain.NormativeDoc;
import ru.gt2.gar.parse.domain.ObjectLevel;
import ru.gt2.gar.parse.domain.Param;
import ru.gt2.gar.parse.domain.ParamType;
import ru.gt2.gar.parse.domain.ReestrObject;
import ru.gt2.gar.parse.domain.Room;
import ru.gt2.gar.parse.domain.RoomType;

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
    public static final XMLAttrMapper<CarPlace> CAR_PLACE = new XMLAttrMapper<>(
            "CarPlaces", "CarPlace", CarPlace.class, GarType.CARPLACES, BOOL_ACTUAL_ACTIVE);
    public static final XMLAttrMapper<ChangeHistory> CHANGE_HISTORY = new XMLAttrMapper<>(
            "Items", "Item", ChangeHistory.class, GarType.CHANGE_HISTORY);
    public static final XMLAttrMapper<HouseType> HOUSE_TYPE = new XMLAttrMapper<>(
            "HouseTypes", "HouseType", HouseType.class, GarType.HOUSE_TYPES);
    public static final XMLAttrMapper<House> HOUSE = new XMLAttrMapper<>(
            "Houses", "House", House.class, GarType.HOUSES, BOOL_ACTUAL_ACTIVE);
    public static final XMLAttrMapper<MunHierarchy> MUN_HIERARCHY = new XMLAttrMapper<>(
            "Items", "Item", MunHierarchy.class, GarType.MUN_HIERARCHY, BOOL_ACTIVE);
    public static final XMLAttrMapper<NormativeDoc> NORMATIVE_DOC = new XMLAttrMapper<>(
            "NormDocs", "NormDoc", NormativeDoc.class, GarType.NORMATIVE_DOCS);
    public static final XMLAttrMapper<NormativeDocKind> NORMATIVE_DOC_KIND = new XMLAttrMapper<>(
            "NDocKinds", "NDocKind", NormativeDocKind.class, GarType.NORMATIVE_DOCS_KINDS);
    public static final XMLAttrMapper<NormativeDocType> NORMATIVE_DOC_TYPE = new XMLAttrMapper<>(
            "NDocTypes", "NDocType", NormativeDocType.class, GarType.NORMATIVE_DOCS_TYPES);
    public static final XMLAttrMapper<ObjectLevel> OBJECT_LEVEL = new XMLAttrMapper<>(
            "ObjectLevels", "ObjectLevel", ObjectLevel.class, GarType.OBJECT_LEVELS);
    public static final XMLAttrMapper<ParamType> PARAM_TYPE = new XMLAttrMapper<>(
            "ParamTypes", "ParamType", ParamType.class, GarType.PARAM_TYPES);
    public static final XMLAttrMapper<Param> ADDR_OBJ_PARAM = new XMLAttrMapper<>(
            "Params", "Param", Param.class, GarType.ADDR_OBJ_PARAMS);
    public static final XMLAttrMapper<Param> HOUSES_PARAM = new XMLAttrMapper<>(
            "Params", "Param", Param.class, GarType.HOUSES_PARAMS);
    public static final XMLAttrMapper<Param> APARTMENTS_PARAM = new XMLAttrMapper<>(
            "Params", "Param", Param.class, GarType.APARTMENTS_PARAMS);
    public static final XMLAttrMapper<Param> ROOMS_PARAM = new XMLAttrMapper<>(
            "Params", "Param", Param.class, GarType.ROOMS_PARAMS);
    public static final XMLAttrMapper<Param> STEADS_PARAM = new XMLAttrMapper<>(
            "Params", "Param", Param.class, GarType.STEADS_PARAMS);
    public static final XMLAttrMapper<Param> CARPLACES_PARAM = new XMLAttrMapper<>(
            "Params", "Param", Param.class, GarType.CARPLACES_PARAMS);
    public static final XMLAttrMapper<ReestrObject> REESTR_OBJECT = new XMLAttrMapper<>(
            "Reestr_Objects", "Object", ReestrObject.class, GarType.REESTR_OBJECTS, BOOL_ACTIVE);
    public static final XMLAttrMapper<RoomType> ROOM_TYPE = new XMLAttrMapper<>(
            "RoomTypes", "RoomType", RoomType.class, GarType.ROOM_TYPES);
    public static final XMLAttrMapper<Room> ROOM = new XMLAttrMapper<>(
            "Rooms", "Room", Room.class, GarType.ROOMS, BOOL_ACTUAL_ACTIVE);

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
