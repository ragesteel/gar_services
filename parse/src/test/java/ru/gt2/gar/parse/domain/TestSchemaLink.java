package ru.gt2.gar.parse.domain;

import org.junit.jupiter.api.Test;

import java.lang.reflect.RecordComponent;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSchemaLink {
    private static final Set<Class<? extends Record>> ENTITIES = GarType.getRecordClasses();

    @Test
    public void testLinks() {
        GarType.forEach(null, this::assertLinkTypes);
    }

    private void assertLinkTypes(RecordComponent recordComponent) {
        SchemaLink schemaLink = recordComponent.getAnnotation(SchemaLink.class);
        if (null == schemaLink) {
            return;
        }
        Class<? extends Record> linkClass = schemaLink.value().recordClass;
        assertTrue(ENTITIES.contains(linkClass),
                () ->  "Field " + recordComponent.getName() + " has invalid link type " + linkClass);

        Class<?> idType = linkClass.getRecordComponents()[0].getType();

        boolean classMatch = idType.equals(recordComponent.getType());
        if (!classMatch && idType.isPrimitive()) {
            if (int.class.equals(idType)) {
                classMatch = Integer.class.equals(recordComponent.getType());
            } else if (long.class.equals(idType)) {
                classMatch = Long.class.equals(recordComponent.getType());
            }
        }
        assertTrue(classMatch, () -> "Field " + recordComponent.getName() + " must be of the same type as the link id type");
    }
}
