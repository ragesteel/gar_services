package ru.gt2.rusref;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import ru.gt2.rusref.fias.FiasRef;
import ru.gt2.rusref.stat.DateFieldStatistics;
import ru.gt2.rusref.stat.IntegerFieldStatistics;
import ru.gt2.rusref.stat.ObjectFieldStatistics;
import ru.gt2.rusref.stat.StringFieldStatistics;

import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Тип поля.
 */
public enum FieldType {
    INTEGER(Integer.class,
        Collections.<Class<? extends Annotation>>singleton(Digits.class),
        ImmutableSet.<Class<? extends Annotation>>of(Id.class, FiasRef.class)) {

        @Override
        public IntegerFieldStatistics createFieldStatistics(Field field) {
            return new IntegerFieldStatistics(field);
        }
    },
    STRING(String.class,
        Collections.<Class<? extends Annotation>>singleton(Size.class),
        ImmutableSet.<Class<? extends Annotation>>of(Id.class, FiasRef.class)) {

        @Override
        public StringFieldStatistics createFieldStatistics(Field field) {
            return new StringFieldStatistics(field);
        }
    },
    DATE(Date.class,
        Collections.<Class<? extends Annotation>>emptySet(),
        ImmutableSet.<Class<? extends Annotation>>of(Past.class, Future.class)) {

        @Override
        public DateFieldStatistics createFieldStatistics(Field field) {
            return new DateFieldStatistics(field);
        }
    },
    GUID(UUID.class,
        Collections.<Class<? extends Annotation>>emptySet(),
        ImmutableSet.<Class<? extends Annotation>>of(Id.class, FiasRef.class));

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

    public ObjectFieldStatistics<?> createFieldStatistics(Field field) {
        return new ObjectFieldStatistics<Object>(field);
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
