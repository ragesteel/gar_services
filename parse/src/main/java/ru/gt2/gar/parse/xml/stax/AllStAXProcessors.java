package ru.gt2.gar.parse.xml.stax;

import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.parse.xml.XMLStreamProcessor;

public class AllStAXProcessors {
    public final XMLStreamProcessor addressObject;
    public final XMLStreamProcessor addressObjectDivision;
    public final XMLStreamProcessor addressObjectType;
    public final XMLStreamProcessor admHierarchy;
    public final XMLStreamProcessor apartmentType;
    public final XMLStreamProcessor apartment;
    public final XMLStreamProcessor operationType;
    public final XMLStreamProcessor carPlace;
    public final XMLStreamProcessor changeHistory;
    public final XMLStreamProcessor houseType;
    public final XMLStreamProcessor house;
    public final XMLStreamProcessor munHierarchy;
    public final XMLStreamProcessor normativeDocType;
    public final XMLStreamProcessor normativeDocKind;
    public final XMLStreamProcessor normativeDoc;
    public final XMLStreamProcessor objectLevel;
    public final XMLStreamProcessor paramType;
    public final XMLStreamProcessor addrObjParam;
    public final XMLStreamProcessor housesParam;
    public final XMLStreamProcessor apartmentsParam;
    public final XMLStreamProcessor roomsParam;
    public final XMLStreamProcessor steadsParam;
    public final XMLStreamProcessor carPlacesParam;
    public final XMLStreamProcessor reestrObject;
    public final XMLStreamProcessor roomType;
    public final XMLStreamProcessor room;
    public final XMLStreamProcessor stead;
    public final XMLStreamProcessor addHouseType;

    public AllStAXProcessors(int batchSize) {
        addressObject = StAXStreamProcessor.forAddressObject(batchSize);
        addressObjectDivision = StAXStreamProcessor.forAddressObjectDivision(batchSize);
        addressObjectType = StAXStreamProcessor.forAddressObjectType(batchSize);
        admHierarchy = StAXStreamProcessor.forAdmHierarchy(batchSize);
        apartmentType = StAXStreamProcessor.forApartmentType(batchSize);
        apartment = StAXStreamProcessor.forApartment(batchSize);
        operationType = StAXStreamProcessor.forOperationType(batchSize);
        carPlace = StAXStreamProcessor.forCarPlace(batchSize);
        changeHistory = StAXStreamProcessor.forChangeHistory(batchSize);
        houseType = StAXStreamProcessor.forHouseType(batchSize);
        house = StAXStreamProcessor.forHouse(batchSize);
        munHierarchy = StAXStreamProcessor.forMunHierarchy(batchSize);
        normativeDocType = StAXStreamProcessor.forNormativeDocType(batchSize);
        normativeDocKind = StAXStreamProcessor.forNormativeDocKind(batchSize);
        normativeDoc = StAXStreamProcessor.forNormativeDoc(batchSize);
        objectLevel = StAXStreamProcessor.forObjectLevel(batchSize);
        paramType = StAXStreamProcessor.forParamType(batchSize);
        addrObjParam = StAXStreamProcessor.forAddrObjParam(batchSize);
        housesParam = StAXStreamProcessor.forHousesParam(batchSize);
        apartmentsParam = StAXStreamProcessor.forApartmentsParam(batchSize);
        roomsParam = StAXStreamProcessor.forRoomsParam(batchSize);
        steadsParam = StAXStreamProcessor.forSteadsParam(batchSize);
        carPlacesParam = StAXStreamProcessor.forCarPlacesParam(batchSize);
        reestrObject = StAXStreamProcessor.forReestrObject(batchSize);
        roomType = StAXStreamProcessor.forRoomType(batchSize);
        room = StAXStreamProcessor.forRoom(batchSize);
        stead = StAXStreamProcessor.forStead(batchSize);
        addHouseType = StAXStreamProcessor.forAddHouseType(batchSize);
    }

    public XMLStreamProcessor getProcessor(GarType garType) {
        return switch (garType) {
            case ADDR_OBJ -> addressObject;
            case ADDR_OBJ_DIVISION -> addressObjectDivision;
            case ADDR_OBJ_TYPES -> addressObjectType;
            case ADM_HIERARCHY -> admHierarchy;
            case APARTMENT_TYPES -> apartmentType;
            case APARTMENTS -> apartment;
            case OPERATION_TYPES -> operationType;
            case CARPLACES -> carPlace;
            case CHANGE_HISTORY -> changeHistory;
            case HOUSE_TYPES -> houseType;
            case HOUSES -> house;
            case MUN_HIERARCHY -> munHierarchy;
            case NORMATIVE_DOCS_TYPES -> normativeDocType;
            case NORMATIVE_DOCS_KINDS -> normativeDocKind;
            case NORMATIVE_DOCS -> normativeDoc;
            case OBJECT_LEVELS -> objectLevel;
            case PARAM_TYPES -> paramType;
            case ADDR_OBJ_PARAMS -> addrObjParam;
            case HOUSES_PARAMS -> housesParam;
            case APARTMENTS_PARAMS -> apartmentsParam;
            case ROOMS_PARAMS -> roomsParam;
            case STEADS_PARAMS -> steadsParam;
            case CARPLACES_PARAMS -> carPlacesParam;
            case REESTR_OBJECTS -> reestrObject;
            case ROOM_TYPES -> roomType;
            case ROOMS -> room;
            case STEADS -> stead;
            case ADDHOUSE_TYPES -> addHouseType;
        };
    }
}
