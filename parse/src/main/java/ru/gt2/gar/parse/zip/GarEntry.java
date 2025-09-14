package ru.gt2.gar.parse.zip;

import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record GarEntry(
        String name,
        LocalDate date,
        UUID id,
        Optional<String> dir,
        long size) {

    public GarEntry(String name, LocalDate date, UUID id, @Nullable String dir) {
        this(name, date, id, Optional.ofNullable(dir), -1);
    }

    public GarEntry withSize(long size) {
            return new GarEntry(name, date, id, dir, size);
    }
}
