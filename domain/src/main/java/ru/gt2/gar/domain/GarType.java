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

    public final Class<? extends Record> recordClass;

    public final String outerTagName;

    public final String elementName;

    GarType(Class<? extends Record> recordClass) {
        this.recordClass = recordClass;
        this.elementName = recordClass.getSimpleName();
        outerTagName = elementName + 's';
    }

    GarType(Class<? extends Record> recordClass, String elementName) {
        this(recordClass, elementName + 's', elementName);
    }

    GarType(Class<? extends Record> recordClass, String outerTagName, String elementName) {
        this.recordClass = recordClass;
        this.outerTagName = outerTagName;
        this.elementName = elementName;
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
