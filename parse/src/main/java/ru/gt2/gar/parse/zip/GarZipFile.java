package ru.gt2.gar.parse.zip;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Streams;
import com.google.common.io.CharStreams;
import lombok.extern.slf4j.Slf4j;
import ru.gt2.gar.parse.domain.GarType;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Maps.newLinkedHashMap;
import static java.util.Objects.requireNonNull;
import static ru.gt2.gar.parse.zip.GarVersion.DATE_FORMATTER;

/**
 * Чтение данных из архива.
 */
@Slf4j
public class GarZipFile {
    private static final String VERSION = "version.txt";
    private static final GarVersion NO_VERSION = new GarVersion(LocalDate.of(2000, Month.JANUARY, 1), -1);

    private GarVersion version;

    private final ZipFile zipFile;
    private Map<GarEntry, ZipEntry> entries;
    private Map<GarType, FileStats> stats;

    public GarZipFile(String fileName) throws IOException {
        requireNonNull(fileName, "fileName must be not null!");
        zipFile = new ZipFile(fileName);
    }

    public Stream<GarEntry> streamEntries() {
        if (null == entries) {
            fillEntries();
        }

        return entries.keySet().stream();
    }

    public InputStream getInputStream(GarEntry ge) throws IOException {
        ZipEntry entry = entries.get(requireNonNull(ge));
        if (null == entry) {
            throw new IOException("ZipEntry not found: " + ge);
        }
        return zipFile.getInputStream(entry);
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

    public Map<GarType, FileStats> getStats() {
        if (null == entries) {
            fillEntries();
        }

        return ImmutableMap.copyOf(stats);
    }

    private void fillEntries() {
        entries = newHashMap();
        stats = newLinkedHashMap();

        Streams.stream(zipFile.entries().asIterator()).forEach(ze -> {
            GarEntry ge = EntryNameMatcher.tryParse(ze.getName());
            if (null == ge) {
                return;
            }
            entries.put(ge, ze);
            stats.merge(GarType.valueOf(ge.name()), new FileStats(1, ze.getSize()), FileStats::add);
        });
    }

    /* TODO что-то подобное нужно добавить, чтобы убедиться в том что мы распарсим всё что есть и что мы ничего не потеряли
    public void validateNames() {
        Multiset<String> garNames = stream().collect(Multisets.toMultiset(GarEntry::name, ge -> 1, HashMultiset::create));
        EnumSet<GarTypes> enumSet = EnumSet.allOf(GarTypes.class);
        garNames.forEach(gn -> {
            String name = gn.substring(3);
            GarTypes garType = GarTypes.valueOf(name);
            enumSet.remove(garType);
        });
        if (!enumSet.isEmpty()) {
            println("Not used: " + enumSet);
        }
    }
    */

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
