package ru.gt2.rusref;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;

import javax.annotation.Nullable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Форматирование данных для MySQL.
 */
public class MysqlCsvFormatter implements Function<Object, String> {

    private static final CharMatcher DASH = CharMatcher.is('-');
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final CharMatcher escape;

    public MysqlCsvFormatter(CharMatcher escape) {
        this.escape = escape;
    }

    @Override
    public String apply(@Nullable Object input) {
        if (null == input) {
            return "\\N";
        } else if (input instanceof String) {
            // FIXME Тут нужен escap'инг.
            String string = (String) input;
            if (escape.matchesAnyOf(string)) {
                throw new IllegalArgumentException("Escape is not implement, but required for string: " + input);
            }
            return string;
        } else if (input instanceof Date) {
            return dateFormat.format((Date) input);
        } else if (input instanceof Integer) {
            return ((Integer) input).toString();
        } else if (input instanceof UUID) {
            String uuid = ((UUID) input).toString();
            return DASH.removeFrom(uuid);
        } else {
            throw new IllegalArgumentException("Processing not defined for class " + input.getClass());
        }
    }
}
