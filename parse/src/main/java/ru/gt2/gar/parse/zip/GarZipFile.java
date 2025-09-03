package ru.gt2.gar.parse.zip;

import com.google.common.collect.Streams;
import com.google.common.io.CharStreams;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static java.util.Objects.requireNonNull;
import static ru.gt2.gar.parse.zip.GarVersion.DATE_FORMATTER;

/**
 * Чтение данных из архива.
 */
// В идеале-бы конечно сразу читать из потока и обрабатывать, но
@Slf4j
public class GarZipFile {
    private static final String VERSION = "version.txt";
    private static final GarVersion NO_VERSION = new GarVersion(LocalDate.of(2000, Month.JANUARY, 1), -1);

    private GarVersion version;

    private final ZipFile zipFile;

    public GarZipFile(String fileName) throws IOException {
        requireNonNull(fileName, "fileName must be not null!");
        zipFile = new ZipFile(fileName);
    }

    public Stream<GarEntry> stream() {
        return Streams.stream(zipFile.entries().asIterator())
                .map(ze -> EntryNameMatcher.tryParse(ze.getName()))
                .filter(Objects::nonNull);
    }

    public Optional<GarVersion> getVersion() {
        if (null == version) {
            version = readVersion();
        }
        if (NO_VERSION.equals(version)) {
            return Optional.empty();
        }
        return Optional.of(version);
    }

    private GarVersion readVersion() {
        ZipEntry versionEntry = zipFile.getEntry(VERSION);
        if (null == versionEntry) {
            return NO_VERSION;
        }

        try (var isr = new InputStreamReader(zipFile.getInputStream(versionEntry))) {
            List<String> strings = CharStreams.readLines(isr);
            if (2 != strings.size()) {
                log.warn("Version lines count != 2, {}", strings);
                return NO_VERSION;
            }
            var dateVer = LocalDate.parse(strings.get(0), DATE_FORMATTER);

            var strVer = strings.get(1);
            if (!strVer.startsWith("v.")) {
                log.warn("Numeric version does not starts with v., {}", strings);
                return NO_VERSION;
            }
            var intVer = Integer.parseInt(strVer.substring(2));
            return new GarVersion(dateVer, intVer);
        } catch (IOException e) {
            log.warn("Unable to get version", e);
            return NO_VERSION;
        }
    }
}
