package ru.gt2.gar.parse.xml.stax2;

import lombok.RequiredArgsConstructor;
import org.codehaus.stax2.XMLStreamReader2;

import javax.xml.stream.XMLStreamException;
import java.time.LocalDate;
import java.util.UUID;

/// Вспомогательный класс для чтения типизированных атрибутов
/// Не является потокобезопасным, нужно создавать новый экземпляр для каждого XML-потока
@RequiredArgsConstructor
public class TypedAttrReader {
    private final XMLStreamReader2 xmlStreamReader2;
    private final LocalDateValueDecoder localDateTimeDecoder = new LocalDateValueDecoder();
    private final IntAsBooleanDecoder intAsBooleanDecoder = new IntAsBooleanDecoder();

    public int getInt(String name) throws XMLStreamException {
        int attributeIndex = xmlStreamReader2.getAttributeIndex(null, name);
        return xmlStreamReader2.getAttributeAsInt(attributeIndex);
    }

    public Integer getNullableInt(String name) throws XMLStreamException {
        int attributeIndex = xmlStreamReader2.getAttributeIndex(null, name);
        if (attributeIndex < 0) {
            return null;
        }
        return xmlStreamReader2.getAttributeAsInt(attributeIndex);
    }

    public String getString(String name) {
        int attributeIndex = xmlStreamReader2.getAttributeIndex(null, name);
        return xmlStreamReader2.getAttributeValue(attributeIndex);
    }

    public String getNullableString(String name) {
        int attributeIndex = xmlStreamReader2.getAttributeIndex(null, name);
        if (attributeIndex < 0) {
            return null;
        }
        return xmlStreamReader2.getAttributeValue(attributeIndex);
    }

    public LocalDate getLocalDate(String name) throws XMLStreamException {
        int attributeIndex = xmlStreamReader2.getAttributeIndex(null, name);
        xmlStreamReader2.getAttributeAs(attributeIndex, localDateTimeDecoder);
        return localDateTimeDecoder.value;
    }

    public LocalDate getNullableLocalDate(String name) throws XMLStreamException {
        int attributeIndex = xmlStreamReader2.getAttributeIndex(null, name);
        if (attributeIndex < 0) {
            return null;
        }
        xmlStreamReader2.getAttributeAs(attributeIndex, localDateTimeDecoder);
        return localDateTimeDecoder.value;
    }

    public boolean getBoolean(String name) throws XMLStreamException {
        int attributeIndex = xmlStreamReader2.getAttributeIndex(null, name);
        return xmlStreamReader2.getAttributeAsBoolean(attributeIndex);
    }

    public long getLong(String name) throws XMLStreamException {
        int attributeIndex = xmlStreamReader2.getAttributeIndex(null, name);
        return xmlStreamReader2.getAttributeAsLong(attributeIndex);
    }

    public Long getNullableLong(String name) throws XMLStreamException {
        int attributeIndex = xmlStreamReader2.getAttributeIndex(null, name);
        if (attributeIndex < 0) {
            return null;
        }
        return xmlStreamReader2.getAttributeAsLong(attributeIndex);
    }

    public UUID getUUID(String name) {
        // TODO Прямое преобразование из CharSequence для скорости
        return UUID.fromString(getString(name));
    }

    public boolean getIntAsBoolean(String name) throws XMLStreamException {
        int attributeIndex = xmlStreamReader2.getAttributeIndex(null, name);
        xmlStreamReader2.getAttributeAs(attributeIndex, intAsBooleanDecoder);
        return intAsBooleanDecoder.value;
    }
}
