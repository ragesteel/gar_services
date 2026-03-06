package ru.gt2.gar.parse.xml.stax2;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeName;
import com.palantir.javapoet.TypeSpec;
import ru.gt2.gar.domain.GarRecord;
import ru.gt2.gar.domain.GarType;

import javax.lang.model.element.Modifier;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.lang.reflect.RecordComponent;
import java.util.HashSet;
import java.util.Set;

public class StAX2RecordMapper {
    // Использовать ссылки на классы, вместо текстовых строчек
    private static final ClassName TYPED_ATTR_READER = ClassName.get("ru.gt2.gar.parse.xml.stax2", "TypedAttrReader");
    private static final ClassName XML_STREAM_EXCEPTION = ClassName.get(XMLStreamException.class);
    private static final ClassName NON_NULL = ClassName.get("org.jspecify.annotations", "NonNull");

    static void main() throws IOException {
        new StAX2RecordMapper().generateMapper();
    }

    // Хотел бы я тут использовать существующиую мета-информацию, по которой строится структура таблиц,
    // но по сути там тоже проход по компонентам record'а и чтение тех-же аннотаций
    public void generateMapper() throws IOException {

        // Генерируем класс-обёртку
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder("GeneratedRecordCreators")
                .addModifiers(Modifier.PUBLIC);

        Set<Class<? extends GarRecord>> createdClasses = new HashSet<>();
        for (GarType value : GarType.values()) {
            // один класс может быть сразу для нескольких типов - использовать только один!
            Class<? extends GarRecord> recordClass = value.recordClass;
            if (!createdClasses.add(recordClass)) {
                continue;
            }
            classBuilder.addMethod(generateMapper(recordClass));
        }

        JavaFile javaFile = JavaFile.builder("ru.gt2.gar.parse.xml.stax2.mapper", classBuilder.build())
                .addFileComment("Сгенерировано автоматически. Не редактировать.")
                .build();

        // Вывод в stdout (можно перенаправить в файл)
        javaFile.writeTo(System.out);
    }

    private MethodSpec generateMapper(Class<? extends GarRecord> recordClass) {
        String methodName = "create" + recordClass.getSimpleName();

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

            boolean nullable = rc.isAnnotationPresent(jakarta.annotation.Nullable.class);
            String readCode = "get"
                    + (nullable ? "Nullable" : "")
                    + switch (type.getSimpleName()) {
                        case "int", "Integer" -> "Int";
                        case "long", "Long" -> "Long";
                        case "String" -> "String";
                        case "LocalDate" -> "LocalDate";
                        case "boolean" -> "Boolean";
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
