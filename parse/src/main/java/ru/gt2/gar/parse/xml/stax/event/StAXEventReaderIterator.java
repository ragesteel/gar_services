package ru.gt2.gar.parse.xml.stax.event;

import com.google.common.collect.Streams;
import lombok.extern.slf4j.Slf4j;
import ru.gt2.gar.domain.GarRecord;
import ru.gt2.gar.parse.xml.AbstractRecordListIterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * Преобразование XML в record.
 * <a href="https://habr.com/ru/articles/724324/">Источник вдохновения</a>
 * TODO вынести обработчик исключений во время работы в отдельную лямбду, чтобы не кидать RuntimeException
 * IDEA а ещё — по хорошему вообще вынести логику сохранения в лист наружу, чтобы тут только парсинг XML остался.
 *      это ведь штука, которая просто по-сути реализует метод map в терминах map/reduce.
 */
@Slf4j
public class StAXEventReaderIterator extends AbstractRecordListIterator {

    private final XMLEventReader eventReader;
    private final BiFunction<String, String, String> valueProcessing;
    private final JacksonAttrConverter<? extends GarRecord> jacksonAttrConverter;
    private boolean expectOuter = true;

    public StAXEventReaderIterator(InputStream inputStream, StAXAttrMapperData mapper,
                                   JacksonAttrConverter<? extends GarRecord> jacksonAttrConverter, int batchSize, int entitySizeLimit)
                throws XMLStreamException {
        super(mapper.garType, batchSize);
        requireNonNull(mapper, "mapper must not be null");
        valueProcessing = mapper.valueProcessing;

        this.jacksonAttrConverter = requireNonNull(jacksonAttrConverter, "converter must not be null");

        requireNonNull(inputStream, "inputStream must not be null");
        // Не ясно, нужно-ли постоянно новую создавать?…
        XMLInputFactory factory = XMLInputFactory.newDefaultFactory();

        if (entitySizeLimit >= 0) {
            // Да, соответствующие константы есть в JdkConstants, но этот класс не экспортируется!
            factory.setProperty("jdk.xml.maxGeneralEntitySizeLimit", entitySizeLimit);
            factory.setProperty("jdk.xml.totalEntitySizeLimit", entitySizeLimit);
        }
        eventReader = factory.createXMLEventReader(inputStream);
    }

    /**
     * Проверяет, есть ли ещё элементы для чтения.
     */
    @Override
    public boolean hasNext() {
        return eventReader.hasNext();
    }

    @Override
    protected Optional<GarRecord> createValue() throws XMLStreamException {
        XMLEvent event = eventReader.nextEvent();
        if (!event.isStartElement()) {
            return Optional.empty();
        }
        StartElement startElement = event.asStartElement();
        String startElementName = startElement.getName().getLocalPart();
        if (expectOuter && (startElementName.equalsIgnoreCase(rootName))) {
            expectOuter = false;
            return Optional.empty();
        } else if (!startElementName.equalsIgnoreCase(elementName)) {
            log.warn("Unexpected element: {}", startElement);
            return Optional.empty();
        }
        Map<String, String> attributes = Streams.stream(startElement.getAttributes())
                .collect(Collectors.toMap(StAXEventReaderIterator::getAttrName, this::getAttrValue));
        return Optional.of(jacksonAttrConverter.apply(attributes));
    }

    private static String getAttrName(Attribute a) {
        return a.getName().getLocalPart();
    }

    private String getAttrValue(Attribute a) {
        return valueProcessing.apply(getAttrName(a), a.getValue());
    }

    /**
     * Закрывает XMLEventReader.
     */
    @Override
    public void close() {
        try {
            eventReader.close();
        } catch (XMLStreamException e) {
            log.warn("Unable to close eventReader", e);
        }
    }
}
