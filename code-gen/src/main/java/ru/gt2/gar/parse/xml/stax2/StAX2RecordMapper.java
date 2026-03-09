package ru.gt2.gar.parse.xml.stax2;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.ParameterizedTypeName;
import com.palantir.javapoet.TypeName;
import com.palantir.javapoet.TypeSpec;
import jakarta.annotation.Nullable;
import org.jspecify.annotations.NonNull;
import ru.gt2.gar.domain.GarRecord;
import ru.gt2.gar.domain.GarType;
import ru.gt2.gar.domain.IntAsBoolean;
import ru.gt2.gar.gen.GenHelper;

import javax.lang.model.element.Modifier;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.lang.reflect.RecordComponent;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.palantir.javapoet.MethodSpec.constructorBuilder;

public class StAX2RecordMapper {
    // Использовать ссылки на классы, вместо текстовых строчек
    private static final ClassName TARGET_CLASS = ClassName.get("ru.gt2.gar.parse.xml.stax2", "GeneratedRecordCreators");
    private static final ClassName TYPED_ATTR_READER = ClassName.get("ru.gt2.gar.parse.xml.stax2", "TypedAttrReader");
    private static final ClassName XML_STREAM_EXCEPTION = ClassName.get(XMLStreamException.class);
    private static final ClassName NON_NULL = ClassName.get(NonNull.class);
    private static final ClassName GAR_TYPE = ClassName.get(GarType.class);
    private static final ClassName XML_STREAM_PROCESSOR = ClassName.get("ru.gt2.gar.parse.xml", "XMLStreamProcessor");
    private static final ClassName STAX2_STREAM_READER_PROCESSOR = ClassName.get("ru.gt2.gar.parse.xml.stax2", "StAX2StreamReaderProcessor");
    private static final ClassName FUNCTION = ClassName.get(Function.class);
    private static final ClassName STREAM = ClassName.get(Stream.class);
    private static final ClassName COLLECTORS = ClassName.get(Collectors.class);
    private static final ClassName XML_INPUT_STREAM2 = ClassName.get("org.codehaus.stax2", "XMLInputFactory2");

    private static final TypeName MAP_GAR_PROCESSOR = ParameterizedTypeName.get(
            ClassName.get("java.util", "Map"), GAR_TYPE, STAX2_STREAM_READER_PROCESSOR);

    static void main() throws IOException {
        new StAX2RecordMapper().generateMapper();
    }

    // Хотел бы я тут использовать существующиую мета-информацию, по которой строится структура таблиц,
    // но по сути там тоже проход по компонентам record'а и чтение тех-же аннотаций
    public void generateMapper() throws IOException {
        // Генерируем класс-обёртку
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(TARGET_CLASS)
                .addModifiers(Modifier.PUBLIC);

        // Приватный конструктор — utility class
        classBuilder.addMethod(constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .build());

        Set<Class<? extends GarRecord>> createdClasses = new HashSet<>();
        MethodSpec.Builder processorsMethod = MethodSpec.methodBuilder("createProcessors")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(MAP_GAR_PROCESSOR)
                .addParameter(XML_INPUT_STREAM2, "xmlInputStream2")
                .addParameter(int.class, "batchSize")
                .addCode(CodeBlock.builder()
                        .add("return $T.of(\n", STREAM)
                        .build());

        boolean first = true;
        for (GarType value : GarType.values()) {
            Class<? extends GarRecord> recordClass = value.recordClass;
            String methodName = "create" + recordClass.getSimpleName();

            processorsMethod
                    .addCode(first ? "        " : ",\n        ")
                    .addCode("new $T($T.$L, xmlInputStream2, batchSize, $T::$L)",
                            STAX2_STREAM_READER_PROCESSOR,
                            GAR_TYPE,
                            value.name(),
                            TARGET_CLASS,
                            methodName);

            if (!createdClasses.add(recordClass)) {
                continue;
            }

            classBuilder.addMethod(generateMapper(methodName, recordClass));
            first = false;
        }

        processorsMethod.addCode(")\n    .collect($T.toMap($T::getGarType, $T.identity()));", COLLECTORS, XML_STREAM_PROCESSOR, FUNCTION);

        classBuilder.addMethod(processorsMethod.build());

        JavaFile javaFile = GenHelper.createJavaFile(getClass(), TARGET_CLASS.packageName(), classBuilder);

        // Вывод в stdout (можно перенаправить в файл)
        javaFile.writeTo(System.out);
    }

    private MethodSpec generateMapper(String methodName, Class<? extends GarRecord> recordClass) {

        // Получаем компоненты record
        RecordComponent[] components = recordClass.getRecordComponents();

        // Строим вызов конструктора record
        CodeBlock.Builder constructorCall = CodeBlock.builder()
                .add("return new $T(\n", recordClass);

        boolean firstField = true;
        for (RecordComponent rc: components) {
            if (firstField) {
                firstField = false;
            } else {
                constructorCall.add(",\n");
            }

            String attrName = rc.getName().toUpperCase(); // По умолчанию: ID → "ID"
            Class<?> type = rc.getType();

            boolean nullable = rc.isAnnotationPresent(Nullable.class);
            String readCode = "get"
                    + (nullable ? "Nullable" : "")
                    + switch (type.getSimpleName()) {
                        case "int", "Integer" -> "Int";
                        case "long", "Long" -> "Long";
                        case "String" -> "String";
                        case "LocalDate" -> "LocalDate";
                        case "boolean" -> rc.isAnnotationPresent(IntAsBoolean.class) ? "IntAsBoolean" : "Boolean";
                        case "UUID" -> "UUID";
                        default -> throw new UnsupportedOperationException("Unsupported type: " + type);
                    };

            constructorCall.add("    $L.$L(\"$L\")", "tar", readCode, attrName);
        }
        constructorCall.add(");");

        // Строим сам метод
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .returns(TypeName.get(recordClass))
                .addParameter(TYPED_ATTR_READER, "tar")
                .addException(XML_STREAM_EXCEPTION)
                .addAnnotation(NON_NULL)
                .addCode(constructorCall.build())
                .build();
    }
}
