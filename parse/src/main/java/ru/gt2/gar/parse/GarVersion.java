package ru.gt2.gar.parse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.YEAR;

public record GarVersion(LocalDate date, int number) {
    static DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
                    .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
                    .appendLiteral('.')
                    .appendValue(MONTH_OF_YEAR, 2)
                    .appendLiteral('.')
                    .appendValue(DAY_OF_MONTH, 2)
                    .toFormatter();
}
