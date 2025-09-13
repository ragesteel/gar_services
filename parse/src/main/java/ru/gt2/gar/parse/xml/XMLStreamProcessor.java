package ru.gt2.gar.parse.xml;

import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.parse.consumer.ListConsumer;

import java.io.InputStream;

import static java.util.Objects.requireNonNull;

// В отличие от XMLStreamParser'а этой штуке нужна «труба» куда она добавлять свои данные.
public class XMLStreamProcessor {
    private final XMLAttrMapper<? extends Record> mapper;
    private final int batchSize;
    private final AttrConverter<? extends Record> attrConverter;

    public static XMLStreamProcessor forAddressObject(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.ADDRESS_OBJECT, batchSize);
    }

    public static XMLStreamProcessor forAddressObjectDivision(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.ADDRESS_OBJECT_DIVISION, batchSize);
    }

    public static XMLStreamProcessor forAddressObjectType(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.ADDRESS_OBJECT_TYPE, batchSize);
    }

    public static XMLStreamProcessor forAdmHierarchy(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.ADM_HIERARCHY, batchSize);
    }

    public static XMLStreamProcessor forApartmentType(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.APARTMENT_TYPE, batchSize);
    }

    public static XMLStreamProcessor forApartment(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.APARTMENT, batchSize);
    }

    public static XMLStreamProcessor forOperationType(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.OPERATION_TYPE, batchSize);
    }

    public static XMLStreamProcessor forCarPlace(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.CAR_PLACE, batchSize);
    }

    public static XMLStreamProcessor forChangeHistory(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.CHANGE_HISTORY, batchSize);
    }

    public static XMLStreamProcessor forHouseType(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.HOUSE_TYPE, batchSize);
    }

    public static XMLStreamProcessor forHouse(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.HOUSE, batchSize);
    }

    public static XMLStreamProcessor forMunHierarchy(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.MUN_HIERARCHY, batchSize);
    }

    public static XMLStreamProcessor forNormativeDoc(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.NORMATIVE_DOC, batchSize);
    }

    public static XMLStreamProcessor forNormativeDocKind(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.NORMATIVE_DOC_KIND, batchSize);
    }

    public static XMLStreamProcessor forNormativeDocType(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.NORMATIVE_DOC_TYPE, batchSize);
    }

    public static XMLStreamProcessor forObjectLevel(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.OBJECT_LEVEL, batchSize);
    }

    public static XMLStreamProcessor forParamType(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.PARAM_TYPE, batchSize);
    }

    public static XMLStreamProcessor forAddrObjParam(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.ADDR_OBJ_PARAM, batchSize);
    }

    public static XMLStreamProcessor forHousesParam(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.HOUSES_PARAM, batchSize);
    }

    public static XMLStreamProcessor forApartmentsParam(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.APARTMENTS_PARAM, batchSize);
    }

    public static XMLStreamProcessor forRoomsParam(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.ROOMS_PARAM, batchSize);
    }

    public static XMLStreamProcessor forSteadsParam(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.STEADS_PARAM, batchSize);
    }

    public static XMLStreamProcessor forCarPlacesParam(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.CARPLACES_PARAM, batchSize);
    }

    public static XMLStreamProcessor forReestrObject(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.REESTR_OBJECT, batchSize);
    }

    public static XMLStreamProcessor forRoomType(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.ROOM_TYPE, batchSize);
    }

    public static XMLStreamProcessor forRoom(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.ROOM, batchSize);
    }

    public static XMLStreamProcessor forStead(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.STEAD, batchSize);
    }

    public static XMLStreamProcessor forAddHouseType(int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.ADD_HOUSE_TYPE, batchSize);
    }

    private XMLStreamProcessor(XMLAttrMapper<? extends Record> mapper, int batchSize) {
        this.mapper = mapper;
        this.batchSize = batchSize;
        attrConverter = AttrConverter.jackson(mapper.valueClass);
    }

    /*
    public static XMLStreamProcessor createFor(GarType garType, int batchSize) {
        return new XMLStreamProcessor(XMLAttrMapper.forGarType(garType), batchSize);
    }*/

    public GarType getGarType() {
        return mapper.garType;
    }

    public void process(InputStream inputStream, ListConsumer dataConsumer) throws Exception {
        requireNonNull(inputStream);
        requireNonNull(dataConsumer);
        dataConsumer.before();
        try (XMLAttrReader reader = new XMLAttrReader(inputStream, mapper, attrConverter, batchSize)) {
            while(reader.hasNext()) {
                dataConsumer.accept(reader.next());
            }
        }
        dataConsumer.after();
    }
}
