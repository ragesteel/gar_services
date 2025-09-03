package ru.gt2.gar.parse.zip;

public record FileStats(int count, long size) {
    public FileStats add(FileStats term) {
        return new FileStats(count + term.count, size + term.size);
    }
}
