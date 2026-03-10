package ru.gt2.gar.db.tm;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.MethodSpec;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.RecordComponent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

@RequiredArgsConstructor
public class ReadMethodRCGenerator implements RecordMethodGenerator {
    private final ClassName recordClassName;
    private final List<RecordComponent> components = new ArrayList<>();

    @Override
    public void onRecordComponent(RecordComponent rc) {
        components.add(rc);
    }

    @Override
    public MethodSpec generate() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("readFrom")
                .addModifiers(PUBLIC, STATIC)
                .returns(recordClassName)
                .addParameter(ClassName.get(ResultSet.class), "rs");

        // StringBuilder for constructor args
        StringBuilder newInstance = new StringBuilder("return new " + recordClassName.simpleName() + "(");
        for (int i = 0; i < components.size(); i++) {
            if (i > 0) newInstance.append(", ");
            String colName = components.get(i).getName();
            Class<?> type = components.get(i).getType();
            String getter = getResultSetGetter(type);
            newInstance.append("rs.").append(getter).append("(\"").append(colName).append("\")");
        }
        newInstance.append(");");

        builder.addStatement(newInstance.toString());
        return builder.build();
    }

    private String getResultSetGetter(Class<?> type) {
        if (type == int.class || type == Integer.class) return "getInt";
        if (type == long.class || type == Long.class) return "getLong";
        if (type == double.class || type == Double.class) return "getDouble";
        if (type == float.class || type == Float.class) return "getFloat";
        if (type == boolean.class || type == Boolean.class) return "getBoolean";
        if (type == String.class) return "getString";
        if (type == java.sql.Date.class) return "getDate";
        if (type == java.sql.Timestamp.class) return "getTimestamp";
        return "getObject"; // fallback
    }
}
