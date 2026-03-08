package ru.gt2.gar.parse.xml.stax.event;

import com.google.common.annotations.VisibleForTesting;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.parse.xml.XMLProcessors;
import ru.gt2.gar.parse.xml.XMLStreamProcessor;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StAXProcessors implements XMLProcessors {
    private final Map<GarType, XMLStreamProcessor> processors;

    public StAXProcessors(int batchSize) {
        processors = Stream.of(
                        StAXEventStreamProcessor.forAddressObject(batchSize),
                        StAXEventStreamProcessor.forAddressObjectDivision(batchSize),
                        StAXEventStreamProcessor.forAddressObjectType(batchSize),
                        StAXEventStreamProcessor.forAdmHierarchy(batchSize),
                        StAXEventStreamProcessor.forApartmentType(batchSize),
                        StAXEventStreamProcessor.forApartment(batchSize),
                        StAXEventStreamProcessor.forOperationType(batchSize),
                        StAXEventStreamProcessor.forCarPlace(batchSize),
                        StAXEventStreamProcessor.forChangeHistory(batchSize),
                        StAXEventStreamProcessor.forHouseType(batchSize),
                        StAXEventStreamProcessor.forHouse(batchSize),
                        StAXEventStreamProcessor.forMunHierarchy(batchSize),
                        StAXEventStreamProcessor.forNormativeDocType(batchSize),
                        StAXEventStreamProcessor.forNormativeDocKind(batchSize),
                        StAXEventStreamProcessor.forNormativeDoc(batchSize),
                        StAXEventStreamProcessor.forObjectLevel(batchSize),
                        StAXEventStreamProcessor.forParamType(batchSize),
                        StAXEventStreamProcessor.forAddrObjParam(batchSize),
                        StAXEventStreamProcessor.forHousesParam(batchSize),
                        StAXEventStreamProcessor.forApartmentsParam(batchSize),
                        StAXEventStreamProcessor.forRoomsParam(batchSize),
                        StAXEventStreamProcessor.forSteadsParam(batchSize),
                        StAXEventStreamProcessor.forCarPlacesParam(batchSize),
                        StAXEventStreamProcessor.forReestrObject(batchSize),
                        StAXEventStreamProcessor.forRoomType(batchSize),
                        StAXEventStreamProcessor.forRoom(batchSize),
                        StAXEventStreamProcessor.forStead(batchSize),
                        StAXEventStreamProcessor.forAddHouseType(batchSize))
                .collect(Collectors.toMap(XMLStreamProcessor::getGarType, Function.identity()));
    }

    @Override
    public XMLStreamProcessor get(GarType garType) {
        return processors.get(garType);
    }

    @VisibleForTesting
    protected int size() {
        return processors.size();
    }
}
