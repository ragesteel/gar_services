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
    Fias value();

    static final Predicate<Field> FIAS_REF = new Predicate<Field>() {
        @Override
        public boolean apply(@Nullable Field field) {
            return (null != field.getAnnotation(FiasRef.class));
        }
    };
}
