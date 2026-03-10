package ru.gt2.gar.db.tm;

import com.google.common.collect.ImmutableMap;
import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.FieldSpec;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.ParameterizedTypeName;
import com.palantir.javapoet.TypeSpec;
import com.palantir.javapoet.WildcardTypeName;
import ru.gt2.gar.db.NamingStrategy;
import ru.gt2.gar.db.schema.DatabaseSchema;
import ru.gt2.gar.db.sql.QueriesGenerator;
import ru.gt2.gar.domain.GarRecord;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.gen.GenHelper;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.lang.reflect.RecordComponent;
import java.util.function.Function;

public class TableMappingGenerator {
    private static final String TARGET_PACKAGE = "ru.gt2.gar.db.tm";
    private static final ClassName ABSTRACT_TABLE_MAPPING = ClassName.get(TARGET_PACKAGE, "AbstractTableMapping");
    private static final ClassName TABLE_MAPPING = ClassName.get(TARGET_PACKAGE, "TableMapping");

    static void main() throws IOException {
        new TableMappingGenerator().generate();
    }

    public void generate() throws IOException {
        DatabaseSchema schema = new DatabaseSchema(NamingStrategy.LOWER_UNDERSCORE);

        for (GarType garType : GarType.values()) {
            TypeSpec.Builder tmClass = generateTableMappingClass(garType, schema);
            GenHelper.createJavaFile(getClass(), TARGET_PACKAGE, tmClass, "db");
        }

        // Генерируем TableMappings (фабрику)
        GenHelper.createJavaFile(getClass(), TARGET_PACKAGE, generateMappingsClass(), "db");
    }

    private TypeSpec.Builder generateTableMappingClass(GarType garType, DatabaseSchema schema) {
        String simpleName = garType.recordClass.getSimpleName();
        String className = simpleName + "TM";
        ClassName domainClass = ClassName.get(garType.recordClass);

        QueriesGenerator queryGen = new QueriesGenerator(garType, schema);

        CodeBlock superCall = CodeBlock.of("super($S, $L, $L,\n    $L);",
                "INT",
                multiline(queryGen.getSelect()),
                multiline(queryGen.getInsert()),
                CodeBlock.of(simpleName + "::id")); // TODO заменить на имя первичного ключа

        ClassName primaryKeyType = ClassName.get(Integer.class); // TODO заменить на класс первичного ключа
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className)
                .superclass(ParameterizedTypeName.get(ABSTRACT_TABLE_MAPPING, domainClass, primaryKeyType))
                .addModifiers(Modifier.PUBLIC)
                .addMethod(MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addCode(superCall)
                        .build());

        return classBuilder
                .addMethod(generateMethod(garType, simpleName, schema, WriteMethodGenerator::new))
//                .addMethod(generateMethod(garType, simpleName, schema, ReadMethodGenerator::new));
                .addMethod(generateMethod(garType.recordClass, ReadMethodRCGenerator::new));
    }

    private MethodSpec generateMethod(GarType garType, String simpleName, DatabaseSchema schema,
                                      Function<String, MethodGenerator> generatorSupplier) {
        MethodGenerator generator = generatorSupplier.apply(simpleName);
        schema.visitTable(garType, generator);
        return generator.generate();
    }

    private MethodSpec generateMethod(Class<? extends GarRecord>  recordClass, Function<ClassName, RecordMethodGenerator> generatorSupplier) {
        RecordMethodGenerator generator = generatorSupplier.apply(ClassName.get(recordClass));
        for (RecordComponent recordComponent : recordClass.getRecordComponents()) {
            generator.onRecordComponent(recordComponent);
        }
        return generator.generate();
    }

    private TypeSpec.Builder generateMappingsClass() {
        ClassName garType = ClassName.get(GarType.class);
        ParameterizedTypeName tableMapping = ParameterizedTypeName.get(TABLE_MAPPING,
                WildcardTypeName.supertypeOf(Object.class),
                WildcardTypeName.supertypeOf(Object.class));

        CodeBlock.Builder mapInit = CodeBlock.builder()
                .add("$T.<$T, $T>builder()\n", ImmutableMap.class, garType, tableMapping);

        for (GarType gt : GarType.values()) {
            String tmClassName = gt.recordClass.getSimpleName() + "TM";
            mapInit.add("    .put($T.$L, new $T())\n",
                    garType, gt.name(),
                    ClassName.get(TARGET_PACKAGE, tmClassName));
        }

        mapInit.add("    .build();");

        FieldSpec mapField = FieldSpec.builder(
                        ParameterizedTypeName.get(ClassName.get(ImmutableMap.class), garType, tableMapping),
                        "MAP",
                        Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer(mapInit.build())
                .build();

        MethodSpec getMethod = MethodSpec.methodBuilder("get")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(tableMapping)
                .addParameter(garType, "type")
                .addStatement("return MAP.get(type)")
                .build();

        return TypeSpec.classBuilder("TableMappings")
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc("Фабрика TableMapping. Сгенерировано автоматически.\n")
                .addField(mapField)
                .addMethod(getMethod);
    }

    private CodeBlock multiline(String sql) {
        return CodeBlock.of("\"\"\"\n    $L\"\"\"", sql);
    }
}