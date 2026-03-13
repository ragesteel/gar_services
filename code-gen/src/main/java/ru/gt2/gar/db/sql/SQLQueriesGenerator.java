package ru.gt2.gar.db.sql;

import com.google.common.collect.ImmutableMap;
import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.FieldSpec;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.ParameterizedTypeName;
import com.palantir.javapoet.TypeSpec;
import ru.gt2.gar.db.NamingStrategy;
import ru.gt2.gar.db.schema.DatabaseSchema;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.gen.GenHelper;

import javax.lang.model.element.Modifier;
import java.io.IOException;

public class SQLQueriesGenerator {
    private static final String TARGET_PACKAGE_NAME = "ru.gt2.gar.db.ps";
    private static final ClassName TARGET_CLASS = ClassName.get(TARGET_PACKAGE_NAME, "SQLQueries");
    private static final ClassName GENERATED_SQL = ClassName.get(TARGET_PACKAGE_NAME, "GeneratedSQL");
    private static final ClassName GAR_TYPE = ClassName.get(GarType.class);

    static void main() throws IOException {
        new SQLQueriesGenerator().generate();
    }

    public void generate() throws IOException {
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(TARGET_CLASS)
                .addModifiers(Modifier.PUBLIC);

        CodeBlock.Builder mapInit = CodeBlock.builder()
                .add("$T.<$T, $T>builder()\n", ImmutableMap.class, GAR_TYPE, GENERATED_SQL);

        DatabaseSchema schema = new DatabaseSchema(NamingStrategy.LOWER_UNDERSCORE);

        for (GarType garType : GarType.values()) {
            QueriesGenerator queryGen = new QueriesGenerator(garType, schema);

            mapInit.add("            .put($T.$L, new $T(\"$L\", $L, $L))\n",
                    GAR_TYPE, garType.name(),
                    GENERATED_SQL,
                    queryGen.getIdColumnType(),
                    textBlock(queryGen.getSelect()),
                    textBlock(queryGen.getInsert()));
        }

        mapInit.add("    .build();");
        FieldSpec mapField = FieldSpec.builder(
                        ParameterizedTypeName.get(ClassName.get(ImmutableMap.class), GAR_TYPE, GENERATED_SQL),
                        "MAP",
                        Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer(mapInit.build())
                .build();

        classBuilder.addField(mapField);

        MethodSpec getMethod = MethodSpec.methodBuilder("get")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(GENERATED_SQL)
                .addParameter(GAR_TYPE, "garType")
                .addStatement("return MAP.get(garType)")
                .build();

        classBuilder.addMethod(getMethod);

        GenHelper.createJavaFile(getClass(), TARGET_PACKAGE_NAME, classBuilder, "db");
    }

    private String textBlock(String s) {
        return "\"\"\"\n                    " + s + "\"\"\"";
    }
}