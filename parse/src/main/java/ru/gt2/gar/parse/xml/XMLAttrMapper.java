package ru.gt2.gar.parse.xml;

import ru.gt2.gar.domain.AddressObject;
import ru.gt2.gar.domain.AddressObjectDivision;
import ru.gt2.gar.domain.AddressObjectType;
import ru.gt2.gar.domain.AdmHierarchy;
import ru.gt2.gar.domain.Apartment;
import ru.gt2.gar.domain.ApartmentType;
import ru.gt2.gar.domain.CarPlace;
import ru.gt2.gar.domain.ChangeHistory;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.domain.House;
import ru.gt2.gar.domain.HouseType;
import ru.gt2.gar.domain.MunHierarchy;
import ru.gt2.gar.domain.NormativeDoc;
import ru.gt2.gar.domain.NormativeDocKind;
import ru.gt2.gar.domain.NormativeDocType;
import ru.gt2.gar.domain.ObjectLevel;
import ru.gt2.gar.domain.OperationType;
import ru.gt2.gar.domain.Param;
import ru.gt2.gar.domain.ParamType;
import ru.gt2.gar.domain.ReestrObject;
import ru.gt2.gar.domain.Room;
import ru.gt2.gar.domain.RoomType;
import ru.gt2.gar.domain.Stead;

import java.util.function.BiFunction;

import static ru.gt2.gar.parse.xml.AttrConverter.BOOL_ACTIVE;
import static ru.gt2.gar.parse.xml.AttrConverter.BOOL_ACTUAL_ACTIVE;
import static ru.gt2.gar.parse.xml.AttrConverter.NOTHING;

// TODO Придумать более правильное название для этого класса, какая-нибудь GarParseData
public class XMLAttrMapper<T> {
    public static final XMLAttrMapper<AddressObject> ADDRESS_OBJECT = new XMLAttrMapper<>(
            AddressObject.class, GarType.ADDR_OBJ, BOOL_ACTUAL_ACTIVE);
    public static final XMLAttrMapper<AddressObjectDivision> ADDRESS_OBJECT_DIVISION = new XMLAttrMapper<>(
            AddressObjectDivision.class, GarType.ADDR_OBJ_DIVISION);
    public static final XMLAttrMapper<AddressObjectType> ADDRESS_OBJECT_TYPE = new XMLAttrMapper<>(
            AddressObjectType.class, GarType.ADDR_OBJ_TYPES);
    public static final XMLAttrMapper<AdmHierarchy> ADM_HIERARCHY = new XMLAttrMapper<>(
            AdmHierarchy.class, GarType.ADM_HIERARCHY, BOOL_ACTIVE);
    public static final XMLAttrMapper<ApartmentType> APARTMENT_TYPE = new XMLAttrMapper<>(
            ApartmentType.class, GarType.APARTMENT_TYPES);
    public static final XMLAttrMapper<Apartment> APARTMENT = new XMLAttrMapper<>(
            Apartment.class, GarType.APARTMENTS, BOOL_ACTUAL_ACTIVE);
    public static final XMLAttrMapper<OperationType> OPERATION_TYPE = new XMLAttrMapper<>(
            OperationType.class, GarType.OPERATION_TYPES);
    public static final XMLAttrMapper<CarPlace> CAR_PLACE = new XMLAttrMapper<>(
            CarPlace.class, GarType.CARPLACES, BOOL_ACTUAL_ACTIVE);
    public static final XMLAttrMapper<ChangeHistory> CHANGE_HISTORY = new XMLAttrMapper<>(
            ChangeHistory.class, GarType.CHANGE_HISTORY);
    public static final XMLAttrMapper<HouseType> HOUSE_TYPE = new XMLAttrMapper<>(
            HouseType.class, GarType.HOUSE_TYPES);
    public static final XMLAttrMapper<House> HOUSE = new XMLAttrMapper<>(
            House.class, GarType.HOUSES, BOOL_ACTUAL_ACTIVE);
    public static final XMLAttrMapper<MunHierarchy> MUN_HIERARCHY = new XMLAttrMapper<>(
            MunHierarchy.class, GarType.MUN_HIERARCHY, BOOL_ACTIVE);
    public static final XMLAttrMapper<NormativeDoc> NORMATIVE_DOC = new XMLAttrMapper<>(
            NormativeDoc.class, GarType.NORMATIVE_DOCS);
    public static final XMLAttrMapper<NormativeDocKind> NORMATIVE_DOC_KIND = new XMLAttrMapper<>(
            NormativeDocKind.class, GarType.NORMATIVE_DOCS_KINDS);
    public static final XMLAttrMapper<NormativeDocType> NORMATIVE_DOC_TYPE = new XMLAttrMapper<>(
            NormativeDocType.class, GarType.NORMATIVE_DOCS_TYPES);
    public static final XMLAttrMapper<ObjectLevel> OBJECT_LEVEL = new XMLAttrMapper<>(
            ObjectLevel.class, GarType.OBJECT_LEVELS);
    public static final XMLAttrMapper<ParamType> PARAM_TYPE = new XMLAttrMapper<>(
            ParamType.class, GarType.PARAM_TYPES);
    public static final XMLAttrMapper<Param> ADDR_OBJ_PARAM = new XMLAttrMapper<>(
            Param.class, GarType.ADDR_OBJ_PARAMS);
    public static final XMLAttrMapper<Param> HOUSES_PARAM = new XMLAttrMapper<>(
            Param.class, GarType.HOUSES_PARAMS);
    public static final XMLAttrMapper<Param> APARTMENTS_PARAM = new XMLAttrMapper<>(
            Param.class, GarType.APARTMENTS_PARAMS);
    public static final XMLAttrMapper<Param> ROOMS_PARAM = new XMLAttrMapper<>(
            Param.class, GarType.ROOMS_PARAMS);
    public static final XMLAttrMapper<Param> STEADS_PARAM = new XMLAttrMapper<>(
            Param.class, GarType.STEADS_PARAMS);
    public static final XMLAttrMapper<Param> CARPLACES_PARAM = new XMLAttrMapper<>(
            Param.class, GarType.CARPLACES_PARAMS);
    public static final XMLAttrMapper<ReestrObject> REESTR_OBJECT = new XMLAttrMapper<>(
            ReestrObject.class, GarType.REESTR_OBJECTS, BOOL_ACTIVE);
    public static final XMLAttrMapper<RoomType> ROOM_TYPE = new XMLAttrMapper<>(
            RoomType.class, GarType.ROOM_TYPES);
    public static final XMLAttrMapper<Room> ROOM = new XMLAttrMapper<>(
            Room.class, GarType.ROOMS, BOOL_ACTUAL_ACTIVE);
    public static final XMLAttrMapper<Stead> STEAD = new XMLAttrMapper<>(
            Stead.class, GarType.STEADS, BOOL_ACTUAL_ACTIVE);
    public static final XMLAttrMapper<HouseType> ADD_HOUSE_TYPE = new XMLAttrMapper<>(
            HouseType.class, GarType.ADDHOUSE_TYPES);

    public final Class<T> valueClass;
    public final GarType garType;
    public final BiFunction<String, String, String> valueProcessing;

    private XMLAttrMapper(Class<T> valueClass, GarType garType,
                          BiFunction<String, String, String> valueProcessing) {
        this.valueClass = valueClass;
        this.garType = garType;
        this.valueProcessing = valueProcessing;
    }

    private XMLAttrMapper(Class<T> valueClass, GarType garType) {
        this(valueClass, garType, NOTHING);
    }

    /*
    public static XMLAttrMapper<Record> forGarType(GarType garType) {
        return switch (garType) {
            case ADDR_OBJ -> ADDRESS_OBJECT;
            case ADDR_OBJ_DIVISION -> ADDRESS_OBJECT_DIVISION;
            case ADDR_OBJ_TYPES -> ADDRESS_OBJECT_TYPE;
            case ADM_HIERARCHY -> ADM_HIERARCHY;
            case APARTMENT_TYPES -> APARTMENT_TYPE;
            case APARTMENTS -> APARTMENT;
            case CARPLACES -> CAR_PLACE;
            case CHANGE_HISTORY -> CHANGE_HISTORY;
            case HOUSE_TYPES -> HOUSE_TYPE;
            case HOUSES -> HOUSE;
            case MUN_HIERARCHY -> MUN_HIERARCHY;
            case NORMATIVE_DOCS -> NORMATIVE_DOC;
            case NORMATIVE_DOCS_KINDS -> NORMATIVE_DOC_KIND;
            case NORMATIVE_DOCS_TYPES -> NORMATIVE_DOC_TYPE;
            case OBJECT_LEVELS -> OBJECT_LEVEL;
            case PARAM_TYPES -> PARAM_TYPE;
            case ADDR_OBJ_PARAMS -> ADDR_OBJ_PARAM;
            case HOUSES_PARAMS -> HOUSES_PARAM;
            case APARTMENTS_PARAMS -> APARTMENTS_PARAM;
            case ROOMS_PARAMS -> ROOMS_PARAM;
            case STEADS_PARAMS -> STEADS_PARAM;
            case CARPLACES_PARAMS -> CARPLACES_PARAM;
            case REESTR_OBJECTS -> REESTR_OBJECT;
            case ROOM_TYPES -> ROOM_TYPE;
            case ROOMS -> ROOM;
            case STEADS -> STEAD;
            case OPERATION_TYPES -> OPERATION_TYPE;
            case ADDHOUSE_TYPES -> ADD_HOUSE_TYPE;
        };
    }*/
}
