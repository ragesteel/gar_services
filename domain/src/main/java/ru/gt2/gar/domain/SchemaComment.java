package ru.gt2.gar.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/// Комментарий к схеме базы данных, применимо и к таблица и к полям
@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.RECORD_COMPONENT})
public @interface SchemaComment {
    String value();
}
