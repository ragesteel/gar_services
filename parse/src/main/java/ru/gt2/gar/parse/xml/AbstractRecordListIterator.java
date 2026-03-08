package ru.gt2.gar.parse.xml;

import lombok.extern.slf4j.Slf4j;
import ru.gt2.gar.domain.GarRecord;
import ru.gt2.gar.domain.GarType;

import javax.xml.stream.XMLStreamException;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AbstractRecordListIterator implements Iterator<List<GarRecord>>, Closeable {
    protected final String rootName;
    protected final String elementName;
    private final int batchSize;

    protected AbstractRecordListIterator(GarType garType, int batchSize) {
        this.rootName = garType.outerTagName;
        this.elementName = garType.elementName;
        if (batchSize <= 0) {
            throw new IllegalArgumentException("batchSize must be > 0");
        }
        this.batchSize = batchSize;
    }

    /**
     * Читает следующую "пачку" объектов, используя атрибуты элемента как поля.
     */
    @Override
    public List<GarRecord> next() {
        try {
            List<GarRecord> result = new ArrayList<>(batchSize);
            while (hasNext() && (result.size() < batchSize)) {
                 createValue().ifPresent(result::add);
            }
            return result;
        } catch (XMLStreamException e) {
            log.error("Error while reading XML: {}", e.getMessage(), e);
            // В зависимости от требований, можно либо бросить Unchecked-исключение, либо вернуть то, что накопилось
            throw new RuntimeException("Failed to read next batch from XML", e);
        }
    }

    protected abstract Optional<GarRecord> createValue() throws XMLStreamException;
}
