package ru.gt2.gar.domain;

import com.google.common.annotations.VisibleForTesting;
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
    ADDR_OBJ(AddressObject.class),
    ADDR_OBJ_DIVISION(AddressObjectDivision.class),
    ADDR_OBJ_TYPES(AddressObjectType.class),
    ADM_HIERARCHY(AdmHierarchy.class),
    APARTMENT_TYPES(ApartmentType.class),
    APARTMENTS(Apartment.class),
    CARPLACES(CarPlace.class),
    CHANGE_HISTORY(ChangeHistory.class),
    HOUSE_TYPES(HouseType.class),
    HOUSES(House.class),
    MUN_HIERARCHY(MunHierarchy.class),
    NORMATIVE_DOCS(NormativeDoc.class),
    NORMATIVE_DOCS_KINDS(NormativeDocKind.class),
    NORMATIVE_DOCS_TYPES(NormativeDocType.class),
    OBJECT_LEVELS(ObjectLevel.class),
    OPERATION_TYPES(OperationType.class),
    // PARAM,
    PARAM_TYPES(ParamType.class),
    REESTR_OBJECTS(ReestrObject.class),
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

    public final Class<? extends Record> recordClass;

    GarType(Class<? extends Record> recordClass) {
        this.recordClass = recordClass;
    }

    public static Optional<GarType> findGarType(Class<? extends Record> recordClass) {
        for (GarType garType : values()) {
            if (garType.recordClass.equals(recordClass)) {
                return Optional.of(garType);
            }
        }
        return Optional.empty();
    }

    @VisibleForTesting
    static Set<Class<? extends Record>> getRecordClasses() {
        Set<Class<? extends Record>> result = new HashSet<>();
        for (GarType garType : values()) {
            result.add(garType.recordClass);
        }
        return result;
    }

    @VisibleForTesting
    static void forEach(@Nullable Consumer<Class<? extends Record>> classConsumer,
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
