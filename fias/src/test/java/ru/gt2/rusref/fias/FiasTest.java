package ru.gt2.rusref.fias;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.*;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Nullable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Тест для проверки соответствия аннотаций моим пожеланиям к классам.
 */
public class FiasTest {

    private static Function<Field, String> FIELD_NAME = new Function<Field, String>() {
        @Override
        public String apply(@Nullable Field field) {
            return field.getName();
        }
    };

    private static Function<Annotation, Class<? extends Annotation>> ANNOTATION_CLASS =
            new Function<Annotation, Class<? extends Annotation>>() {
        @Override
        public Class<? extends Annotation> apply(@Nullable Annotation annotation) {
            return annotation.annotationType();
        }
    };

    private static final ImmutableSet<Class<? extends Annotation>> COMMON_CONSTRAINS =
            ImmutableSet.of(NotNull.class, XmlAttribute.class);

    private static final ImmutableMultimap<Class<?>, Class<? extends Annotation>> CONSTRAINS_BY_TYPE;

    static {
        // FIXME Отдельный класс, с разделением на обязательные и необязательные, FieldType
        Multimap<Class<?>, Class<? extends Annotation>> initial = Multimaps.newSetMultimap(
                Maps.<Class<?>, Collection<Class<? extends Annotation>>>newHashMap(),
                new Supplier<Set<Class<? extends Annotation>>>() {
                    @Override
                    public Set<Class<? extends Annotation>> get() {
                        return Sets.newHashSet();
                    }
                });

        initial.put(String.class, Size.class);
        initial.put(Integer.class, Digits.class);
        initial.putAll(Date.class, Arrays.asList(Past.class, Future.class));
        for (Class<?> type : initial.keySet()) {
            initial.putAll(type, COMMON_CONSTRAINS);
        }
        initial.putAll(UUID.class, COMMON_CONSTRAINS);

        CONSTRAINS_BY_TYPE = ImmutableMultimap.copyOf(initial);
    }

    /**
     * Проверка налаичия всех полей в propOrder и propOrder на укакзания на поля.
     * Дубликаты propOrder также нужно удалять.
     */
    @Test
    public void testFieldsInPropOrder() {
        for (Fias fias : Fias.values()) {
            testFieldsInPropOrder(fias);
        }
    }

    /**
     * Проверка на то, что в каждом поле есть @NotNull, если но Required.
     */
    @Test
    public void testFieldsNullable() {
        for (Fias fias : Fias.values()) {
            testFieldsNullable(fias);
        }
    }

    /**
     * Проверка ограничений по полям, в зависмости от типа.
     */
    @Test
    public void testFieldConstrainsByType() {
        for (Fias fias : Fias.values()) {
            testFieldsConstrainsByType(fias);
        }
    }

    private void testFieldsInPropOrder(Fias fias) {

        String[] propOrderArr = getPropOrder(fias);
        Set<String> propOrder = Sets.newHashSet(propOrderArr);
        Assert.assertEquals(propOrderArr.length, propOrder.size());
        
        List<Field> fields = getAllFields(fias);
        Set<String> fieldNames = Sets.newHashSet(Iterables.transform(fields, FIELD_NAME));
        Assert.assertEquals(fields.size(), fieldNames.size());
        Assert.assertEquals(fias.name(), fieldNames, propOrder);
    }

    private void testFieldsNullable(Fias fias) {
        List<Field> fields = getAllFields(fias);
        for (Field field : fields) {
            XmlAttribute xmlAttribute = field.getAnnotation(XmlAttribute.class);
            Assert.assertNotNull(xmlAttribute);
            NotNull notNull = field.getAnnotation(NotNull.class);
            if (xmlAttribute.required()) {
                Assert.assertNotNull("Field " + field + " is required, must be notNull", notNull);
            } else {
                Assert.assertNull(notNull);
            }
        }
    }

    private void testFieldsConstrainsByType(Fias fias) {
        List<Field> fields = getAllFields(fias);
        for (Field field : fields) {
            Class<?> type = field.getType();
            ImmutableCollection<Class<? extends Annotation>> allowedAnnotations = CONSTRAINS_BY_TYPE.get(type);
            Assert.assertFalse("Type " + type + " does not contains in supported types",
                    allowedAnnotations.isEmpty());
            Annotation[] annotations = field.getAnnotations();
            ImmutableSet<Class<? extends Annotation>> annotationClasses = ImmutableSet.copyOf(
                    (Iterables.transform(Arrays.asList(annotations), ANNOTATION_CLASS)));
            Sets.SetView<Class<? extends Annotation>> difference = 
                    Sets.difference(annotationClasses, ImmutableSet.copyOf(allowedAnnotations));
            Assert.assertTrue("Field " + field + " contains annotation(s) that not allowed: " + difference,
                    difference.isEmpty());
        }
    }
    
    // internals

    private String[] getPropOrder(Fias fias) {
        Class<?> item = fias.item;
        XmlType xmlType = item.getAnnotation(XmlType.class);
        Assert.assertNotNull(xmlType);
        return xmlType.propOrder();
    }
    
    private List<Field> getAllFields(Fias fias) {
        Class<?> item = fias.item;
        List<Field> fields = Lists.newArrayList();
        while (!Object.class.equals(item)) {
            fields.addAll(Arrays.asList(item.getDeclaredFields()));
            item = item.getSuperclass();
        }
        return fields;
    }

}
