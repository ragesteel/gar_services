package ru.gt2.gar.db.tm;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeName;

import java.sql.SQLException;

import static javax.lang.model.element.Modifier.PUBLIC;

public abstract class AbstractJdbcMethodGenerator implements RecordMethodGenerator {
    protected final MethodSpec.Builder builder;

    public AbstractJdbcMethodGenerator(String methodName) {
        this.builder = MethodSpec.methodBuilder(methodName)
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .addException(SQLException.class);
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

    public static String getJdbcTypeSuffix(Class<?> type) {
        if (type == int.class || type == Integer.class) {
            return "Int";
        } else if (type == long.class || type == Long.class) {
            return "Long";
        } else if (type == double.class || type == Double.class) {
            return "Double";
        } else if (type == float.class || type == Float.class) {
            return "Float";
        } else if (type == boolean.class || type == Boolean.class) {
            return "Boolean";
        } else if (type == String.class) {
            return "String";
        }
        return "Object";
    }

    @Override
    public MethodSpec generate() {
        return builder.build();
    }
}
