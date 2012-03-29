package ru.gt2.rusref.fias;

import com.google.common.base.Predicate;

import javax.annotation.Nullable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

/**
 * Аннотация для элемента справочника.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FiasRef {
    /** Целевой справочник. */
    Class<?> value();
}
