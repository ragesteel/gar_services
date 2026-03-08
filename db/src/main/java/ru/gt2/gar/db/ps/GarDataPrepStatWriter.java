package ru.gt2.gar.db.ps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gt2.gar.db.GarDataWriter;
import ru.gt2.gar.db.jrm.AddressObjectTypeJm;
import ru.gt2.gar.domain.AddressObjectType;
import ru.gt2.gar.domain.GarRecord;
import ru.gt2.gar.domain.GarType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Запись в базу с помощью PreparedStatement
@RequiredArgsConstructor
@Component
public class GarDataPrepStatWriter implements GarDataWriter {
    private final DataSource dataSource;
    private final AddressObjectTypeJm addressObjectTypeJm = new AddressObjectTypeJm();

    // TODO Сразу передавать Map<? extends Number, ? extends GarRecord>, чтобы не строить этот массив внутри
    @Override
    public void writeEntities(GarType garType, List<? extends GarRecord> entities) {
        if (entities.isEmpty()) {
            return;
        }

        switch (garType) {
            case ADDR_OBJ_TYPES -> writeAddressObjectTypes((List<AddressObjectType>) entities);
            default -> throw new IllegalStateException("Unexpected value: " + garType);
        }
    }

    private void writeAddressObjectTypes(List<AddressObjectType> entities) {
        GeneratedSQL generatedSQL = SQLQueries.get(GarType.ADDR_OBJ_TYPES);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(generatedSQL.selectIdIn());
             PreparedStatement insertStatement = connection.prepareStatement(generatedSQL.insertSQL())) {

            // Получаем список существующих записей
            Map<Integer, AddressObjectType> existingEntities = new HashMap<>();
            selectStatement.setArray(1, connection.createArrayOf(generatedSQL.idColumnType(),
                    entities.stream().map(addressObjectTypeJm.primaryKey()).toArray()));
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    AddressObjectType existingEntity = addressObjectTypeJm.read(resultSet);
                    existingEntities.put(addressObjectTypeJm.primaryKey().apply(existingEntity), existingEntity);
                }
            }

            // Сравниваем с тем, что есть в базе данных
            // TODO — если ничего в базе не нашли — сразу переходим к вставке в таблицы
            boolean updateNeeded = false;
            List<AddressObjectType> entitiesToInsert = new ArrayList<>();
            for (AddressObjectType entity : entities) {
                Integer primaryKey = addressObjectTypeJm.primaryKey().apply(entity);
                AddressObjectType existingEntity = existingEntities.get(primaryKey);
                if (null == existingEntity) {
                    entitiesToInsert.add(entity);
                    continue;
                }
                if (existingEntity.equals(entity)) {
                    continue;
                }
                updateNeeded = true;
                entitiesToInsert.add(entity);
            }

            if (!entitiesToInsert.isEmpty()) {
                // Сохраняем новые записи
                for (AddressObjectType entity : entitiesToInsert) {
                    addressObjectTypeJm.write(entity, insertStatement);
                    insertStatement.addBatch();
                }

                int[] batch = insertStatement.executeBatch();
                if (batch.length != entitiesToInsert.size()) {
                    throw new RuntimeException(
                            "Not all records were inserted, only " + batch.length + " instead of " + entities.size());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to write entities", e);
        }
    }
}
