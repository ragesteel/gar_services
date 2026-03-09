package ru.gt2.gar.db.tm;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.MethodSpec;
import ru.gt2.gar.db.schema.TableVisitor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javax.lang.model.element.Modifier.PUBLIC;

public class WriteMethodGenerator implements TableVisitor {
    private final String domainClassName;
    private int parameterIndex = 1;
    private final List<CodeBlock> setterStatements = new ArrayList<>();

    public WriteMethodGenerator(String domainClassName) {
        this.domainClassName = domainClassName;
    }

    @Override
    public void onStartTable(String tableName, String tableComment) {
        parameterIndex = 1;
        setterStatements.clear();
    }

    @Override
    public void onColumn(String columnName, String columnComment, String type, boolean primaryKey, boolean nullable) {
        String getter = "source." + toPropertyName(columnName) + "()";
        CodeBlock statement;
        switch (type) {
            case "DATE":
                statement = CodeBlock.of("ps.setObject($L, $L, JDBCType.DATE);\n", parameterIndex++, getter);
                break;
            case "BOOLEAN":
                statement = CodeBlock.of("ps.setBoolean($L, $L);\n", parameterIndex++, getter);
                break;
            case "INTEGER":
                statement = CodeBlock.of("ps.setInt($L, $L);\n", parameterIndex++, getter);
                break;
            default:
                statement = CodeBlock.of("ps.setString($L, $L);\n", parameterIndex++, getter);
                break;
        }
        setterStatements.add(statement);
    }

    @Override
    public void onEndTable() {}

    public MethodSpec generate() {
        MethodSpec.Builder method = MethodSpec.methodBuilder("write")
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .returns(void.class)
                .addParameter(ClassName.get("", domainClassName), "source")
                .addParameter(ClassName.get(PreparedStatement.class), "ps")
                .addException(ClassName.get(SQLException.class));

        CodeBlock.Builder body = CodeBlock.builder();
        for (CodeBlock stmt : setterStatements) {
            body.add(stmt);
        }
        method.addCode(body.build());

        return method.build();
    }

    private String toPropertyName(String columnName) {
        // Простая конвертация snake_case в camelCase
        StringBuilder sb = new StringBuilder();
        boolean nextUpper = false;
        for (char c : columnName.toCharArray()) {
            if (c == '_') {
                nextUpper = true;
            } else if (nextUpper) {
                sb.append(Character.toUpperCase(c));
                nextUpper = false;
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }
}