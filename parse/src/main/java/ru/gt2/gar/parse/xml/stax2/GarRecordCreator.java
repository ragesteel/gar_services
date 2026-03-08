package ru.gt2.gar.parse.xml.stax2;

import ru.gt2.gar.domain.GarRecord;

import javax.xml.stream.XMLStreamException;

@FunctionalInterface
public interface GarRecordCreator<T extends GarRecord> {
    T create(TypedAttrReader tar) throws XMLStreamException;
}
