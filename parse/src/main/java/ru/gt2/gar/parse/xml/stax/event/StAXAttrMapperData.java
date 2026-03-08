package ru.gt2.gar.parse.xml.stax.event;

import com.google.common.base.Functions;
import lombok.Getter;
import ru.gt2.gar.domain.GarType;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.gt2.gar.parse.xml.stax.event.JacksonAttrConverter.BOOL_ACTIVE;
import static ru.gt2.gar.parse.xml.stax.event.JacksonAttrConverter.BOOL_ACTUAL_ACTIVE;
import static ru.gt2.gar.parse.xml.stax.event.JacksonAttrConverter.NOTHING;

public class StAXAttrMapperData {

    @Getter
    public final GarType garType;
    public final BiFunction<String, String, String> valueProcessing;

    private static final Map<GarType, StAXAttrMapperData> FOR_TYPE;

    static {
        FOR_TYPE = Stream.of(
                new StAXAttrMapperData(GarType.ADDR_OBJ, BOOL_ACTUAL_ACTIVE),
                new StAXAttrMapperData(GarType.ADDR_OBJ, BOOL_ACTUAL_ACTIVE),
                new StAXAttrMapperData(GarType.ADDR_OBJ_DIVISION),
                new StAXAttrMapperData(GarType.ADDR_OBJ_TYPES),
                new StAXAttrMapperData(GarType.ADM_HIERARCHY, BOOL_ACTIVE),
                new StAXAttrMapperData(GarType.APARTMENT_TYPES),
                new StAXAttrMapperData(GarType.APARTMENTS, BOOL_ACTUAL_ACTIVE),
                new StAXAttrMapperData(GarType.OPERATION_TYPES),
                new StAXAttrMapperData(GarType.CARPLACES, BOOL_ACTUAL_ACTIVE),
                new StAXAttrMapperData(GarType.CHANGE_HISTORY),
                new StAXAttrMapperData(GarType.HOUSE_TYPES),
                new StAXAttrMapperData(GarType.HOUSES, BOOL_ACTUAL_ACTIVE),
                new StAXAttrMapperData(GarType.MUN_HIERARCHY, BOOL_ACTIVE),
                new StAXAttrMapperData(GarType.NORMATIVE_DOCS),
                new StAXAttrMapperData(GarType.NORMATIVE_DOCS_KINDS),
                new StAXAttrMapperData(GarType.NORMATIVE_DOCS_TYPES),
                new StAXAttrMapperData(GarType.OBJECT_LEVELS),
                new StAXAttrMapperData(GarType.PARAM_TYPES),
                new StAXAttrMapperData(GarType.ADDR_OBJ_PARAMS),
                new StAXAttrMapperData(GarType.HOUSES_PARAMS),
                new StAXAttrMapperData(GarType.APARTMENTS_PARAMS),
                new StAXAttrMapperData(GarType.ROOMS_PARAMS),
                new StAXAttrMapperData(GarType.STEADS_PARAMS),
                new StAXAttrMapperData(GarType.CARPLACES_PARAMS),
                new StAXAttrMapperData(GarType.REESTR_OBJECTS, BOOL_ACTIVE),
                new StAXAttrMapperData(GarType.ROOM_TYPES),
                new StAXAttrMapperData(GarType.ROOMS, BOOL_ACTUAL_ACTIVE),
                new StAXAttrMapperData(GarType.STEADS, BOOL_ACTUAL_ACTIVE),
                new StAXAttrMapperData(GarType.ADDHOUSE_TYPES)
        ).collect(Collectors.toMap(StAXAttrMapperData::getGarType, Functions.identity()));
    }

    private StAXAttrMapperData(GarType garType,
                               BiFunction<String, String, String> valueProcessing) {
        this.garType = garType;
        this.valueProcessing = valueProcessing;
    }

    private StAXAttrMapperData(GarType garType) {
        this(garType, NOTHING);
    }

    public static StAXAttrMapperData forType(GarType garType) {
        return FOR_TYPE.get(garType);
    }
}
