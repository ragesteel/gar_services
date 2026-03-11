package ru.gt2.gar.domain;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Все разновидности объектов для файлов, по крайней мере для которых имеется описание.
 */
public enum GarType {
    ADDR_OBJ(AddressObject.class, "AddressObjects", "Object"),
    ADDR_OBJ_DIVISION(AddressObjectDivision.class, "Item"),
    ADDR_OBJ_TYPES(AddressObjectType.class),
    ADM_HIERARCHY(AdmHierarchy.class, "Item"),
    APARTMENT_TYPES(ApartmentType.class),
    APARTMENTS(Apartment.class),
    CARPLACES(CarPlace.class),
    CHANGE_HISTORY(ChangeHistory.class, "Item"),
    HOUSE_TYPES(HouseType.class),
    HOUSES(House.class),
    MUN_HIERARCHY(MunHierarchy.class, "Item"),
    NORMATIVE_DOCS(NormativeDoc.class, "NormDoc"),
    NORMATIVE_DOCS_KINDS(NormativeDocKind.class, "NDocKind"),
    NORMATIVE_DOCS_TYPES(NormativeDocType.class, "NDocType"),
    OBJECT_LEVELS(ObjectLevel.class),
    OPERATION_TYPES(OperationType.class),
    // PARAM,
    PARAM_TYPES(ParamType.class),
    REESTR_OBJECTS(ReestrObject.class, "Reestr_Objects", "Object"),
    ROOM_TYPES(RoomType.class),
    ROOMS(Room.class),
    STEADS(Stead.class),
    // Описаны в AS_PARAM_*
    ADDR_OBJ_PARAMS(Param.class),
    HOUSES_PARAMS(Param.class),
    APARTMENTS_PARAMS(Param.class),
    ROOMS_PARAMS(Param.class),
    STEADS_PARAMS(Param.class),
    CARPLACES_PARAMS(Param.class),

    // APARTMENT_TYPES, HOUSE_TYPES, OPERATION_TYPES тоже очень похожи, разница только в ограничиениях длины полей
    // PARAM_TYPES — тот-же набор, но вместо shortName поле называется code, ROOM_TYPES = APARTMENT_TYPES.

    // Недокументированные, но имеющиеся в gar_xml 2025-08-29, видимо тот-же набор полей, что и HOUSE_TYPES.
    ADDHOUSE_TYPES(HouseType.class);

    public final Class<? extends GarRecord> recordClass;

    public final String outerTagName;

    public final String elementName;
    
    /// Корневые справочники
    public static final ImmutableSet<GarType> ROOT_REFS = ImmutableSet.of(
            GarType.ADDHOUSE_TYPES,
            GarType.ADDR_OBJ_TYPES,
            GarType.APARTMENT_TYPES,
            GarType.HOUSE_TYPES,
            GarType.NORMATIVE_DOCS_KINDS,
            GarType.NORMATIVE_DOCS_TYPES,
            GarType.OBJECT_LEVELS,
            GarType.OPERATION_TYPES,
            GarType.PARAM_TYPES,
            GarType.ROOM_TYPES);

    /// Региональные данные
    public static final ImmutableSet<GarType> REGIONAL_DATA = ImmutableSet.of(
            GarType.ADDR_OBJ,
            GarType.ADDR_OBJ_DIVISION,
            GarType.ADM_HIERARCHY,
            GarType.APARTMENTS,
            GarType.CARPLACES,
            GarType.CHANGE_HISTORY,
            GarType.HOUSES,
            GarType.MUN_HIERARCHY,
            GarType.NORMATIVE_DOCS,
            GarType.ADDR_OBJ_PARAMS,
            GarType.HOUSES_PARAMS,
            GarType.APARTMENTS_PARAMS,
            GarType.ROOMS_PARAMS,
            GarType.STEADS_PARAMS,
            GarType.CARPLACES_PARAMS,
            GarType.REESTR_OBJECTS,
            GarType.ROOMS,
            GarType.STEADS);

    GarType(Class<? extends GarRecord> recordClass) {
        this.recordClass = recordClass;
        this.elementName = recordClass.getSimpleName().toUpperCase();
        outerTagName = elementName + 'S';
    }

    GarType(Class<? extends GarRecord> recordClass, String elementName) {
        this(recordClass, elementName.toUpperCase() + 'S', elementName.toUpperCase());
    }

    GarType(Class<? extends GarRecord> recordClass, String outerTagName, String elementName) {
        this.recordClass = recordClass;
        this.outerTagName = outerTagName.toUpperCase();
        this.elementName = elementName.toUpperCase();
    }

    public static Optional<GarType> findGarType(Class<? extends GarRecord> recordClass) {
        for (GarType garType : values()) {
            if (garType.recordClass.equals(recordClass)) {
                return Optional.of(garType);
            }
        }
        return Optional.empty();
    }

    @VisibleForTesting
    static Set<Class<? extends GarRecord>> getRecordClasses() {
        Set<Class<? extends GarRecord>> result = new HashSet<>();
        for (GarType garType : values()) {
            result.add(garType.recordClass);
        }
        return result;
    }

    @VisibleForTesting
    static void forEach(@Nullable Consumer<Class<? extends GarRecord>> classConsumer,
                        @Nullable Consumer<RecordComponent> componentConsumer) {
        if (null != classConsumer) {
            GarType.getRecordClasses().forEach(classConsumer);
        }
        if (null != componentConsumer) {
            GarType.getRecordClasses().stream().map(Class::getRecordComponents).
                    flatMap(Arrays::stream).forEach(componentConsumer);
        }
    }
}
