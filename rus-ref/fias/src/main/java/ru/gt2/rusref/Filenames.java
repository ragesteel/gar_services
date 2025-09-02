package ru.gt2.rusref;

import com.google.common.base.Preconditions;
import com.google.common.io.Files;

/**
 * Инструменты для работы с именами файлов.
 */
public class Filenames {
    public static String replaceExtension(String filename, String extension) {
        Preconditions.checkNotNull(filename);
        Preconditions.checkNotNull(extension);
        String existingExtension = Files.getFileExtension(filename);

        return filename.substring(0, filename.length() - existingExtension.length())
                + (existingExtension.isEmpty() ? "." : "") + extension;
    }
}
