package ru.gt2.rusref;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;

import javax.annotation.Nullable;
import java.math.BigDecimal;
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
    private final CharMatcher escape = CharMatcher.anyOf("\"\\");

    @Override
    public String apply(@Nullable Object input) {
        if (null == input) {
            return "\\N";
        } else if (input instanceof String) {
            // FIXME Просто и наивное экранирование
            String string = (String) input;
            if (escape.matchesAnyOf(string)) {
                StringBuilder escaped = new StringBuilder();
                for (int i = 0; i < string.length(); i++) {
                    char c = string.charAt(i);
                    if (escape.matches(c)) {
                        escaped.append('\\');
                    }
                    escaped.append(c);
                }
                string = escaped.toString();
            }
            return '"' + string + '"';
        } else if (input instanceof Date) {
            return dateFormat.format((Date) input);
        } else if (input instanceof Integer) {
            return ((Integer) input).toString();
        } else if (input instanceof UUID) {
            String uuid = ((UUID) input).toString();
            return DASH.removeFrom(uuid);
        } else if (input instanceof BigDecimal) {
            return ((BigDecimal) input).toString();
        } else {
            throw new IllegalArgumentException("Processing not defined for class " + input.getClass());
        }
    }
}
