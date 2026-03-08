package ru.gt2.gar.parse.xml.stax.event;

import ru.gt2.gar.domain.GarRecord;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.parse.xml.ListConsumer;
import ru.gt2.gar.parse.xml.XMLStreamProcessor;

import java.io.InputStream;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

// В отличие от XMLStreamParser'а этой штуке нужна «труба» куда она добавлять свои данные.
public class StAXEventStreamProcessor implements XMLStreamProcessor {
    private final StAXAttrMapperData mapper;
    private final int batchSize;
    private final JacksonAttrConverter<? extends GarRecord> jacksonAttrConverter;

    public static Map<GarType, XMLStreamProcessor> createProcessors(int batchSize) {
        return Stream.of(GarType.values())
                .map(gt -> new StAXEventStreamProcessor(StAXAttrMapperData.forType(gt), batchSize))
                .collect(Collectors.toMap(XMLStreamProcessor::getGarType, Function.identity()));
    }

    private StAXEventStreamProcessor(StAXAttrMapperData mapper, int batchSize) {
        this.mapper = mapper;
        this.batchSize = batchSize;
        // TODO Вот тут можно вместо конвертора от JackSon'а применить к примеру JAXB, если получится.
        // Ещё вариант — просто взять MapStruct, он вполне себе так умеет!
        jacksonAttrConverter = JacksonAttrConverter.jackson(mapper.garType.recordClass);
    }

    public GarType getGarType() {
        return mapper.garType;
    }

    // TODO Выделить в AbstractXMLStreamProcessor
    @Override
    public void process(InputStream inputStream, ListConsumer dataConsumer, int entitySizeLimit) throws Exception {
        requireNonNull(inputStream);
        requireNonNull(dataConsumer);
        dataConsumer.before();
        try (StAXEventReaderIterator reader = new StAXEventReaderIterator(inputStream, mapper, jacksonAttrConverter, batchSize, entitySizeLimit)) {
            while(reader.hasNext()) {
                dataConsumer.accept(reader.next());
            }
        }
        dataConsumer.after();
    }
}
