package ru.gt2.rusref;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Описание сущности/поля.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface Description {
    String value();
}
