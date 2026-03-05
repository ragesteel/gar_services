package ru.gt2.gar.parse.xml;

import ru.gt2.gar.domain.GarType;

import java.util.function.BiFunction;

import static ru.gt2.gar.parse.xml.AttrConverter.BOOL_ACTIVE;
import static ru.gt2.gar.parse.xml.AttrConverter.BOOL_ACTUAL_ACTIVE;
import static ru.gt2.gar.parse.xml.AttrConverter.NOTHING;

// TODO Убрать ссылку на объект, ибо она и так имеется в GarType.
// TODO Придумать более правильное название для этого класса, какая-нибудь GarParseData
public class XMLAttrMapper {
    public static final XMLAttrMapper ADDRESS_OBJECT = new XMLAttrMapper(
            GarType.ADDR_OBJ, BOOL_ACTUAL_ACTIVE);
    public static final XMLAttrMapper ADDRESS_OBJECT_DIVISION = new XMLAttrMapper(
            GarType.ADDR_OBJ_DIVISION);
    public static final XMLAttrMapper ADDRESS_OBJECT_TYPE = new XMLAttrMapper(
            GarType.ADDR_OBJ_TYPES);
    public static final XMLAttrMapper ADM_HIERARCHY = new XMLAttrMapper(
            GarType.ADM_HIERARCHY, BOOL_ACTIVE);
    public static final XMLAttrMapper APARTMENT_TYPE = new XMLAttrMapper(
            GarType.APARTMENT_TYPES);
    public static final XMLAttrMapper APARTMENT = new XMLAttrMapper(
            GarType.APARTMENTS, BOOL_ACTUAL_ACTIVE);
    public static final XMLAttrMapper OPERATION_TYPE = new XMLAttrMapper(
            GarType.OPERATION_TYPES);
    public static final XMLAttrMapper CAR_PLACE = new XMLAttrMapper(
            GarType.CARPLACES, BOOL_ACTUAL_ACTIVE);
    public static final XMLAttrMapper CHANGE_HISTORY = new XMLAttrMapper(
            GarType.CHANGE_HISTORY);
    public static final XMLAttrMapper HOUSE_TYPE = new XMLAttrMapper(
            GarType.HOUSE_TYPES);
    public static final XMLAttrMapper HOUSE = new XMLAttrMapper(
            GarType.HOUSES, BOOL_ACTUAL_ACTIVE);
    public static final XMLAttrMapper MUN_HIERARCHY = new XMLAttrMapper(
            GarType.MUN_HIERARCHY, BOOL_ACTIVE);
    public static final XMLAttrMapper NORMATIVE_DOC = new XMLAttrMapper(
            GarType.NORMATIVE_DOCS);
    public static final XMLAttrMapper NORMATIVE_DOC_KIND = new XMLAttrMapper(
            GarType.NORMATIVE_DOCS_KINDS);
    public static final XMLAttrMapper NORMATIVE_DOC_TYPE = new XMLAttrMapper(
            GarType.NORMATIVE_DOCS_TYPES);
    public static final XMLAttrMapper OBJECT_LEVEL = new XMLAttrMapper(
            GarType.OBJECT_LEVELS);
    public static final XMLAttrMapper PARAM_TYPE = new XMLAttrMapper(
            GarType.PARAM_TYPES);
    public static final XMLAttrMapper ADDR_OBJ_PARAM = new XMLAttrMapper(
            GarType.ADDR_OBJ_PARAMS);
    public static final XMLAttrMapper HOUSES_PARAM = new XMLAttrMapper(
            GarType.HOUSES_PARAMS);
    public static final XMLAttrMapper APARTMENTS_PARAM = new XMLAttrMapper(
            GarType.APARTMENTS_PARAMS);
    public static final XMLAttrMapper ROOMS_PARAM = new XMLAttrMapper(
            GarType.ROOMS_PARAMS);
    public static final XMLAttrMapper STEADS_PARAM = new XMLAttrMapper(
            GarType.STEADS_PARAMS);
    public static final XMLAttrMapper CARPLACES_PARAM = new XMLAttrMapper(
            GarType.CARPLACES_PARAMS);
    public static final XMLAttrMapper REESTR_OBJECT = new XMLAttrMapper(
            GarType.REESTR_OBJECTS, BOOL_ACTIVE);
    public static final XMLAttrMapper ROOM_TYPE = new XMLAttrMapper(
            GarType.ROOM_TYPES);
    public static final XMLAttrMapper ROOM = new XMLAttrMapper(
            GarType.ROOMS, BOOL_ACTUAL_ACTIVE);
    public static final XMLAttrMapper STEAD = new XMLAttrMapper(
            GarType.STEADS, BOOL_ACTUAL_ACTIVE);
    public static final XMLAttrMapper ADD_HOUSE_TYPE = new XMLAttrMapper(
            GarType.ADDHOUSE_TYPES);

    public final GarType garType;
    public final BiFunction<String, String, String> valueProcessing;

    private XMLAttrMapper(GarType garType,
                          BiFunction<String, String, String> valueProcessing) {
        this.garType = garType;
        this.valueProcessing = valueProcessing;
    }

    private XMLAttrMapper(GarType garType) {
        this(garType, NOTHING);
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
