package ru.gt2.gar.parse.xml;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.gt2.gar.parse.domain.AddressObject;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Slf4j
public class XmlStreamParser {
    public static final String XML_ELEMENT = "OBJECT";
    private final int recordsPerBatch;

    public XmlStreamParser(
            @Value("${gar.parse.batch:1000}") int recordsPerBatch) {
        this.recordsPerBatch = recordsPerBatch;
    }

    public void parse(InputStream inputStream) throws IOException, XMLStreamException {
        try (XMLAttributeReader<AddressObject> reader =
                     new XMLAttributeReader<>(inputStream, XML_ELEMENT, recordsPerBatch, AddressObject.class)) {
            while(reader.hasNext()) {
                List<AddressObject> batch = reader.next();
                log.info("Number of records read: {}", batch.size());
                // TODO по хорошему потом нужно их куда-то передавать ещё, но пока так.
            }
        }
    }
}
