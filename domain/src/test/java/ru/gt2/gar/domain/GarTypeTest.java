package ru.gt2.gar.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.OptionalInt;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GarTypeTest {
    record ExpectedTagName(GarType garType, String outer, String inner) {}

    private final static ExpectedTagName[] EXPECTED_TAG_NAMES = {
            new ExpectedTagName(GarType.ADDR_OBJ, "AddressObjects", "Object"),
            new ExpectedTagName(GarType.ADDR_OBJ_DIVISION, "Items", "Item"),
            new ExpectedTagName(GarType.ADDR_OBJ_TYPES, "AddressObjectTypes", "AddressObjectType"),
            new ExpectedTagName(GarType.ADM_HIERARCHY, "Items", "Item"),
            new ExpectedTagName(GarType.APARTMENT_TYPES, "ApartmentTypes", "ApartmentType"),
            new ExpectedTagName(GarType.APARTMENTS, "Apartments", "Apartment"),
            new ExpectedTagName(GarType.CARPLACES, "CarPlaces", "CarPlace"),
            new ExpectedTagName(GarType.CHANGE_HISTORY, "Items", "Item"),
            new ExpectedTagName(GarType.HOUSE_TYPES, "HouseTypes", "HouseType"),
            new ExpectedTagName(GarType.HOUSES, "Houses", "House"),
            new ExpectedTagName(GarType.MUN_HIERARCHY, "Items", "Item"),
            new ExpectedTagName(GarType.NORMATIVE_DOCS, "NormDocs", "NormDoc"),
            new ExpectedTagName(GarType.NORMATIVE_DOCS_KINDS, "NDocKinds", "NDocKind"),
            new ExpectedTagName(GarType.NORMATIVE_DOCS_TYPES, "NDocTypes", "NDocType"),
            new ExpectedTagName(GarType.OBJECT_LEVELS, "ObjectLevels", "ObjectLevel"),
            new ExpectedTagName(GarType.OPERATION_TYPES, "OperationTypes", "OperationType"),
            new ExpectedTagName(GarType.PARAM_TYPES, "ParamTypes", "ParamType"),
            new ExpectedTagName(GarType.REESTR_OBJECTS, "Reestr_Objects", "Object"),
            new ExpectedTagName(GarType.ROOM_TYPES, "RoomTypes", "RoomType"),
            new ExpectedTagName(GarType.ROOMS, "Rooms", "Room"),
            new ExpectedTagName(GarType.STEADS, "Steads", "Stead"),
            new ExpectedTagName(GarType.ADDR_OBJ_PARAMS, "Params", "Param"),
            new ExpectedTagName(GarType.HOUSES_PARAMS, "Params", "Param"),
            new ExpectedTagName(GarType.APARTMENTS_PARAMS, "Params", "Param"),
            new ExpectedTagName(GarType.ROOMS_PARAMS, "Params", "Param"),
            new ExpectedTagName(GarType.STEADS_PARAMS, "Params", "Param"),
            new ExpectedTagName(GarType.CARPLACES_PARAMS, "Params", "Param"),
            new ExpectedTagName(GarType.ADDHOUSE_TYPES, "HouseTypes", "HouseType"),
    };

    @Test
    public void testGetRecordClasses() {
        Assertions.assertEquals(22, GarType.getRecordClasses().size());
    }

    @Test
    public void getMaxNameLength() {
        OptionalInt max = Arrays.stream(GarType.values()).map(Enum::name).mapToInt(String::length).max();
        assertTrue(max.isPresent());
        assertEquals(Gar.MAX_NAME_LENGTH, max.getAsInt());
    }

    @Test
    public void checkTagNames() {
        Set<GarType> allGarTypes = EnumSet.allOf(GarType.class);
        for (ExpectedTagName expectedTagName : EXPECTED_TAG_NAMES) {
            GarType garType = expectedTagName.garType;
            assertTrue(allGarTypes.remove(garType), () -> "Multiple instances for garType " + garType);

            assertEquals(expectedTagName.outer.toUpperCase(), garType.outerTagName);
            assertEquals(expectedTagName.inner.toUpperCase(), garType.elementName);
        }
        assertTrue(allGarTypes.isEmpty(), () -> "Not definition for types: " + allGarTypes);
    }
}