package ru.gt2.gar.parse.xml;

import ru.gt2.gar.domain.GarType;

public class AllXMLProcessors {
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

    public AllXMLProcessors(int batchSize) {
        addressObject = XMLStreamProcessor.forAddressObject(batchSize);
        addressObjectDivision = XMLStreamProcessor.forAddressObjectDivision(batchSize);
        addressObjectType = XMLStreamProcessor.forAddressObjectType(batchSize);
        admHierarchy = XMLStreamProcessor.forAdmHierarchy(batchSize);
        apartmentType = XMLStreamProcessor.forApartmentType(batchSize);
        apartment = XMLStreamProcessor.forApartment(batchSize);
        operationType = XMLStreamProcessor.forOperationType(batchSize);
        carPlace = XMLStreamProcessor.forCarPlace(batchSize);
        changeHistory = XMLStreamProcessor.forChangeHistory(batchSize);
        houseType = XMLStreamProcessor.forHouseType(batchSize);
        house = XMLStreamProcessor.forHouse(batchSize);
        munHierarchy = XMLStreamProcessor.forMunHierarchy(batchSize);
        normativeDocType = XMLStreamProcessor.forNormativeDocType(batchSize);
        normativeDocKind = XMLStreamProcessor.forNormativeDocKind(batchSize);
        normativeDoc = XMLStreamProcessor.forNormativeDoc(batchSize);
        objectLevel = XMLStreamProcessor.forObjectLevel(batchSize);
        paramType = XMLStreamProcessor.forParamType(batchSize);
        addrObjParam = XMLStreamProcessor.forAddrObjParam(batchSize);
        housesParam = XMLStreamProcessor.forHousesParam(batchSize);
        apartmentsParam = XMLStreamProcessor.forApartmentsParam(batchSize);
        roomsParam = XMLStreamProcessor.forRoomsParam(batchSize);
        steadsParam = XMLStreamProcessor.forSteadsParam(batchSize);
        carPlacesParam = XMLStreamProcessor.forCarPlacesParam(batchSize);
        reestrObject = XMLStreamProcessor.forReestrObject(batchSize);
        roomType = XMLStreamProcessor.forRoomType(batchSize);
        room = XMLStreamProcessor.forRoom(batchSize);
        stead = XMLStreamProcessor.forStead(batchSize);
        addHouseType = XMLStreamProcessor.forAddHouseType(batchSize);
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
