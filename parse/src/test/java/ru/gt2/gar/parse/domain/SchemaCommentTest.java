package ru.gt2.gar.parse.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SchemaCommentTest {
    // Убедиться что все сущности и поля имеют комментарии
    @Test
    public void testAllHaveComments() {
        GarType.forEach(
            recordClass -> assertTrue(recordClass.isAnnotationPresent(SchemaComment.class),
                () -> "Class " + recordClass.getName() + " has no SchemaComment annotation"),
            recordComponent -> assertTrue(recordComponent.isAnnotationPresent(SchemaComment.class),
                () -> "Field " + recordComponent.getName() + " has no SchemaComment annotation"));
    }
}
