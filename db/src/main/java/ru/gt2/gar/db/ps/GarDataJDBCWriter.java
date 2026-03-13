package ru.gt2.gar.db.ps;

import lombok.RequiredArgsConstructor;
import ru.gt2.gar.db.tm.TableMapping;
import ru.gt2.gar.db.tm.TableMappings;
import ru.gt2.gar.domain.GarRecord;
import ru.gt2.gar.domain.GarType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/// Писатель в базу пачек данных
@RequiredArgsConstructor
public class GarDataJDBCWriter {
    private final GarType garType;
    private final Connection connection;

    public void write(List<? extends GarRecord> entities) throws SQLException {
        TableMapping<GarRecord, ? extends Number> tableMapping = TableMappings.get(garType);
        GeneratedSQL generatedSQL = SQLQueries.get(garType);
        try (PreparedStatement selectStatement = connection.prepareStatement(generatedSQL.selectIdIn());
             PreparedStatement insertStatement = connection.prepareStatement(generatedSQL.insertSQL())) {

            // Получаем список существующих записей
            Function<GarRecord, ? extends Number> primaryKey = tableMapping.getPrimaryKey();
            Map<Number, GarRecord> existingEntities = new HashMap<>();
            selectStatement.setArray(1, connection.createArrayOf(generatedSQL.idColumnType(),
                    entities.stream()
                            .map(primaryKey)
                            .toArray()));
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    GarRecord existingEntity = tableMapping.read(resultSet);
                    existingEntities.put(primaryKey.apply(existingEntity), existingEntity);
                }
            }

            // Сравниваем с тем, что есть в базе данных
            // TODO — если ничего в базе не нашли — сразу переходим к вставке в таблицы
            boolean updateNeeded = false;
            List<GarRecord> entitiesToInsert = new ArrayList<>();
            for (GarRecord entity : entities) {
                Number pk = primaryKey.apply(entity);
                GarRecord existingEntity = existingEntities.get(pk);
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
                for (GarRecord entity : entitiesToInsert) {
                    tableMapping.write(entity, insertStatement);
                    insertStatement.addBatch();
                }

                int[] batch = insertStatement.executeBatch();
                if (batch.length != entitiesToInsert.size()) {
                    throw new RuntimeException(
                            "Not all records were inserted, only " + batch.length + " instead of " + entities.size());
                }
            }
        }
    }
}
