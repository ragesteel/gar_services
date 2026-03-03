package ru.gt2.gar.db.ps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gt2.gar.db.GarDataWriter;
import ru.gt2.gar.db.jrm.AddressObjectTypeJm;
import ru.gt2.gar.db.schema.DatabaseSchema;
import ru.gt2.gar.domain.AddressObjectType;
import ru.gt2.gar.domain.GarRecord;
import ru.gt2.gar.domain.GarType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

// Запись в базу с помощью PreparedStatement
@RequiredArgsConstructor
@Component
public class GarDataPrepStatWriter implements GarDataWriter {
    private final DatabaseSchema schema;
    private final DataSource dataSource;
    private final AddressObjectTypeJm addressObjectTypeJm = new AddressObjectTypeJm();

    @Override
    public void writeEntities(GarType garType, List<? extends GarRecord> entities) {
        if (entities.isEmpty()) {
            return;
        }

        // TODO сделать кэширование insertData в ConcurrentHashMap.
        InsertData insertData = new InsertGenerator(garType, schema).generate();



        try (Connection connection = dataSource.getConnection();



            PreparedStatement preparedStatement = connection.prepareStatement(insertData.insertSQL())) {
            for (GarRecord entity : entities) {
                switch (garType) {
                    case ADDR_OBJ_TYPES -> addressObjectTypeJm.write((AddressObjectType) entity, preparedStatement);
                    default -> throw new IllegalStateException("Unexpected value: " + garType);
                }
                preparedStatement.addBatch();
            }

            int[] batch = preparedStatement.executeBatch();
            if (batch.length != entities.size()) {
                throw new RuntimeException(
                        "Not all records were inserted, only " + batch.length + " instead of " + entities.size());
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to write entities", e);
        }
    }
}
