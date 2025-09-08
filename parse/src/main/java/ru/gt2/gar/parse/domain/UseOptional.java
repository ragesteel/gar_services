package ru.gt2.gar.parse.domain;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.RECORD_COMPONENT;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Указание на то что поле является опциональным (необязательным) и может быть null.

 * Увы и ах, но использовать {@link org.jspecify.annotations.Nullable} не получается,
 * ибо я не могу получить эту аннотацию ни в конструкторе ни в методе который получает значение.
 */
@Target(RECORD_COMPONENT)
@Retention(RUNTIME)
public @interface UseOptional {
}
