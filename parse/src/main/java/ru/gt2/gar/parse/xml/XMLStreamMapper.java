package ru.gt2.gar.parse.xml;

import ru.gt2.gar.parse.domain.AddressObject;
import ru.gt2.gar.parse.domain.AddressObjectDivision;

import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

// В отличие от XMLStreamParser'а этой штуке нужна «труба» куда она добавлять свои данные.
public class XMLStreamMapper<T> {
    public static final int BATCH_SIZE = 1000;

    private final XMLAttrMapper<T> mapper;
    private final Converter<T> converter;

    public static XMLStreamMapper<AddressObject> forAddressObject() {
        return new XMLStreamMapper<>(XMLAttrMapper.ADDRESS_OBJECT);
    }

    public static XMLStreamMapper<AddressObjectDivision> forAddressObjectDivision() {
        return new XMLStreamMapper<>(XMLAttrMapper.ADDRESS_OBJECT_DIVISION);
    }

    private XMLStreamMapper(XMLAttrMapper<T> mapper) {
        this.mapper = mapper;
        converter = Converter.jackson(mapper.valueClass);
    }

    public void process(InputStream inputStream, Consumer<List<T>> dataConsumer) throws Exception {
        try (XMLAttrReader<T> reader =
                     new XMLAttrReader<>(inputStream, mapper, converter, BATCH_SIZE)) {
            while(reader.hasNext()) {
                dataConsumer.accept(reader.next());
            }
        }
    }
}
