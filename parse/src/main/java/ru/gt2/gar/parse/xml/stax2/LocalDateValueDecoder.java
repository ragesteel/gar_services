package ru.gt2.gar.parse.xml.stax2;

import org.codehaus.stax2.typed.TypedValueDecoder;

import java.time.LocalDate;

/// Простая и наивная реализация, только для дат формата YYYY-MM-DD.
/// По задумке — должна быть быстрой.
public class LocalDateValueDecoder extends TypedValueDecoder {
    public LocalDate value;

    @Override
    public void decode(String input) throws IllegalArgumentException {
        decodeValue(input);
    }

    @Override
    public void decode(char[] buffer, int start, int end) throws IllegalArgumentException {
        decodeValue(new SimpleCharSequence(buffer, start, end));
    }

    @Override
    public void handleEmptyValue() throws IllegalArgumentException {
        throw new IllegalArgumentException("Unable to parse date from empty string");
    }

    private void decodeValue(CharSequence charSequence) {
        if (charSequence.length() != 10) {
            throw new IllegalArgumentException("Unable to parse date from string: " + charSequence);
        }
        // 2026-03-05
        // 0123456789
        int year = Integer.parseInt(charSequence, 0, 4, 10);
        int month = Integer.parseInt(charSequence, 5, 7, 10);
        int day = Integer.parseInt(charSequence, 8, 10, 10);
        value = LocalDate.of(year, month, day);
        // В идеале бы ещё проверить на наличие разделителей, но сейчас приоритет - скорость обработки!
    }
}
