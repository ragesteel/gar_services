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
            FieldType fieldType = FieldType.FROM_TYPE.get(type);
            Assert.assertNotNull("Type " + type + " does not contains in supported types",
                    fieldType);
            Annotation[] annotations = field.getAnnotations();
            ImmutableSet<Class<? extends Annotation>> annotationClasses = ImmutableSet.copyOf(
                    (Iterables.transform(Arrays.asList(annotations), ANNOTATION_CLASS)));
            ImmutableSet<Class<? extends Annotation>> requiredAnnotations = fieldType.required;
            Sets.SetView<Class<? extends Annotation>> intersectionWithRequired = 
                    Sets.intersection(requiredAnnotations, annotationClasses);
            Assert.assertEquals("Missing required annotations " +
                    Sets.difference(requiredAnnotations, annotationClasses),
                    requiredAnnotations.size(), intersectionWithRequired.size());
            Sets.SetView<Class<? extends Annotation>> difference =
                    Sets.difference(annotationClasses, ImmutableSet.copyOf(fieldType.all));
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
