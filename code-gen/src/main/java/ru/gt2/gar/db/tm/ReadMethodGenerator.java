package ru.gt2.gar.db.tm;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeName;

import java.lang.reflect.RecordComponent;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReadMethodGenerator extends AbstractJdbcMethodGenerator {
    private final ClassName recordClassName;
    private final List<String> paramNames = new ArrayList<>();

    public ReadMethodGenerator(ClassName recordClassName) {
        super("read");
        this.recordClassName = recordClassName;
        builder.returns(recordClassName)
                .addParameter(ClassName.get(ResultSet.class), "rs");
    }

    @Override
    public void onRecordComponent(RecordComponent rc) {
        String paramName = rc.getName();
        Class<?> type = rc.getType();
        TypeName typeName = getJavaTypeName(type);
        paramNames.add(paramName);
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
        return super.generate();
    }
}