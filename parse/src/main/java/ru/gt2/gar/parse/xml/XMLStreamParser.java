package ru.gt2.gar.parse.xml;

import lombok.Getter;
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
public class XMLStreamParser {
    private final int recordsPerBatch;
    @Getter
    private int totalRead;

    public XMLStreamParser(
            @Value("${gar.parse.batch:1000}") int recordsPerBatch) {
        this.recordsPerBatch = recordsPerBatch;
    }

    public void parse(InputStream inputStream) throws IOException, XMLStreamException {
        try (XMLAttrReader<AddressObject> reader =
                     new XMLAttrReader<>(inputStream, XMLAttrMapper.ADDRESS_OBJECT, Converter.jackson(AddressObject.class), recordsPerBatch)) {
            while(reader.hasNext()) {
                List<AddressObject> batch = reader.next();
                totalRead += batch.size();
                // log.info("Number of records read: {}", batch.size());
                // TODO по хорошему потом нужно их куда-то передавать ещё, но пока так.
            }
        }
    }
}
