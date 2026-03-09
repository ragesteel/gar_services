package ru.gt2.gar.gen;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.TypeSpec;

import javax.annotation.processing.Generated;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class GenHelper {
    public static String createFileComment(Class<?> generator) {
        return """
                АВТОМАТИЧЕСКИ СГЕНЕРИРОВАННЫЙ ФАЙЛ
                Вам следует изменить класс гененратора (%s) вместо ручного редактирования!"""
                .formatted(generator.getSimpleName());
    }

    public static JavaFile createJavaFile(Class<?> generatorClass, String packageName, TypeSpec.Builder javaClassBuilder) {
        String dateTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        TypeSpec javaClass = javaClassBuilder.addAnnotation(
                        AnnotationSpec.builder(Generated.class)
                                .addMember("value", "$S", generatorClass.getName())
                                .addMember("date", "$S", dateTime)
                                .build())
                .build();
        return JavaFile.builder(packageName, javaClass)
                .indent("    ")
                .addFileComment(createFileComment(generatorClass))
                .build();
    }
}
