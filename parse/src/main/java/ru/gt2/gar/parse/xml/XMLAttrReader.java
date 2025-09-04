package ru.gt2.gar.parse.xml;

import com.google.common.collect.Streams;
import lombok.extern.slf4j.Slf4j;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.Closeable;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * Преобразование XML в record.
 * <a href="https://habr.com/ru/articles/724324/">Источник вдохновения</a>
 * TODO вынести обработчик исключений во время работы в отдельную лямбду, чтобы не кидать RuntimeException
 * TODO вынести наружу mapper, а в идеале так вообще его самостоятельно генерировать
 * IDEA а ещё — по хорошему вообще вынести логику сохранения в лист наружу, чтобы тут только парсинг XML остался.
 *      это ведь штука, которая просто по-сути реализует метод map в терминах map/reduce.
 */
@Slf4j
public class XMLAttrReader<T> implements Iterator<List<T>>, Closeable {

    private final XMLEventReader eventReader;
    private final String rootName;
    private final String elementName;
    private final BiFunction<String, String, String> valueProcessing;
    private final int batchSize;
    private final AttrConverter<T> attrConverter;
    private boolean expectOuter = true;

    public XMLAttrReader(InputStream inputStream, XMLAttrMapper<T> mapper, AttrConverter<T> attrConverter, int batchSize)
            throws XMLStreamException {
        requireNonNull(mapper, "mapper must not be null");
        rootName = mapper.rootName;
        elementName = mapper.elementName;
        valueProcessing = mapper.valueProcessing;

        this.attrConverter = requireNonNull(attrConverter, "converter must not be null");

        if (batchSize <= 0) {
            throw new IllegalArgumentException("batchSize must be > 0");
        }
        this.batchSize = batchSize;

        requireNonNull(inputStream, "inputStream must not be null");
        XMLInputFactory factory = XMLInputFactory.newInstance(); // Не ясно, нужно-ли постоянно новую создавать?…
        eventReader = factory.createXMLEventReader(inputStream);
    }

    /**
     * Проверяет, есть ли ещё элементы для чтения.
     */
    @Override
    public boolean hasNext() {
        return eventReader.hasNext();
    }

    /**
     * Читает следующую "пачку" объектов, используя атрибуты элемента как поля.
     */
    @Override
    public List<T> next() {
        try {
            List<T> result = new ArrayList<>(batchSize);
            while (eventReader.hasNext() && (result.size() < batchSize)) {
                XMLEvent event = eventReader.nextEvent();
                if (!event.isStartElement()) {
                    continue;
                }
                StartElement startElement = event.asStartElement();
                String startElementName = startElement.getName().getLocalPart();
                if (expectOuter && (startElementName.equalsIgnoreCase(rootName))) {
                    expectOuter = false;
                    continue;
                } else if (!startElementName.equalsIgnoreCase(elementName)) {
                    log.warn("Unexpected element: {}", startElement);
                    continue;
                }
                result.add(createValue(startElement));
            }
            return result;
        } catch (XMLStreamException e) {
            log.error("Error while reading XML: {}", e.getMessage(), e);
            // В зависимости от требований, можно либо бросить Unchecked-исключение, либо вернуть то, что накопилось
            throw new RuntimeException("Failed to read next batch from XML", e);
        }
    }

    private T createValue(StartElement startElement) {
        Map<String, String> attributes = Streams.stream(startElement.getAttributes())
                .collect(Collectors.toMap(XMLAttrReader::getAttrName, this::getAttrValue));
        return attrConverter.apply(attributes);
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
