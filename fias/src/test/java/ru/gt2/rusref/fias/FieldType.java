package ru.gt2.rusref.fias;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import javax.persistence.Id;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAttribute;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Тип поля.
 */
public enum FieldType {
    INTEGER(Integer.class,
        Collections.<Class<? extends Annotation>>singleton(Digits.class),
        Collections.<Class<? extends Annotation>>singleton(Id.class)),
    STRING(String.class,
        Collections.<Class<? extends Annotation>>singleton(Size.class),
        Collections.<Class<? extends Annotation>>singleton(Id.class)),
    DATE(Date.class,
        Collections.<Class<? extends Annotation>>emptySet(),
        ImmutableSet.<Class<? extends Annotation>>of(Past.class, Future.class)),
    GUID(UUID.class,
        Collections.<Class<? extends Annotation>>emptySet(),
        Collections.<Class<? extends Annotation>>singleton(Id.class));

    public final Class<?> type;
    public final ImmutableSet<Class<? extends Annotation>> required;
    public final ImmutableSet<Class<? extends Annotation>> optional;
    public final ImmutableSet<Class<? extends Annotation>> all;
    
    public static final ImmutableMap<Class<?>, FieldType> FROM_TYPE;

    static {
        Map<Class<?>, FieldType> fromType = Maps.newHashMap();
        for (FieldType fieldType : values()) {
            fromType.put(fieldType.type, fieldType);
        }
        FROM_TYPE = ImmutableMap.copyOf(fromType);
    }

    private FieldType(Class<?> type,
                      Set<Class<? extends Annotation>> required,
                      Set<Class<? extends Annotation>> optional) {
        this.type = type;
        
        this.required = ImmutableSet.copyOf(Sets.union(required, Collections.singleton(XmlAttribute.class)));
        this.optional = ImmutableSet.copyOf(Sets.union(optional, Collections.singleton(NotNull.class)));
        this.all = ImmutableSet.copyOf(Sets.union(this.required, this.optional));
    }
}
