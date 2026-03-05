package ru.gt2.gar.parse.xml.stax;

import ru.gt2.gar.domain.GarRecord;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.parse.xml.ListConsumer;
import ru.gt2.gar.parse.xml.XMLStreamProcessor;

import java.io.InputStream;

import static java.util.Objects.requireNonNull;

// В отличие от XMLStreamParser'а этой штуке нужна «труба» куда она добавлять свои данные.
public class StAXStreamProcessor implements XMLStreamProcessor {
    private final StAXAttrMapper mapper;
    private final int batchSize;
    private final JacksonAttrConverter<? extends GarRecord> jacksonAttrConverter;

    public static XMLStreamProcessor forAddressObject(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.ADDRESS_OBJECT, batchSize);
    }

    public static XMLStreamProcessor forAddressObjectDivision(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.ADDRESS_OBJECT_DIVISION, batchSize);
    }

    public static XMLStreamProcessor forAddressObjectType(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.ADDRESS_OBJECT_TYPE, batchSize);
    }

    public static XMLStreamProcessor forAdmHierarchy(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.ADM_HIERARCHY, batchSize);
    }

    public static XMLStreamProcessor forApartmentType(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.APARTMENT_TYPE, batchSize);
    }

    public static XMLStreamProcessor forApartment(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.APARTMENT, batchSize);
    }

    public static XMLStreamProcessor forOperationType(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.OPERATION_TYPE, batchSize);
    }

    public static XMLStreamProcessor forCarPlace(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.CAR_PLACE, batchSize);
    }

    public static XMLStreamProcessor forChangeHistory(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.CHANGE_HISTORY, batchSize);
    }

    public static XMLStreamProcessor forHouseType(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.HOUSE_TYPE, batchSize);
    }

    public static XMLStreamProcessor forHouse(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.HOUSE, batchSize);
    }

    public static XMLStreamProcessor forMunHierarchy(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.MUN_HIERARCHY, batchSize);
    }

    public static XMLStreamProcessor forNormativeDoc(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.NORMATIVE_DOC, batchSize);
    }

    public static XMLStreamProcessor forNormativeDocKind(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.NORMATIVE_DOC_KIND, batchSize);
    }

    public static XMLStreamProcessor forNormativeDocType(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.NORMATIVE_DOC_TYPE, batchSize);
    }

    public static XMLStreamProcessor forObjectLevel(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.OBJECT_LEVEL, batchSize);
    }

    public static XMLStreamProcessor forParamType(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.PARAM_TYPE, batchSize);
    }

    public static XMLStreamProcessor forAddrObjParam(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.ADDR_OBJ_PARAM, batchSize);
    }

    public static XMLStreamProcessor forHousesParam(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.HOUSES_PARAM, batchSize);
    }

    public static XMLStreamProcessor forApartmentsParam(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.APARTMENTS_PARAM, batchSize);
    }

    public static XMLStreamProcessor forRoomsParam(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.ROOMS_PARAM, batchSize);
    }

    public static XMLStreamProcessor forSteadsParam(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.STEADS_PARAM, batchSize);
    }

    public static XMLStreamProcessor forCarPlacesParam(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.CARPLACES_PARAM, batchSize);
    }

    public static XMLStreamProcessor forReestrObject(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.REESTR_OBJECT, batchSize);
    }

    public static XMLStreamProcessor forRoomType(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.ROOM_TYPE, batchSize);
    }

    public static XMLStreamProcessor forRoom(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.ROOM, batchSize);
    }

    public static XMLStreamProcessor forStead(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.STEAD, batchSize);
    }

    public static XMLStreamProcessor forAddHouseType(int batchSize) {
        return new StAXStreamProcessor(StAXAttrMapper.ADD_HOUSE_TYPE, batchSize);
    }

    private StAXStreamProcessor(StAXAttrMapper mapper, int batchSize) {
        this.mapper = mapper;
        this.batchSize = batchSize;
        // TODO Вот тут можно вместо конвертора от JackSon'а применить к примеру JAXB, если получится.
        // Ещё вариант — просто взять MapStruct, он вполне себе так умеет!
        jacksonAttrConverter = JacksonAttrConverter.jackson(mapper.garType.recordClass);
    }

    /*
    public static XMLStreamProcessor createFor(GarType garType, int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.forGarType(garType), batchSize);
    }*/

    public GarType getGarType() {
        return mapper.garType;
    }

    @Override public void process(InputStream inputStream, ListConsumer dataConsumer, int entitySizeLimit) throws Exception {
        requireNonNull(inputStream);
        requireNonNull(dataConsumer);
        dataConsumer.before();
        try (StAXAttrReader reader = new StAXAttrReader(inputStream, mapper, jacksonAttrConverter, batchSize, entitySizeLimit)) {
            while(reader.hasNext()) {
                dataConsumer.accept(reader.next());
            }
        }
        dataConsumer.after();
    }
}
