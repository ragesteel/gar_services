package ru.gt2.gar.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/// Нужно применять для полей с типом int. При сохранении будет происходить треобразование 0 в false, 1 в true
@Retention(RUNTIME)
@Target(ElementType.RECORD_COMPONENT)
public @interface IntAsBoolean {
}
