package ru.gt2.gar.parse.xml.stax2;

import com.ctc.wstx.sr.TypedStreamReader;
import com.ctc.wstx.stax.WstxInputFactory;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.evt.XMLEvent2;
import ru.gt2.gar.domain.GarRecord;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.parse.xml.AbstractRecordListIterator;

import javax.xml.stream.XMLStreamException;
import java.io.InputStream;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Slf4j
public class StAX2StreamReaderIterator extends AbstractRecordListIterator {
    private final TypedStreamReader xsr;
    private final TypedAttrReader tar;
    private final GarRecordCreator<?> mapper;

    private boolean expectOuter = true;

    public StAX2StreamReaderIterator(GarType garType, int batchSize, InputStream inputStream,
                                     GarRecordCreator<?> mapper) throws XMLStreamException {
        super(garType, batchSize);
        this.mapper = requireNonNull(mapper);

        XMLInputFactory2 xmlInputFactory = new WstxInputFactory();
        xsr = (TypedStreamReader) xmlInputFactory.createXMLStreamReader(inputStream);
        tar = new TypedAttrReader(xsr);
    }

    @Override
    public boolean hasNext() {
        return xsr.hasNext();
    }

    @Override
    protected Optional<GarRecord> createValue() throws XMLStreamException {
        int eventCode = xsr.next();
        switch (eventCode) {
            case XMLEvent2.START_ELEMENT: {
                String startElementName = xsr.getName().getLocalPart();
                if (expectOuter) {
                    if (startElementName.equals(rootName)) {
                        expectOuter = false;
                        return Optional.empty();
                    } else {
                        throw new IllegalArgumentException("Unexpected outer tag: " + startElementName);
                    }
                } else {
                    if (!startElementName.equals(elementName)) {
                        throw new IllegalArgumentException("Unexpected tag: " + startElementName);
                    }
                }
                return Optional.of(mapper.create(tar));
            }
            case XMLEvent2.END_ELEMENT: {
                // Outer tag is finished if xsr.getName().getLocalPart().equals(rootName)
                break;
            }
            case XMLEvent2.END_DOCUMENT: {
                // Document is finished
                break;
            }
            case XMLEvent2.CHARACTERS: {
                // Nothing
                break;
            }
            default: {
                // Unexpected event
            }
        }

        return Optional.empty();
    }

    @Override
    public void close() {
        try {
            xsr.close();
        } catch (XMLStreamException e) {
            log.warn("Unable to close eventReader", e);
        }

    }
}
