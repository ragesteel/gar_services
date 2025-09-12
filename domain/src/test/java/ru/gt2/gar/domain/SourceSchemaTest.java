package ru.gt2.gar.domain;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SourceSchemaTest {
    @Test
    public void testXSD() {
        assertEquals(            "AS_ADDR_OBJ_2_251_01_04_01_01.xsd", getXsd(AddressObject.class));
        assertEquals(               "AS_PARAM_2_251_02_04_01_01.xsd", getXsd(Param.class));
        assertEquals(      "AS_ADDR_OBJ_TYPES_2_251_03_04_01_01.xsd", getXsd(AddressObjectType.class));
        assertEquals(       "AS_ADM_HIERARCHY_2_251_04_04_01_01.xsd", getXsd(AdmHierarchy.class));
        assertEquals(          "AS_APARTMENTS_2_251_05_04_01_01.xsd", getXsd(Apartment.class));
        assertEquals(           "AS_CARPLACES_2_251_06_04_01_01.xsd", getXsd(CarPlace.class));
        assertEquals(     "AS_APARTMENT_TYPES_2_251_07_04_01_01.xsd", getXsd(ApartmentType.class));
        assertEquals(              "AS_HOUSES_2_251_08_04_01_01.xsd", getXsd(House.class));
        assertEquals("AS_NORMATIVE_DOCS_KINDS_2_251_09_04_01_01.xsd", getXsd(NormativeDocKind.class));
        assertEquals(       "AS_MUN_HIERARCHY_2_251_10_04_01_01.xsd", getXsd(MunHierarchy.class));
        assertEquals(      "AS_NORMATIVE_DOCS_2_251_11_04_01_01.xsd", getXsd(NormativeDoc.class));
        assertEquals(       "AS_OBJECT_LEVELS_2_251_12_04_01_01.xsd", getXsd(ObjectLevel.class));
        assertEquals(         "AS_HOUSE_TYPES_2_251_13_04_01_01.xsd", getXsd(HouseType.class));
        assertEquals(     "AS_OPERATION_TYPES_2_251_14_04_01_01.xsd", getXsd(OperationType.class));
        assertEquals(               "AS_ROOMS_2_251_15_04_01_01.xsd", getXsd(Room.class));
        assertEquals("AS_NORMATIVE_DOCS_TYPES_2_251_16_04_01_01.xsd", getXsd(NormativeDocType.class));
        assertEquals(          "AS_ROOM_TYPES_2_251_17_04_01_01.xsd", getXsd(RoomType.class));
        assertEquals(              "AS_STEADS_2_251_18_04_01_01.xsd", getXsd(Stead.class));
        assertEquals(   "AS_ADDR_OBJ_DIVISION_2_251_19_04_01_01.xsd", getXsd(AddressObjectDivision.class));
        assertEquals(         "AS_PARAM_TYPES_2_251_20_04_01_01.xsd", getXsd(ParamType.class));
        assertEquals(        "AS_CHANGE_HISTORY_251_21_04_01_01.xsd", getXsd(ChangeHistory.class));
        assertEquals(      "AS_REESTR_OBJECTS_2_251_22_04_01_01.xsd", getXsd(ReestrObject.class));
    }

    private String getXsd(Class<? extends Record> recordClass) {
        SourceSchema annotation = recordClass.getAnnotation(SourceSchema.class);
        if (null == annotation) {
            return "";
        }

        String name;
        if (recordClass.equals(Param.class)) {
            name = "PARAM";
        } else {
            Optional<GarType> garType = GarType.findGarType(recordClass);
            if (garType.isEmpty()) {
                return "";
            }
            name = garType.get().name();
        }

        return String.format("AS_%s%s_%s_0%s_%s.xsd",
                name,
                annotation.suffix(),
                annotation.part(),
                annotation.format().replace(".", "_"),
                annotation.schema());
    }
}