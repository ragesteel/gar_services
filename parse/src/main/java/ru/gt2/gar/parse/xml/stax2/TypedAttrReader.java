package ru.gt2.gar.parse.xml.stax2;

import com.ctc.wstx.sr.TypedStreamReader;
import lombok.RequiredArgsConstructor;

import javax.xml.stream.XMLStreamException;
import java.time.LocalDate;
import java.util.UUID;

/// Вспомогательный класс для чтения типизированных атрибутов
/// Не является потокобезопасным, нужно создавать новый экземпляр для каждого XML-потока
@RequiredArgsConstructor
public class TypedAttrReader {
    private final TypedStreamReader typedStreamReader;
    private final LocalDateValueDecoder localDateTimeDecoder = new LocalDateValueDecoder();
    private final IntAsBooleanDecoder intAsBooleanDecoder = new IntAsBooleanDecoder();

    public int getInt(String name) throws XMLStreamException {
        int attributeIndex = typedStreamReader.getAttributeIndex(null, name);
        return typedStreamReader.getAttributeAsInt(attributeIndex);
    }

    public Integer getNullableInt(String name) throws XMLStreamException {
        int attributeIndex = typedStreamReader.getAttributeIndex(null, name);
        if (attributeIndex < 0) {
            return null;
        }
        return typedStreamReader.getAttributeAsInt(attributeIndex);
    }

    public String getString(String name) {
        int attributeIndex = typedStreamReader.getAttributeIndex(null, name);
        return typedStreamReader.getAttributeValue(attributeIndex);
    }

    public String getNullableString(String name) {
        int attributeIndex = typedStreamReader.getAttributeIndex(null, name);
        if (attributeIndex < 0) {
            return null;
        }
        return typedStreamReader.getAttributeValue(attributeIndex);
    }

    public LocalDate getLocalDate(String name) throws XMLStreamException {
        int attributeIndex = typedStreamReader.getAttributeIndex(null, name);
        typedStreamReader.getAttributeAs(attributeIndex, localDateTimeDecoder);
        return localDateTimeDecoder.value;
    }

    public LocalDate getNullableLocalDate(String name) throws XMLStreamException {
        int attributeIndex = typedStreamReader.getAttributeIndex(null, name);
        if (attributeIndex < 0) {
            return null;
        }
        typedStreamReader.getAttributeAs(attributeIndex, localDateTimeDecoder);
        return localDateTimeDecoder.value;
    }

    public boolean getBoolean(String name) throws XMLStreamException {
        int attributeIndex = typedStreamReader.getAttributeIndex(null, name);
        return typedStreamReader.getAttributeAsBoolean(attributeIndex);
    }

    public long getLong(String name) throws XMLStreamException {
        int attributeIndex = typedStreamReader.getAttributeIndex(null, name);
        return typedStreamReader.getAttributeAsLong(attributeIndex);
    }

    public Long getNullableLong(String name) throws XMLStreamException {
        int attributeIndex = typedStreamReader.getAttributeIndex(null, name);
        if (attributeIndex < 0) {
            return null;
        }
        return typedStreamReader.getAttributeAsLong(attributeIndex);
    }

    public UUID getUUID(String name) throws XMLStreamException {
        // TODO Прямое преобразование из CharSequence для скорости
        return UUID.fromString(getString(name));
    }

    public boolean getIntAsBoolean(String name) throws XMLStreamException {
        int attributeIndex = typedStreamReader.getAttributeIndex(null, name);
        typedStreamReader.getAttributeAs(attributeIndex, intAsBooleanDecoder);
        return intAsBooleanDecoder.value;
    }
}
