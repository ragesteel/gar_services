package ru.gt2.rusref.fias;

import lombok.ToString;

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
    private Map<String, ObjectFieldStatistics> statistics;

    @Deprecated // Нужно перейти на updateStatistics
    public void increaceItemCount() {
        itemCount++;
    }
    
    public ExtractResult newExtractResult(Fias fias) {
        // FIXME Иницилизация статистики по полям
        return null;
    }
    
    public void updateStatistics(Object item) {
        itemCount++;
        // FIXME Проход по полям
        for (ObjectFieldStatistics fieldStatistics : statistics.values()) {
            fieldStatistics.updateStatistics(item);
        }
    }
}
