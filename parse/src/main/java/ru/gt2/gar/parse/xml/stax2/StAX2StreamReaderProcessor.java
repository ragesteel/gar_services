package ru.gt2.gar.parse.xml.stax2;

import lombok.RequiredArgsConstructor;
import org.codehaus.stax2.XMLInputFactory2;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.parse.xml.ListConsumer;
import ru.gt2.gar.parse.xml.XMLStreamProcessor;

import java.io.InputStream;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
public class StAX2StreamReaderProcessor implements XMLStreamProcessor {
    private final GarType garType;
    private final XMLInputFactory2 xmlInputFactory;
    private final int batchSize;
    private final GarRecordCreator<?> mapper;

    @Override
    public void process(InputStream inputStream, ListConsumer dataConsumer, int entitySizeLimit) throws Exception {
        requireNonNull(inputStream);
        requireNonNull(dataConsumer);
        dataConsumer.before();
        try (StAX2StreamReaderIterator reader = new StAX2StreamReaderIterator(garType, xmlInputFactory, batchSize,
                inputStream, mapper)) {
            while(reader.hasNext()) {
                dataConsumer.accept(reader.next());
            }
        }
        dataConsumer.after();
    }

    @Override
    public GarType getGarType() {
        return garType;
    }
}
