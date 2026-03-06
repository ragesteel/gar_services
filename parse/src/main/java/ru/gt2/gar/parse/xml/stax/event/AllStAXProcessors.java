package ru.gt2.gar.parse.xml.stax.event;

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
        addressObject = StAXEventStreamProcessor.forAddressObject(batchSize);
        addressObjectDivision = StAXEventStreamProcessor.forAddressObjectDivision(batchSize);
        addressObjectType = StAXEventStreamProcessor.forAddressObjectType(batchSize);
        admHierarchy = StAXEventStreamProcessor.forAdmHierarchy(batchSize);
        apartmentType = StAXEventStreamProcessor.forApartmentType(batchSize);
        apartment = StAXEventStreamProcessor.forApartment(batchSize);
        operationType = StAXEventStreamProcessor.forOperationType(batchSize);
        carPlace = StAXEventStreamProcessor.forCarPlace(batchSize);
        changeHistory = StAXEventStreamProcessor.forChangeHistory(batchSize);
        houseType = StAXEventStreamProcessor.forHouseType(batchSize);
        house = StAXEventStreamProcessor.forHouse(batchSize);
        munHierarchy = StAXEventStreamProcessor.forMunHierarchy(batchSize);
        normativeDocType = StAXEventStreamProcessor.forNormativeDocType(batchSize);
        normativeDocKind = StAXEventStreamProcessor.forNormativeDocKind(batchSize);
        normativeDoc = StAXEventStreamProcessor.forNormativeDoc(batchSize);
        objectLevel = StAXEventStreamProcessor.forObjectLevel(batchSize);
        paramType = StAXEventStreamProcessor.forParamType(batchSize);
        addrObjParam = StAXEventStreamProcessor.forAddrObjParam(batchSize);
        housesParam = StAXEventStreamProcessor.forHousesParam(batchSize);
        apartmentsParam = StAXEventStreamProcessor.forApartmentsParam(batchSize);
        roomsParam = StAXEventStreamProcessor.forRoomsParam(batchSize);
        steadsParam = StAXEventStreamProcessor.forSteadsParam(batchSize);
        carPlacesParam = StAXEventStreamProcessor.forCarPlacesParam(batchSize);
        reestrObject = StAXEventStreamProcessor.forReestrObject(batchSize);
        roomType = StAXEventStreamProcessor.forRoomType(batchSize);
        room = StAXEventStreamProcessor.forRoom(batchSize);
        stead = StAXEventStreamProcessor.forStead(batchSize);
        addHouseType = StAXEventStreamProcessor.forAddHouseType(batchSize);
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
