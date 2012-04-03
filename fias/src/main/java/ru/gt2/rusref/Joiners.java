package ru.gt2.rusref;

import com.google.common.base.Joiner;

/**
 * Экземпляры Joiner'ов
 */
public class Joiners {
    public static final Joiner COMMA_SEPARATED = Joiner.on(',').useForNull("");
}
