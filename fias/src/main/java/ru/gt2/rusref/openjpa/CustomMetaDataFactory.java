package ru.gt2.rusref.openjpa;

import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.persistence.AnnotationPersistenceMetaDataParser;
import org.apache.openjpa.persistence.PersistenceMetaDataFactory;
import ru.gt2.rusref.Description;

import java.lang.annotation.Annotation;

/**
 * Расширение.
 */
public class CustomMetaDataFactory extends PersistenceMetaDataFactory {
    @Override
    protected AnnotationPersistenceMetaDataParser newAnnotationParser() {
        return new AnnotationPersistenceMetaDataParser
                (repos.getConfiguration()) {
            @Override
            protected boolean handleUnknownClassAnnotation(ClassMetaData meta, Annotation anno) {
                if (Description.class.equals(anno.annotationType())) {
                    Description description = (Description) anno;
                    meta.setComments(new String[]{description.value()});
                }
                return super.handleUnknownClassAnnotation(meta, anno);
            }
        };
    }
}
