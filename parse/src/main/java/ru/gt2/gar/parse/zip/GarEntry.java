package ru.gt2.gar.parse.zip;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record GarEntry(
        String name,
        LocalDate date,
        UUID id,
        Optional<String> dir
) {
}
