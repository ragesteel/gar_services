package ru.gt2.gar.db.tm;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeName;

import java.lang.reflect.RecordComponent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.lang.model.element.Modifier.PUBLIC;

public class ReadMethodRCGenerator implements RecordMethodGenerator {
    private final ClassName recordClassName;
    private final MethodSpec.Builder builder;
    private final List<String> paramNames = new ArrayList<>();

    public ReadMethodRCGenerator(ClassName recordClassName) {
        this.recordClassName = recordClassName;
        builder = MethodSpec.methodBuilder("read")
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .returns(recordClassName)
                .addException(SQLException.class)
                .addParameter(ClassName.get(ResultSet.class), "rs");
    }

    @Override
    public void onRecordComponent(RecordComponent rc) {
        String paramName = rc.getName();
        Class<?> type = rc.getType();
        TypeName typeName = getJavaTypeName(type);

        paramNames.add(paramName);
        // Получаем способ чтения из ResultSet
        int parameterIndex = paramNames.size();
        String getterCall;
        if (type == LocalDate.class) {
            getterCall = "Object(" + parameterIndex + ", LocalDate.class";
        } else {
            getterCall = getJdbcTypeSuffix(type) + "(" + parameterIndex;
        }

        builder.addStatement("$T $L = rs.get$L)", typeName, paramName, getterCall);
    }

    @Override
    public MethodSpec generate() {
        String args = String.join(", ", paramNames);
        builder.addStatement("return new $T($L)", recordClassName, args);
        return builder.build();
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
}