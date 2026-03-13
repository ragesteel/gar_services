package ru.gt2.gar.parse.xml;

import ru.gt2.gar.domain.GarType;

import java.io.InputStream;

public interface XMLStreamProcessor {
    void process(InputStream inputStream, ListConsumer dataConsumer, int entitySizeLimit) throws Exception;

    GarType getGarType();
}
