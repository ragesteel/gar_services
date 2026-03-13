// АВТОМАТИЧЕСКИ СГЕНЕРИРОВАННЫЙ ФАЙЛ
// Вам следует изменить класс гененратора (TableMappingGenerator) вместо ручного редактирования!
package ru.gt2.gar.db.tm;

import com.google.common.collect.ImmutableMap;
import ru.gt2.gar.domain.GarRecord;
import ru.gt2.gar.domain.GarType;

import javax.annotation.processing.Generated;

@Generated(
        value = "ru.gt2.gar.db.tm.TableMappingGenerator",
        date = "2026-03-13T18:35:21.146943+03:00"
)
public class TableMappings {
    private static final ImmutableMap<GarType, TableMapping<? extends GarRecord, ? extends Number>> MAP = 
    ImmutableMap.<GarType, TableMapping<? extends GarRecord, ? extends Number>>builder()
        .put(GarType.ADDR_OBJ, new AddressObjectTM())
        .put(GarType.ADDR_OBJ_DIVISION, new AddressObjectDivisionTM())
        .put(GarType.ADDR_OBJ_TYPES, new AddressObjectTypeTM())
        .put(GarType.ADM_HIERARCHY, new AdmHierarchyTM())
        .put(GarType.APARTMENT_TYPES, new ApartmentTypeTM())
        .put(GarType.APARTMENTS, new ApartmentTM())
        .put(GarType.CARPLACES, new CarPlaceTM())
        .put(GarType.CHANGE_HISTORY, new ChangeHistoryTM())
        .put(GarType.HOUSE_TYPES, new HouseTypeTM())
        .put(GarType.HOUSES, new HouseTM())
        .put(GarType.MUN_HIERARCHY, new MunHierarchyTM())
        .put(GarType.NORMATIVE_DOCS, new NormativeDocTM())
        .put(GarType.NORMATIVE_DOCS_KINDS, new NormativeDocKindTM())
        .put(GarType.NORMATIVE_DOCS_TYPES, new NormativeDocTypeTM())
        .put(GarType.OBJECT_LEVELS, new ObjectLevelTM())
        .put(GarType.OPERATION_TYPES, new OperationTypeTM())
        .put(GarType.PARAM_TYPES, new ParamTypeTM())
        .put(GarType.REESTR_OBJECTS, new ReestrObjectTM())
        .put(GarType.ROOM_TYPES, new RoomTypeTM())
        .put(GarType.ROOMS, new RoomTM())
        .put(GarType.STEADS, new SteadTM())
        .put(GarType.ADDR_OBJ_PARAMS, new ParamTM())
        .put(GarType.HOUSES_PARAMS, new ParamTM())
        .put(GarType.APARTMENTS_PARAMS, new ParamTM())
        .put(GarType.ROOMS_PARAMS, new ParamTM())
        .put(GarType.STEADS_PARAMS, new ParamTM())
        .put(GarType.CARPLACES_PARAMS, new ParamTM())
        .put(GarType.ADDHOUSE_TYPES, new HouseTypeTM())
        .build();

    public static <T extends GarRecord, K extends Number> TableMapping<T, K> get(GarType type) {
        return (TableMapping<T, K>) MAP.get(type);
    }
}
