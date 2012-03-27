package ru.gt2.rusref.fias;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAttribute;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Тип поля.
 */
public enum FieldType {
    INTEGER(Integer.class, Collections.<Class<? extends Annotation>>singleton(Digits.class)),
    STRING(String.class, Collections.<Class<? extends Annotation>>singleton(Size.class)),
    DATE(Date.class, ImmutableSet.<Class<? extends Annotation>>of(Past.class, Future.class)),
    GUID(UUID.class, Collections.<Class<? extends Annotation>>emptySet());

    public final Class<?> type;
    public final ImmutableSet<Class<? extends Annotation>> required;
    public final ImmutableSet<Class<? extends Annotation>> optional;
    
    public static final ImmutableMap<Class<?>, FieldType> FROM_TYPE;

    static {
        Map<Class<?>, FieldType> fromType = Maps.newHashMap();
        for (FieldType fieldType : values()) {
            fromType.put(fieldType.type, fieldType);
        }
        FROM_TYPE = ImmutableMap.copyOf(fromType);
    }

    private FieldType(Class<?> type, 
                      Set<Class<? extends Annotation>> optional) {
        this.type = type;
        
        this.required = ImmutableSet.<Class<? extends Annotation>>of(XmlAttribute.class);
        this.optional = ImmutableSet.copyOf(Sets.union(optional, Collections.singleton(NotNull.class)));
    }
}
