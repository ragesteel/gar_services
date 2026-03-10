package ru.gt2.gar.db.tm;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.MethodSpec;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javax.lang.model.element.Modifier.PUBLIC;

public class ReadMethodGenerator implements MethodGenerator {
    private final String domainClassName;
    private int columnIndex = 1;
    private final List<String> propertyNames = new ArrayList<>();
    private final List<VariableDecl> variables = new ArrayList<>();
    CodeBlock.Builder body = CodeBlock.builder();

    public ReadMethodGenerator(String domainClassName) {
        this.domainClassName = domainClassName;
    }

    @Override
    public void onColumn(String columnName, String columnComment, String type, boolean primaryKey, boolean nullable) {
        String propertyName = toPropertyName(columnName);
        propertyNames.add(propertyName);

        // TODO #9 Смотреть ещё и на nullable!
        String typeName = switch (type) {
            case "BIGINT" -> "long";
            case "DATE" -> "LocalDate";
            case "BOOLEAN" -> "boolean";
            case "INT" -> "int";
            case String s when s.startsWith("VARCHAR(") -> "String";
            case "UUID" -> "UUID";
            default -> throw new IllegalArgumentException("Unsupported SQL date type: " + type);
        };

        CodeBlock value = CodeBlock.of("rs.get" + switch (type) {
            case "BIGINT" -> "Long($L";
            case "DATE" -> "Object($L, LocalDate.class";
            case "BOOLEAN" -> "Boolean($L";
            case "INT" -> "Int($L";
            case String s when s.startsWith("VARCHAR(") -> "String($L";
            case "UUID" -> "Object($L";
            default -> throw new IllegalArgumentException("Unsupported SQL date type: " + type);
        } + ")", columnIndex++);
        variables.add(new VariableDecl(typeName, propertyName, value));
    }

    @Override
    public MethodSpec generate() {
        MethodSpec.Builder method = MethodSpec.methodBuilder("read")
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .returns(ClassName.get("", domainClassName))
                .addParameter(ClassName.get(ResultSet.class), "rs")
                .addException(ClassName.get(SQLException.class));


        // Объявление переменных: используем $T только для ссылочных типов, иначе $L
        for (VariableDecl var : variables) {
            if (isPrimitive(var.type)) {
                body.add("$L $L = $L;\n", var.type, var.name, var.value);
            } else {
                body.add("$T $L = $L;\n", ClassName.bestGuess(var.type), var.name, var.value);
            }
        }

        // Вызов конструктора record
        String args = String.join(", ", propertyNames);
        body.add("return new $T($L);\n", ClassName.get("", domainClassName), args);

        method.addCode(body.build());
        return method.build();
    }

    private boolean isPrimitive(String type) {
        return switch (type) {
            case "boolean", "int", "long", "short", "byte", "char", "float", "double" -> true;
            default -> false;
        };
    }

    private String toPropertyName(String columnName) {
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

    private static class VariableDecl {
        final String type;
        final String name;
        final CodeBlock value;

        VariableDecl(String type, String name, CodeBlock value) {
            this.type = type;
            this.name = name;
            this.value = value;
        }
    }
}