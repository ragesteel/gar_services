package ru.gt2.gar.db.tm;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.TypeName;

import java.sql.JDBCType;
import java.sql.PreparedStatement;

public class WriteMethodGenerator extends AbstractJdbcMethodGenerator {
    public WriteMethodGenerator(ClassName domainClassName) {
        super("write");
        builder.returns(void.class)
                .addParameter(domainClassName, "source")
                .addParameter(ClassName.get(PreparedStatement.class), "ps");
    }

    @Override
    public void onRecordComponent(String name, Class<?> type, TypeName typeName, String typeSuffix, Integer index) {
        // Да, тут можно избавить от дублирования части строк, но тогда сильно пострадает читаемость
        if (typeSuffix.isEmpty()) {
            String jdbcType = getJdbcType(type);
            if (jdbcType.isEmpty()) {
                builder.addStatement("ps.setObject($L, source.$L())", index, name);
            } else {
                builder.addStatement("ps.setObject($L, source.$L(), $T.$L)", index, name, JDBCType.class, jdbcType);
            }
        } else {
            builder.addStatement("ps.set$L($L, source.$L())", typeSuffix, index, name);
        }
    }
}