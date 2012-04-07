package ru.gt2.rusref.fias;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.common.io.NullOutputStream;
import org.junit.Assert;
import org.junit.Test;
import ru.gt2.rusref.Description;
import ru.gt2.rusref.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.io.ObjectOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Тест для проверки соответствия аннотаций моим пожеланиям к классам.
 *
 * FIXME Заставить работать с Enhance'нутыми классами.
 */
public class FiasTest {

    private static final String DEFAULT = "##default";

    private static final ImmutableMap<Fias, String> SCHEME_BY_FIAS =
        ImmutableMap.<Fias, String>builder()
            .put(Fias.ADDROBJ,  "AS_ADDROBJ_2_250_01_04_01_01")
            .put(Fias.HOUSE,    "AS_HOUSE_2_250_02_04_01_01")
            .put(Fias.HOUSEINT, "AS_HOUSEINT_2_250_03_04_01_01")
            .put(Fias.LANDMARK, "AS_LANDMARK_2_250_04_04_01_01")
            .put(Fias.NORMDOC,  "AS_NORMDOC_2_250_05_04_01_01")
            .put(Fias.SOCRBASE,  "AS_SOCRBASE_2_250_06_04_01_01")
            .put(Fias.CURENTST,  "AS_CURENTST_2_250_07_04_01_01")
            .put(Fias.ACTSTAT,   "AS_ACTSTAT_2_250_08_04_01_01")
            .put(Fias.OPERSTAT,  "AS_OPERSTAT_2_250_09_04_01_01")
            .put(Fias.CENTERST,  "AS_CENTERST_2_250_10_04_01_01")
            .put(Fias.INTVSTAT,  "AS_INTVSTAT_2_250_11_04_01_01")
            .put(Fias.HSTSTAT,   "AS_HSTSTAT_2_250_12_04_01_01")
            .put(Fias.ESTSTAT,   "AS_ESTSTAT_2_250_13_04_01_01")
            .put(Fias.STRSTAT,   "AS_STRSTAT_2_250_14_04_01_01")
            .build();

    private static final ImmutableSet<Class<? extends Annotation>> REQUIRED_ANNOTATIONS =
            ImmutableSet.of(Description.class, Entity.class, XmlType.class);

    private static Function<Field, String> FIELD_NAME = new Function<Field, String>() {
        @Override
        public String apply(Field field) {
            return field.getName();
        }
    };

    private static Function<Annotation, Class<? extends Annotation>> ANNOTATION_CLASS =
            new Function<Annotation, Class<? extends Annotation>>() {
        @Override
        public Class<? extends Annotation> apply(Annotation annotation) {
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

    @Test
    public void testSchemeNames() {
        for (Fias fias : Fias.values()) {
            String scheme = SCHEME_BY_FIAS.get(fias);
            Assert.assertNotNull(scheme);
            String schemePrefix = scheme.substring(0, scheme.length() - 2);
            Assert.assertEquals(schemePrefix, fias.schemePrefix);
        }
    }

    /**
     * Проверка на типы ссылок.
     */
    @Test
    public void testReferenceTypes() {
        for (Fias fias : Fias.values()) {
            testReferenceTypes(fias);
        }
    }

    @Test
    public void testSerializable() throws Exception {
        for (Fias fias : Fias.values()) {
            testSerializable(fias);
        }
    }

    @Test
    public void testWrapperHasContainer() {
        for (Fias fias : Fias.values()) {
            testWrapperHasContainer(fias);
        }
    }

    @Test
    public void testItemAnnotations() {
        for (Fias fias : Fias.values()) {
            testItemAnnotations(fias);
        }
    }

    private void testFieldsInPropOrder(Fias fias) {
        String[] propOrderArr = getPropOrder(fias);
        Set<String> propOrder = Sets.newHashSet(propOrderArr);
        Assert.assertEquals(propOrderArr.length, propOrder.size());
        
        List<Field> fields = fias.itemFields;
        Set<String> fieldNames = Sets.newHashSet(Iterables.transform(fields, FIELD_NAME));
        Assert.assertEquals(fields.size(), fieldNames.size());
        Assert.assertEquals(fias.name(), fieldNames, propOrder);
    }

    private void testFieldsNullable(Fias fias) {
        for (Field field : fias.itemFields) {
            XmlAttribute xmlAttribute = field.getAnnotation(XmlAttribute.class);
            Assert.assertNotNull(xmlAttribute);
            NotNull notNull = field.getAnnotation(NotNull.class);
            if (xmlAttribute.required()) {
                Assert.assertNotNull("Field " + field + " is required, must be notNull", notNull);
            } else {
                Assert.assertNull(notNull);
            }
            
            Id id = field.getAnnotation(Id.class);
            if (null != id) {
                Assert.assertNotNull("Field " + field + " is key, must be notNull", notNull);
            }
        }
    }

    private void testFieldsConstrainsByType(Fias fias) {
        for (Field field : fias.itemFields) {
            testFieldConstrainsByType(field);
        }
    }

    private void testFieldConstrainsByType(Field field) {
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
        Assert.assertEquals("Field " + field + " missing required annotations " +
                Sets.difference(requiredAnnotations, annotationClasses),
                requiredAnnotations.size(), intersectionWithRequired.size());
        Sets.SetView<Class<? extends Annotation>> difference =
                Sets.difference(annotationClasses, ImmutableSet.copyOf(fieldType.all));
        Assert.assertTrue("Field " + field + " contains annotation(s) that not allowed: " + difference,
                difference.isEmpty());

        for (Annotation annotation : annotations) {
            testFieldAnnotationConstrains(field, annotation);
        }
    }

    private void testFieldAnnotationConstrains(Field field, Annotation annotation) {
        Class<? extends Annotation> annotationType = annotation.annotationType();
        if (Size.class.equals(annotationType)) {
            Size size = (Size) annotation;
            Assert.assertTrue(field + ", Size min must be >= 0",
                    size.min() >= 0);
            Assert.assertTrue(field + ", Size min must be <= max",
                    size.min() <= size.max());
        } else if (XmlAttribute.class.equals(annotationType)) {
            XmlAttribute xmlAttribute = (XmlAttribute) annotation;
            Assert.assertFalse(field + ", name must be Set" + xmlAttribute.name(),
                    DEFAULT.equals(xmlAttribute.name()));
        } else if (Digits.class.equals(annotationType)) {
            Digits digits = (Digits) annotation;
            if (Integer.class.equals(field.getType())) {
                Assert.assertEquals(field + ", fraction must be set to 0 for Integer", 0, digits.fraction());
            }
        } else if (Column.class.equals(annotationType)) {
            Column column = (Column) annotation;
            Assert.assertTrue(field + ", nullable must be not set if NotNull present",
                    column.nullable() == (null == field.getAnnotation(NotNull.class)));

            Size size = field.getAnnotation(Size.class);
            if (null != size) {
                if (Integer.MAX_VALUE != size.max()) {
                    Assert.assertEquals(field + ", Column.length != Size.max",
                            size.max(), column.length());
                }
            }

            Digits digits = field.getAnnotation(Digits.class);
            if (null != digits) {
                Assert.assertEquals(field + ", Column.scale != Digits.integer",
                        digits.integer(), column.scale());
                Assert.assertEquals(field + ", Column.precision != Digits.fraction",
                        digits.fraction(), column.precision());
            }
        }
    }

    private void testReferenceTypes(Fias fias) {
        for (Field field : Fias.getReferences(fias.itemFields)) {
            Fias fiasTarget = Fias.FIAS_REF_TARGET.apply(field);
            Assert.assertEquals("Reference (" + field + ") and id field must be of the same type",
                    fiasTarget.idField.getType(), field.getType());
        }
    }

    private void testSerializable(Fias fias) throws Exception {
        Class<?> itemClass = fias.item;
        Object item = itemClass.newInstance();
        Assert.assertNotNull(item);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new NullOutputStream());
        objectOutputStream.writeObject(item);
        objectOutputStream.close();
    }

    private void testWrapperHasContainer(Fias fias) {
        Assert.assertTrue("Wrapper of " + fias + " does not implement Container",
                Arrays.asList(fias.wrapper.getInterfaces()).contains(Container.class));
    }

    private void testItemAnnotations(Fias fias) {
        Class<?> item = fias.item;
        Annotation[] annotations = item.getAnnotations();
        ImmutableSet<Class<? extends Annotation>> annotationClasses = ImmutableSet.copyOf(
                (Iterables.transform(Arrays.asList(annotations), ANNOTATION_CLASS)));

        for (Class<? extends Annotation> requiredAnnotation : REQUIRED_ANNOTATIONS) {
            Annotation annotation = item.getAnnotation(requiredAnnotation);
            Assert.assertNotNull(item + " must have @" + requiredAnnotation.getSimpleName(), annotation);
        }
    }

    // internals

    private String[] getPropOrder(Fias fias) {
        Class<?> item = fias.item;
        XmlType xmlType = item.getAnnotation(XmlType.class);
        Assert.assertNotNull(xmlType);
        return xmlType.propOrder();
    }
}
