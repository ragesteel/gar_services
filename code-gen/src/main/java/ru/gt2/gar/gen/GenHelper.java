package ru.gt2.gar.gen;

import com.palantir.javapoet.*;

import javax.annotation.processing.Generated;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class GenHelper {
    public static String createFileComment(Class<?> generator) {
        return """
                АВТОМАТИЧЕСКИ СГЕНЕРИРОВАННЫЙ ФАЙЛ
                Вам следует изменить класс гененратора (%s) вместо ручного редактирования!"""
                .formatted(generator.getSimpleName());
    }

    public static void createJavaFile(Class<?> generatorClass, String packageName, TypeSpec.Builder javaClassBuilder,
                                      String moduleName) throws IOException {
        String dateTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        TypeSpec javaClass = javaClassBuilder.addAnnotation(
                        AnnotationSpec.builder(Generated.class)
                                .addMember("value", "$S", generatorClass.getName())
                                .addMember("date", "$S", dateTime)
                                .build())
                .build();
        JavaFile javaFile = JavaFile.builder(packageName, javaClass)
                .indent("    ")
                .addFileComment(createFileComment(generatorClass))
                .skipJavaLangImports(true)
                .build();

        javaFile.writeTo(Paths.get(moduleName, "src", "main", "java"));
    }

    public static TypeName getJavaTypeName(Class<?> type) {
        if (!type.isPrimitive()) {
            return ClassName.get(type);
        }
        if (type == int.class) {
            return TypeName.INT;
        } else if (type == long.class) {
            return TypeName.LONG;
        } else if (type == boolean.class) {
            return TypeName.BOOLEAN;
        }
        throw new IllegalArgumentException(
                "Processing for primitive type is not yet implemented" + type);
    }
}
