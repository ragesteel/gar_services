package ru.gt2.gar.parse.xml.stax;

import ru.gt2.gar.domain.GarType;

import java.util.function.BiFunction;

import static ru.gt2.gar.parse.xml.stax.JacksonAttrConverter.BOOL_ACTIVE;
import static ru.gt2.gar.parse.xml.stax.JacksonAttrConverter.BOOL_ACTUAL_ACTIVE;
import static ru.gt2.gar.parse.xml.stax.JacksonAttrConverter.NOTHING;

// TODO Убрать ссылку на объект, ибо она и так имеется в GarType.
// TODO Придумать более правильное название для этого класса, какая-нибудь GarParseData
public class StAXAttrMapper {
    public static final StAXAttrMapper ADDRESS_OBJECT = new StAXAttrMapper(
            GarType.ADDR_OBJ, BOOL_ACTUAL_ACTIVE);
    public static final StAXAttrMapper ADDRESS_OBJECT_DIVISION = new StAXAttrMapper(
            GarType.ADDR_OBJ_DIVISION);
    public static final StAXAttrMapper ADDRESS_OBJECT_TYPE = new StAXAttrMapper(
            GarType.ADDR_OBJ_TYPES);
    public static final StAXAttrMapper ADM_HIERARCHY = new StAXAttrMapper(
            GarType.ADM_HIERARCHY, BOOL_ACTIVE);
    public static final StAXAttrMapper APARTMENT_TYPE = new StAXAttrMapper(
            GarType.APARTMENT_TYPES);
    public static final StAXAttrMapper APARTMENT = new StAXAttrMapper(
            GarType.APARTMENTS, BOOL_ACTUAL_ACTIVE);
    public static final StAXAttrMapper OPERATION_TYPE = new StAXAttrMapper(
            GarType.OPERATION_TYPES);
    public static final StAXAttrMapper CAR_PLACE = new StAXAttrMapper(
            GarType.CARPLACES, BOOL_ACTUAL_ACTIVE);
    public static final StAXAttrMapper CHANGE_HISTORY = new StAXAttrMapper(
            GarType.CHANGE_HISTORY);
    public static final StAXAttrMapper HOUSE_TYPE = new StAXAttrMapper(
            GarType.HOUSE_TYPES);
    public static final StAXAttrMapper HOUSE = new StAXAttrMapper(
            GarType.HOUSES, BOOL_ACTUAL_ACTIVE);
    public static final StAXAttrMapper MUN_HIERARCHY = new StAXAttrMapper(
            GarType.MUN_HIERARCHY, BOOL_ACTIVE);
    public static final StAXAttrMapper NORMATIVE_DOC = new StAXAttrMapper(
            GarType.NORMATIVE_DOCS);
    public static final StAXAttrMapper NORMATIVE_DOC_KIND = new StAXAttrMapper(
            GarType.NORMATIVE_DOCS_KINDS);
    public static final StAXAttrMapper NORMATIVE_DOC_TYPE = new StAXAttrMapper(
            GarType.NORMATIVE_DOCS_TYPES);
    public static final StAXAttrMapper OBJECT_LEVEL = new StAXAttrMapper(
            GarType.OBJECT_LEVELS);
    public static final StAXAttrMapper PARAM_TYPE = new StAXAttrMapper(
            GarType.PARAM_TYPES);
    public static final StAXAttrMapper ADDR_OBJ_PARAM = new StAXAttrMapper(
            GarType.ADDR_OBJ_PARAMS);
    public static final StAXAttrMapper HOUSES_PARAM = new StAXAttrMapper(
            GarType.HOUSES_PARAMS);
    public static final StAXAttrMapper APARTMENTS_PARAM = new StAXAttrMapper(
            GarType.APARTMENTS_PARAMS);
    public static final StAXAttrMapper ROOMS_PARAM = new StAXAttrMapper(
            GarType.ROOMS_PARAMS);
    public static final StAXAttrMapper STEADS_PARAM = new StAXAttrMapper(
            GarType.STEADS_PARAMS);
    public static final StAXAttrMapper CARPLACES_PARAM = new StAXAttrMapper(
            GarType.CARPLACES_PARAMS);
    public static final StAXAttrMapper REESTR_OBJECT = new StAXAttrMapper(
            GarType.REESTR_OBJECTS, BOOL_ACTIVE);
    public static final StAXAttrMapper ROOM_TYPE = new StAXAttrMapper(
            GarType.ROOM_TYPES);
    public static final StAXAttrMapper ROOM = new StAXAttrMapper(
            GarType.ROOMS, BOOL_ACTUAL_ACTIVE);
    public static final StAXAttrMapper STEAD = new StAXAttrMapper(
            GarType.STEADS, BOOL_ACTUAL_ACTIVE);
    public static final StAXAttrMapper ADD_HOUSE_TYPE = new StAXAttrMapper(
            GarType.ADDHOUSE_TYPES);

    public final GarType garType;
    public final BiFunction<String, String, String> valueProcessing;

    private StAXAttrMapper(GarType garType,
                           BiFunction<String, String, String> valueProcessing) {
        this.garType = garType;
        this.valueProcessing = valueProcessing;
    }

    private StAXAttrMapper(GarType garType) {
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
