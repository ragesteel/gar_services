package ru.gt2.gar.db.tm;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeName;

import java.sql.ResultSet;
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
    public void onRecordComponent(String name, Class<?> type, TypeName typeName, String typeSuffix, Integer index) {
        paramNames.add(name);
        if (typeSuffix.isEmpty()) {
            builder.addStatement("$T $L = rs.getObject($L, $T.class)", typeName, name, index, typeName);
        } else {
            builder.addStatement("$T $L = rs.get$L($L)", typeName, name, typeSuffix, index);
        }
    }

    @Override
    protected MethodSpec generate() {
        String args = String.join(", ", paramNames);
        builder.addStatement("return new $T($L)", recordClassName, args);
        return super.generate();
    }
}