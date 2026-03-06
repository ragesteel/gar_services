package ru.gt2.gar.parse.xml.stax.event;

import ru.gt2.gar.domain.GarType;

import java.util.function.BiFunction;

import static ru.gt2.gar.parse.xml.stax.event.JacksonAttrConverter.BOOL_ACTIVE;
import static ru.gt2.gar.parse.xml.stax.event.JacksonAttrConverter.BOOL_ACTUAL_ACTIVE;
import static ru.gt2.gar.parse.xml.stax.event.JacksonAttrConverter.NOTHING;

public class StAXAttrMapperData {
    public static final StAXAttrMapperData ADDRESS_OBJECT = new StAXAttrMapperData(
            GarType.ADDR_OBJ, BOOL_ACTUAL_ACTIVE);
    public static final StAXAttrMapperData ADDRESS_OBJECT_DIVISION = new StAXAttrMapperData(
            GarType.ADDR_OBJ_DIVISION);
    public static final StAXAttrMapperData ADDRESS_OBJECT_TYPE = new StAXAttrMapperData(
            GarType.ADDR_OBJ_TYPES);
    public static final StAXAttrMapperData ADM_HIERARCHY = new StAXAttrMapperData(
            GarType.ADM_HIERARCHY, BOOL_ACTIVE);
    public static final StAXAttrMapperData APARTMENT_TYPE = new StAXAttrMapperData(
            GarType.APARTMENT_TYPES);
    public static final StAXAttrMapperData APARTMENT = new StAXAttrMapperData(
            GarType.APARTMENTS, BOOL_ACTUAL_ACTIVE);
    public static final StAXAttrMapperData OPERATION_TYPE = new StAXAttrMapperData(
            GarType.OPERATION_TYPES);
    public static final StAXAttrMapperData CAR_PLACE = new StAXAttrMapperData(
            GarType.CARPLACES, BOOL_ACTUAL_ACTIVE);
    public static final StAXAttrMapperData CHANGE_HISTORY = new StAXAttrMapperData(
            GarType.CHANGE_HISTORY);
    public static final StAXAttrMapperData HOUSE_TYPE = new StAXAttrMapperData(
            GarType.HOUSE_TYPES);
    public static final StAXAttrMapperData HOUSE = new StAXAttrMapperData(
            GarType.HOUSES, BOOL_ACTUAL_ACTIVE);
    public static final StAXAttrMapperData MUN_HIERARCHY = new StAXAttrMapperData(
            GarType.MUN_HIERARCHY, BOOL_ACTIVE);
    public static final StAXAttrMapperData NORMATIVE_DOC = new StAXAttrMapperData(
            GarType.NORMATIVE_DOCS);
    public static final StAXAttrMapperData NORMATIVE_DOC_KIND = new StAXAttrMapperData(
            GarType.NORMATIVE_DOCS_KINDS);
    public static final StAXAttrMapperData NORMATIVE_DOC_TYPE = new StAXAttrMapperData(
            GarType.NORMATIVE_DOCS_TYPES);
    public static final StAXAttrMapperData OBJECT_LEVEL = new StAXAttrMapperData(
            GarType.OBJECT_LEVELS);
    public static final StAXAttrMapperData PARAM_TYPE = new StAXAttrMapperData(
            GarType.PARAM_TYPES);
    public static final StAXAttrMapperData ADDR_OBJ_PARAM = new StAXAttrMapperData(
            GarType.ADDR_OBJ_PARAMS);
    public static final StAXAttrMapperData HOUSES_PARAM = new StAXAttrMapperData(
            GarType.HOUSES_PARAMS);
    public static final StAXAttrMapperData APARTMENTS_PARAM = new StAXAttrMapperData(
            GarType.APARTMENTS_PARAMS);
    public static final StAXAttrMapperData ROOMS_PARAM = new StAXAttrMapperData(
            GarType.ROOMS_PARAMS);
    public static final StAXAttrMapperData STEADS_PARAM = new StAXAttrMapperData(
            GarType.STEADS_PARAMS);
    public static final StAXAttrMapperData CARPLACES_PARAM = new StAXAttrMapperData(
            GarType.CARPLACES_PARAMS);
    public static final StAXAttrMapperData REESTR_OBJECT = new StAXAttrMapperData(
            GarType.REESTR_OBJECTS, BOOL_ACTIVE);
    public static final StAXAttrMapperData ROOM_TYPE = new StAXAttrMapperData(
            GarType.ROOM_TYPES);
    public static final StAXAttrMapperData ROOM = new StAXAttrMapperData(
            GarType.ROOMS, BOOL_ACTUAL_ACTIVE);
    public static final StAXAttrMapperData STEAD = new StAXAttrMapperData(
            GarType.STEADS, BOOL_ACTUAL_ACTIVE);
    public static final StAXAttrMapperData ADD_HOUSE_TYPE = new StAXAttrMapperData(
            GarType.ADDHOUSE_TYPES);

    public final GarType garType;
    public final BiFunction<String, String, String> valueProcessing;

    private StAXAttrMapperData(GarType garType,
                               BiFunction<String, String, String> valueProcessing) {
        this.garType = garType;
        this.valueProcessing = valueProcessing;
    }

    private StAXAttrMapperData(GarType garType) {
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
