package ru.gt2.gar.db.tm;

import com.palantir.javapoet.MethodSpec;
import ru.gt2.gar.domain.GarRecord;
import ru.gt2.gar.gen.GenHelper;

import java.lang.reflect.RecordComponent;
import java.sql.SQLException;
import java.time.LocalDate;

import static javax.lang.model.element.Modifier.PUBLIC;

public abstract class AbstractJdbcMethodGenerator implements RecordMethodGenerator {
    protected final MethodSpec.Builder builder;

    public AbstractJdbcMethodGenerator(String methodName) {
        this.builder = MethodSpec.methodBuilder(methodName)
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .addException(SQLException.class);
    }

    public static String getJdbcTypeSuffix(Class<?> type) {
        if (int.class.equals(type)) {
            return "Int";
        } else if (long.class.equals(type)) {
            return "Long";
        } else if (boolean.class.equals(type)) {
            return "Boolean";
        } else if (String.class.equals(type)) {
            return "String";
        } else if (LocalDate.class.equals(type)) {
            return "Date";
        }
        return "";
    }

    public static String getJdbcType(Class<?> type) {
        if (LocalDate.class.equals(type)) {
            return "DATE";
        } else if (Long.class.equals(type)) {
            return "BIGINT";
        } else if (Integer.class.equals(type)) {
            return "INTEGER";
        }
        return "";
    }

    @Override
    public MethodSpec generate(Class<? extends GarRecord> recordClass) {
        int index = 1;
        for (RecordComponent recordComponent : recordClass.getRecordComponents()) {
            Class<?> type = recordComponent.getType();
            onRecordComponent(recordComponent.getName(), type, GenHelper.getJavaTypeName(type), getJdbcTypeSuffix(type), index++);
        }
        return generate();
    }

    protected MethodSpec generate() {
        return builder.build();
    }
}
