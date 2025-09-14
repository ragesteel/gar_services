package ru.gt2.gar.parse.zip;

import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntryNameMatcher {
    private static final Pattern NAME_PATTERN = Pattern.compile(
            "^((?<Dir>\\d{2})/)?AS_(?<Name>[A-Z_]+)_(?<Year>\\d{4})(?<Month>\\d{2})(?<Day>\\d{2})_" +
                    "(?<UUID>[a-f0-9]{8}-[a-f0-9]{4}-[A-F0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})\\.XML$", Pattern.CASE_INSENSITIVE);

    @Nullable
    public static GarEntry tryParse(String name) {
        Matcher matcher = NAME_PATTERN.matcher(name);
        if (!matcher.find()) {
            return null;
        }
        var day = Integer.parseInt(matcher.group("Day"));
        var month = Integer.parseInt(matcher.group("Month"));
        var year = Integer.parseInt(matcher.group("Year"));

        return new GarEntry(matcher.group("Name"), LocalDate.of(year, month, day),
                UUID.fromString(matcher.group("UUID")), matcher.group("Dir"));
    }
}
