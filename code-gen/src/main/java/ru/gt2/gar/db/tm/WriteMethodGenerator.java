package ru.gt2.gar.db.tm;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.TypeName;

import java.sql.Date;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class WriteMethodGenerator extends AbstractJdbcMethodGenerator {
    public WriteMethodGenerator(ClassName domainClassName) {
        super("write");
        builder.returns(void.class)
                .addParameter(domainClassName, "source")
                .addParameter(ClassName.get(PreparedStatement.class), "ps");
    }

    @Override
    public void onRecordComponent(String name, Class<?> type, TypeName typeName, String typeSuffix, Integer index, boolean nullable) {
        // Да, тут можно избавить от дублирования части строк, но тогда сильно пострадает читаемость
        if (typeSuffix.isEmpty()) {
            String jdbcType = getJdbcType(type);
            if (jdbcType.isEmpty()) {
                builder.addStatement("ps.setObject($L, source.$L())", index, name);
            } else {
                builder.addStatement("ps.setObject($L, source.$L(), $T.$L)", index, name, JDBCType.class, jdbcType);
            }
        } else {
            if (LocalDate.class.equals(type)) {
                if (nullable) {
                    /*
                    builder.addStatement("""
                        $T $L = source.$L();
                        if ($L == null) {
                            ps.setNull($L, $T.DATE);
                        } else {
                            ps.setDate($L, $T.valueOf($L));
                        }""", type, name, name, name, index, Types.class, index, Date.class, name);
                    */
                    builder.addStatement("""
                        $T $L = source.$L(); ps.setDate($L, ($L == null) ? null : $T.valueOf($L))""",
                            type, name, name, index, name, Date.class, name);
                    // Ещё можно так сделать, но не факт что оно получается более читаемо:
                    // ps.setDate(11, Optional.ofNullable(source.accDate()).map(Date::valueOf).orElse(null));
                } else {
                    builder.addStatement("ps.setDate($L, $T.valueOf(source.$L()))", index, Date.class, name);
                }
            } else {
                builder.addStatement("ps.set$L($L, source.$L())", typeSuffix, index, name);

            }
        }
    }
}