package ru.gt2.gar.db.ps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gt2.gar.db.GarDataWriter;
import ru.gt2.gar.domain.GarRecord;
import ru.gt2.gar.domain.GarType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

// Запись в базу с помощью PreparedStatement
@RequiredArgsConstructor
@Component
public class GarDataPrepStatWriter implements GarDataWriter {
    private final DataSource dataSource;

    // TODO Сразу передавать Map<? extends Number, ? extends GarRecord>, чтобы не строить этот массив внутри
    @Override
    public void writeEntities(GarType garType, List<? extends GarRecord> entities) {
        if (entities.isEmpty()) {
            return;
        }

        writeRecords(garType, entities);
    }

    private void writeRecords(GarType garType, List<? extends GarRecord> entities) {
        try (Connection connection = dataSource.getConnection()) {
            new GarDataJDBCWriter(garType, connection).write(entities);
        } catch (Exception e) {
            throw new RuntimeException("Unable to write entities", e);
        }
    }
}
