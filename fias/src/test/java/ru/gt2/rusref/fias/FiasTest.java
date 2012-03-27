package ru.gt2.rusref.fias;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
    
    private static final ImmutableMap<Class<?>, ImmutableSet<Class<? extends Annotation>>> CONSTRAINS_BY_TYPES =
        ImmutableMap.<Class<?>, ImmutableSet<Class<? extends Annotation>>>builder()
            .put(String.class, ImmutableSet.of(NotNull.class, Size.class))
            .put(Integer.class, ImmutableSet.of(NotNull.class, Digits.class))
            .put(UUID.class, ImmutableSet.<Class<? extends Annotation>>of(NotNull.class))
            .put(Date.class, ImmutableSet.of(NotNull.class, Past.class, Future.class))
            .build();

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
            ImmutableSet<Class<? extends Annotation>> constrains = CONSTRAINS_BY_TYPES.get(type);
            Assert.assertNotNull("Type " + type + " does not contains in supported types",
                    constrains);
            
            System.out.println(field.getAnnotations());
            
            

            
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
