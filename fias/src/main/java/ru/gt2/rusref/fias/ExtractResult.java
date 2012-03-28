package ru.gt2.rusref.fias;

import com.google.common.collect.Maps;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * Представление результата импорта.
 */
@ToString
public class ExtractResult {
    /** Количество загруженных элементов. */
    private int itemCount;
    /** Статистика по полям. */
    private final Map<String, ObjectFieldStatistics> statistics;

    public ExtractResult(Fias fias) {
        statistics = Maps.newLinkedHashMap();
        for (Field field : fias.itemFields) {
            statistics.put(field.getName(), ObjectFieldStatistics.newFieldStatistics(field));
        }
    }
    
    public void updateStatistics(Object item) {
        itemCount++;
        for (ObjectFieldStatistics fieldStatistics : statistics.values()) {
            fieldStatistics.updateStatistics(item);
        }
    }
}
