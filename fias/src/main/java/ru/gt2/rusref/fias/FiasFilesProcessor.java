package ru.gt2.rusref.fias;

import lombok.RequiredArgsConstructor;
import ru.gt2.rusref.CsvWriter;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Извлекатель данных из справочников ФИАС.
 */
@RequiredArgsConstructor
public class FiasFilesProcessor {

    private static final String FILE_PREFIX = "AS_";

    private static final String FILE_SUFFIX = ".XML";

    private final Fias fias;

    private final CsvWriter report;

    private File directory;

    private File[] files;

    private SinglePassProcessor abstractExtractor;

    public void processFiles(String directoryName) throws IOException, JAXBException {
        directory = new File(directoryName);
        findFiles();
        if ((null == files) || (0 == files.length)) {
            return;
        }

        abstractExtractor = createProcessor();
        abstractExtractor.process();
    }

    private void findFiles() {
        final String prefix = FILE_PREFIX + fias.name() + "_";
        final int nameLen = prefix.length() + FILE_SUFFIX.length() + 36 + 8 + 1;
        files = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (nameLen != name.length()) {
                    return false;
                }
                String upperName = name.toUpperCase();
                return upperName.startsWith(prefix) && upperName.endsWith(FILE_SUFFIX);
            }
        });
    }

    private SinglePassProcessor createProcessor() {
        boolean noSelfReferenceFields = Fias.getSelfReferenceFields(fias).isEmpty();
        if (noSelfReferenceFields) {
            return new SinglePassProcessor(fias, files, report, directory);
        } else {
            return new MultiPassProcessor(fias, files, report, directory);
        }
    }

}
