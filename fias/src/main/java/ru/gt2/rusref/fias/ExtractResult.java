package ru.gt2.rusref.fias;

import lombok.RequiredArgsConstructor;

import lombok.Setter;
import lombok.ToString;
/**
 * Представление результата импорта.
 */
@RequiredArgsConstructor
@ToString(exclude = "filename")
public class ExtractResult {
    /** Исходный файл. */
    private final String filename;

    /** Количество загруженных элементов. */
    private int itemCount;

    public void increaceItemCount() {
        itemCount++;
    }
}
