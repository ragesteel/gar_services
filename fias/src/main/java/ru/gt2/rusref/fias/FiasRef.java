package ru.gt2.rusref.fias;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для ссылки на справочник.
 * Также используется на промежуточных таблицах для указания источника данных.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface FiasRef {
    /** Целевой справочник. */
    Class<?> value();

    /**
     * Целевое поле.
     *
     * По умолчанию берётся первичный ключ.
     */
    String fieldName() default "";
}
