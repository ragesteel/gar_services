package ru.gt2.gar.parse.xml;

import ru.gt2.gar.parse.domain.AddressObject;
import ru.gt2.gar.parse.domain.AddressObjectDivision;
import ru.gt2.gar.parse.domain.AddressObjectType;
import ru.gt2.gar.parse.domain.AdmHierarchy;
import ru.gt2.gar.parse.domain.Apartment;
import ru.gt2.gar.parse.domain.ApartmentType;
import ru.gt2.gar.parse.domain.OperationType;
import ru.gt2.gar.parse.domain.GarType;
import ru.gt2.gar.parse.domain.CarPlace;
import ru.gt2.gar.parse.domain.ChangeHistory;
import ru.gt2.gar.parse.domain.HouseType;
import ru.gt2.gar.parse.domain.House;
import ru.gt2.gar.parse.domain.MunHierarchy;
import ru.gt2.gar.parse.domain.NormativeDocType;
import ru.gt2.gar.parse.domain.NormativeDocKind;
import ru.gt2.gar.parse.domain.NormativeDoc;

import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

// В отличие от XMLStreamParser'а этой штуке нужна «труба» куда она добавлять свои данные.
public class XMLStreamProcessor<T> {
    private final XMLAttrMapper<T> mapper;
    private final int batchSize;
    private final AttrConverter<T> attrConverter;

    public static XMLStreamProcessor<AddressObject> forAddressObject(int batchSize) {
        return new XMLStreamProcessor<>(XMLAttrMapper.ADDRESS_OBJECT, batchSize);
    }

    public static XMLStreamProcessor<AddressObjectDivision> forAddressObjectDivision(int batchSize) {
        return new XMLStreamProcessor<>(XMLAttrMapper.ADDRESS_OBJECT_DIVISION, batchSize);
    }

    public static XMLStreamProcessor<AddressObjectType> forAddressObjectType(int batchSize) {
        return new XMLStreamProcessor<>(XMLAttrMapper.ADDRESS_OBJECT_TYPE, batchSize);
    }

    public static XMLStreamProcessor<AdmHierarchy> forAdmHierarchy(int batchSize) {
        return new XMLStreamProcessor<>(XMLAttrMapper.ADM_HIERARCHY, batchSize);
    }

    public static XMLStreamProcessor<ApartmentType> forApartmentType(int batchSize) {
        return new XMLStreamProcessor<>(XMLAttrMapper.APARTMENT_TYPE, batchSize);
    }

    public static XMLStreamProcessor<Apartment> forApartment(int batchSize) {
        return new XMLStreamProcessor<>(XMLAttrMapper.APARTMENT, batchSize);
    }

    public static XMLStreamProcessor<OperationType> forOperationType(int batchSize) {
        return new XMLStreamProcessor<>(XMLAttrMapper.OPERATION_TYPE, batchSize);
    }

    public static XMLStreamProcessor<CarPlace> forCarPlace(int batchSize) {
        return new XMLStreamProcessor<>(XMLAttrMapper.CAR_PLACE, batchSize);
    }

    public static XMLStreamProcessor<ChangeHistory> forChangeHistory(int batchSize) {
        return new XMLStreamProcessor<>(XMLAttrMapper.CHANGE_HISTORY, batchSize);
    }

    public static XMLStreamProcessor<HouseType> forHouseType(int batchSize) {
        return new XMLStreamProcessor<>(XMLAttrMapper.HOUSE_TYPE, batchSize);
    }

    public static XMLStreamProcessor<House> forHouse(int batchSize) {
        return new XMLStreamProcessor<>(XMLAttrMapper.HOUSE, batchSize);
    }

    public static XMLStreamProcessor<MunHierarchy> forMunHierarchy(int batchSize) {
        return new XMLStreamProcessor<>(XMLAttrMapper.MUN_HIERARCHY, batchSize);
    }

    public static XMLStreamProcessor<NormativeDoc> forNormativeDoc(int batchSize) {
        return new XMLStreamProcessor<>(XMLAttrMapper.NORMATIVE_DOC, batchSize);
    }

    public static XMLStreamProcessor<NormativeDocKind> forNormativeDocKind(int batchSize) {
        return new XMLStreamProcessor<>(XMLAttrMapper.NORMATIVE_DOC_KIND, batchSize);
    }

    public static XMLStreamProcessor<NormativeDocType> forNormativeDocType(int batchSize) {
        return new XMLStreamProcessor<>(XMLAttrMapper.NORMATIVE_DOC_TYPE, batchSize);
    }

    private XMLStreamProcessor(XMLAttrMapper<T> mapper, int batchSize) {
        this.mapper = mapper;
        this.batchSize = batchSize;
        attrConverter = AttrConverter.jackson(mapper.valueClass);
    }

    public GarType getGarType() {
        return mapper.garType;
    }

    public void process(InputStream inputStream, Consumer<List<T>> dataConsumer) throws Exception {
        requireNonNull(inputStream);
        requireNonNull(dataConsumer);
        try (XMLAttrReader<T> reader = new XMLAttrReader<>(inputStream, mapper, attrConverter, batchSize)) {
            while(reader.hasNext()) {
                dataConsumer.accept(reader.next());
            }
        }
    }
}
