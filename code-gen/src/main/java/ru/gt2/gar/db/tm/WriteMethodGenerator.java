package ru.gt2.gar.db.tm;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.MethodSpec;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javax.lang.model.element.Modifier.PUBLIC;

public class WriteMethodGenerator implements MethodGenerator {
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
        List<Object> args = List.of(parameterIndex++, getter, JDBCType.class);
        if (!"DATE".equals(type)) {
            args = args.subList(0, 2);
        }

        CodeBlock statement = CodeBlock.of("ps.set" + switch (type) {
            case "BIGINT" -> "Long($L, $L";
            case "DATE" -> "Object($L, $L, $T.DATE";
            case "BOOLEAN" -> "Boolean($L, $L";
            case "INT" -> "Int($L, $L";
            case String s when s.startsWith("VARCHAR(") -> "String($L, $L";
            case "UUID" -> "Object($L, $L";
            default -> throw new IllegalArgumentException("Unsupported SQL date type: " + type);
        } + ");\n", args.toArray());
        setterStatements.add(statement);
    }

    @Override
    public void onEndTable() {}

    @Override
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