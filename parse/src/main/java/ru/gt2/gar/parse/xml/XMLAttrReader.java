package ru.gt2.gar.parse.xml;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Streams;
import lombok.extern.slf4j.Slf4j;
import ru.gt2.gar.parse.domain.ElementName;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
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
    private final String elementName;
    private final int batchSize;
    private final ObjectMapper objectMapper;
    private final Class<T> valueType;

    /**
     * Конструктор и создание файлового reader-а.
     *
     * @param inputStream входной поток с XML
     * @param valueType класс, в который мапятся данные
     * @param batchSize   размер пакета (сколько объектов читать за раз)
     */
    public XMLAttrReader(InputStream inputStream, Class<T> valueType, int batchSize)
            throws XMLStreamException {
        this.valueType = requireNonNull(valueType, "valueType must not be null");
        ElementName annotation = valueType.getAnnotation(ElementName.class);
        elementName = requireNonNull(annotation, "ElementName annotation must be present on class " + valueType).value();

        if (batchSize <= 0) {
            throw new IllegalArgumentException("batchSize must be > 0");
        }
        this.batchSize = batchSize;

        requireNonNull(inputStream, "inputStream must not be null");
        XMLInputFactory factory = XMLInputFactory.newInstance(); // Не ясно, нужно-ли постоянно новую создавать?…
        eventReader = factory.createXMLEventReader(inputStream);
        objectMapper = JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .build();

        objectMapper.registerModule(new JavaTimeModule());
        // или можно objectMapper.findAndRegisterModules() и поставить scope=runtime зависимости jackson-datatype-jsr310
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
        List<T> result = new ArrayList<>(batchSize);
        int count = 0;

        try {
            while (eventReader.hasNext() && (count < batchSize)) {
                // пропуск прочих событий
                XMLEvent event = eventReader.nextEvent();
                if (XMLStreamConstants.START_ELEMENT != event.getEventType()) {
                    continue;
                }
                StartElement startElement = event.asStartElement();
                if (elementName.equalsIgnoreCase(startElement.getName().getLocalPart())) {
                    T obj = createValue(startElement);
                    result.add(obj);
                    count++;
                }
            }
        } catch (XMLStreamException e) {
            log.error("Error while reading XML: {}", e.getMessage(), e);
            // В зависимости от требований, можно либо бросить Unchecked-исключение, либо вернуть то, что накопилось
            throw new RuntimeException("Failed to read next batch from XML", e);
        }

        return result;
    }

    private T createValue(StartElement startElement) {
        Map<String, String> attributes = Streams.stream(startElement.getAttributes())
                .collect(Collectors.toMap(a -> a.getName().getLocalPart(), Attribute::getValue));

        return objectMapper.convertValue(attributes, valueType);
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
