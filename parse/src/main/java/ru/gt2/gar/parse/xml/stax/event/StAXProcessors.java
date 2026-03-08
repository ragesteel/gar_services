package ru.gt2.gar.parse.xml.stax.event;

import com.google.common.annotations.VisibleForTesting;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.parse.xml.XMLProcessors;
import ru.gt2.gar.parse.xml.XMLStreamProcessor;

import java.util.Map;

public class StAXProcessors implements XMLProcessors {
    private final Map<GarType, XMLStreamProcessor> processors;

    public StAXProcessors(int batchSize) {
        processors = StAXEventStreamProcessor.createProcessors(batchSize);
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
