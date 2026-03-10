package ru.gt2.gar.db.tm;

import com.palantir.javapoet.ClassName;

import java.lang.reflect.RecordComponent;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class WriteMethodGenerator extends AbstractJdbcMethodGenerator {
    private Integer parameterIndex = 1;

    public WriteMethodGenerator(ClassName domainClassName) {
        super("write");
        builder.returns(void.class)
                .addParameter(domainClassName, "source")
                .addParameter(ClassName.get(PreparedStatement.class), "ps");
    }

    @Override
    public void onRecordComponent(RecordComponent rc) {
        String paramName = rc.getName();
        Class<?> type = rc.getType();
        String setterCall;
        List<Object> args = new ArrayList<>(List.of(parameterIndex++, "source." + paramName + "()"));
        if (type == java.time.LocalDate.class) {
            args.add(JDBCType.class);
            setterCall = "Object($L, $L, $T.DATE)";
        } else {
            setterCall = getJdbcTypeSuffix(type) + "($L, $L)";
        }

        builder.addStatement("ps.set" + setterCall, args.toArray());
    }
}