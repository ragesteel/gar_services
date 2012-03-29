package ru.gt2.rusref.fias;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Аннотация для элемента справочника.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FiasRef {
    /** Целевой справочник. */
    Fias value();
}
