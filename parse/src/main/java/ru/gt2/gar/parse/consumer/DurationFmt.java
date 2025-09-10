package ru.gt2.gar.parse.consumer;

import java.time.Duration;

/**
 * Форматирование java.time.Duration, ибо форматёр Guava Stopwatch спрятан глубоко внутри.
 */
public class DurationFmt {
    public static String format(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
