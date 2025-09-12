package ru.gt2.gar.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/// Описание исходной XSD-схемы
@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface SourceSchema {
    /// Суффикс сразу после имени, не документированный
    String suffix() default "_2";
    /// Часть
    String part();
    /// Версия формата
    String format();
    /// Номер версии схемы
    String schema();
}
