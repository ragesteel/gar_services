package ru.gt2.gar.parse.zip;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.io.CharStreams;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static java.util.Objects.requireNonNull;
import static ru.gt2.gar.parse.zip.GarVersion.DATE_FORMATTER;

/**
 * Чтение данных из архива.
 */
// В идеале-бы конечно сразу читать из потока и обрабатывать, но
@Slf4j
public class ZipFileReader {
    private static final String VERSION = "version.txt";

    @Getter
    private GarVersion version;
    @Getter
    private final Multiset<String> garNames = HashMultiset.create();

    private final ZipFile zipFile;

    public ZipFileReader(String fileName) throws IOException {
        requireNonNull(fileName, "fileName must be not null!");
        zipFile = new ZipFile(fileName);
    }

    public void process() throws IOException {
        var entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            onZipEntry(entries.nextElement());
        }
    }

    private void onZipEntry(ZipEntry zipEntry) throws IOException {
        if (VERSION.equals(zipEntry.getName())) {
            try (var is = zipFile.getInputStream(zipEntry)) {
                parseVersion(is);
            }
            return;
        }
        parseName(zipEntry.getName());
    }

    private void parseVersion(InputStream is) throws IOException {
        try (var isr = new InputStreamReader(is)) {
            List<String> strings = CharStreams.readLines(isr);
            if (2 != strings.size()) {
                log.warn("Version lines count != 2, {}", strings);
                return;
            }
            var dateVer = LocalDate.parse(strings.get(0), DATE_FORMATTER);

            var strVer = strings.get(1);
            if (!strVer.startsWith("v.")) {
                log.warn("Numeric version does not starts with v., {}", strings);
                return;
            }
            var intVer = Integer.parseInt(strVer.substring(2));

            version = new GarVersion(dateVer, intVer);
        }
    }

    private void parseName(String name) {
        GarEntry garEntry = EntryNameMatcher.tryParse(name);
        if (null == garEntry) {
            log.warn("Unknown file: " + name);
            return;
        }
        garNames.add(garEntry.name());
    }
}
