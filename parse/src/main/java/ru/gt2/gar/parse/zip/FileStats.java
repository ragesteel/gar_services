package ru.gt2.gar.parse.zip;

// TODO добавить информацию о количестве обработанных элементов и времени обработки,
//  возможно выделить для этого отдельный record
public record FileStats(int count, long size) {
    public FileStats add(FileStats term) {
        return new FileStats(count + term.count, size + term.size);
    }
}
