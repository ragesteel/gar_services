package ru.gt2.gar.parse.zip;

import org.jspecify.annotations.Nullable;
import ru.gt2.gar.domain.Gar;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record GarEntry(
        String name,
        LocalDate date,
        UUID id,
        Optional<String> dir,
        long size,
        int crc32) {

    public GarEntry(String name, LocalDate date, UUID id, @Nullable String dir) {
        this(name, date, id, Optional.ofNullable(dir), -1, 0);
    }

    public GarEntry withSizeCrc32(long newSize, int newCrc32) {
            return new GarEntry(name, date, id, dir, newSize, newCrc32);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        int pad = Gar.MAX_NAME_LENGTH - name.length() + 3 + 1;
        if (dir.isPresent()) {
            result.append(dir.get()).append('/');
            pad -= 3;
        }

        result.append(name).repeat(' ', pad).append(id);
        if (0 != crc32) {
            String crcStr = Integer.toHexString(crc32);
            result.repeat(' ', 1 + 8 - crcStr.length()).append(crcStr);
        }
        if (-1 != size) {
            String sizeStr = DecimalFormat.getInstance().format(size);
            result.repeat(' ', 1 + 15 - sizeStr.length()).append(sizeStr);
        }

        return result.toString();
    }

    public String nameWithDir() {
        return dir.map(s -> s + "/" + name).orElse(name);
    }
}
