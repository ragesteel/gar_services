package ru.gt2.gar.parse.xml.stax2;

import com.google.common.annotations.VisibleForTesting;
import org.codehaus.stax2.XMLInputFactory2;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.parse.xml.XMLProcessors;
import ru.gt2.gar.parse.xml.XMLStreamProcessor;

import java.util.Map;

public class StAX2Processors implements XMLProcessors {
    private final Map<GarType, StAX2StreamReaderProcessor> processors;

    public StAX2Processors(int batchSize, XMLInputFactory2 xmlInputFactory2) {
        processors = GeneratedRecordCreators.createProcessors(xmlInputFactory2, batchSize);
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
