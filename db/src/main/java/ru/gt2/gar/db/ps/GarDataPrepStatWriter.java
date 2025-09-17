package ru.gt2.gar.db.ps;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gt2.gar.db.GarDataWriter;
import ru.gt2.gar.db.schema.DatabaseSchema;
import ru.gt2.gar.domain.GarType;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

// Запись в базу с помощью PreparedStatement
@RequiredArgsConstructor
@Component
public class GarDataPrepStatWriter implements GarDataWriter {
    private final DatabaseSchema schema;
    private final DataSource dataSource;

    @Override
    public void writeEntities(GarType garType, List<? extends Record> entities) {
        if (entities.isEmpty()) {
            return;
        }

        // TODO сделать кэширование insertData в ConcurrentHashMap.
        InsertData insertData = new InsertGenerator(garType, schema).generate();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertData.insertSQL())) {
            for (Record entity : entities) {
                addEntityParams(preparedStatement, entity, insertData.accessors());
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

    private void addEntityParams(PreparedStatement preparedStatement, Record entity, ImmutableList<Method> accessors) {
        for (int i = 0; i < accessors.size(); i++) {
            Method method = accessors.get(i);
            try {
                preparedStatement.setObject(i + 1, method.invoke(entity));
            } catch (Exception e) {
                throw new RuntimeException("Unable to get value via accessor " + method, e);
            }
        }
    }
}
