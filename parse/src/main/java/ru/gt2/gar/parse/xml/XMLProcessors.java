package ru.gt2.gar.parse.xml;

import ru.gt2.gar.domain.GarType;

public interface XMLProcessors {
    XMLStreamProcessor get(GarType garType);
}
