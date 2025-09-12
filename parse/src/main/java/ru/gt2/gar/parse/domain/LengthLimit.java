package ru.gt2.gar.parse.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/// Ограничение длины для VARCHAR
@Retention(RUNTIME)
@Target(ElementType.RECORD_COMPONENT)
public @interface LengthLimit {
    int value();
}
