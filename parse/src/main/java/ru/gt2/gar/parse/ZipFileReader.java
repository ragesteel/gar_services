package ru.gt2.gar.parse;

import com.google.common.io.CharStreams;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static java.util.Objects.requireNonNull;
import static ru.gt2.gar.parse.GarVersion.DATE_FORMATTER;

/**
 * Чтение данных из архива.
 */
// В идеале-бы конечно сразу читать из потока и обрабатывать, но
@Slf4j
public class ZipFileReader {
    private static final String VERSION = "version.txt";
    private GarVersion version;
    private final ZipFile zipFile;

    public ZipFileReader(String fileName) throws IOException {
        requireNonNull(fileName, "fileName must be not null!");
        zipFile = new ZipFile(fileName);
    }

    public void process() throws IOException {
        zipFile.stream().forEach(this::onZipEntry);
    }

    private void onZipEntry(ZipEntry zipEntry) {
        try {
            if (VERSION.equals(zipEntry.getName())) {
                try (var is = zipFile.getInputStream(zipEntry)) {
                    parseVersion(is);
                }
            }
        } catch (IOException e) {
            log.error("Unable to process zipEntry " + zipEntry, e);
        }
        System.out.println(zipEntry);
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
            System.out.println("Version is: " + version);
        }
    }
}
