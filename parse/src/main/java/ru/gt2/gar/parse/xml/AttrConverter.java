package ru.gt2.gar.parse.xml;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Преобразователи для более правильных преобразований типов, чтобы вместо цифр 0 и 1 получить boolean.
 */
public interface AttrConverter<T> extends Function<Map<String, String>, T> {
    static <T> AttrConverter<T> jackson(Class<T> valueType) {
        ObjectMapper objectMapper = JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                // .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                // или можно .findAndAddModules() и поставить scope=runtime зависимости jackson-datatype-jsr310
                .addModule(new JavaTimeModule())
                .build();

        return map -> objectMapper.convertValue(map, valueType);
    }

    BiFunction<String, String, String> BOOL_ACTUAL_ACTIVE = new BiFunction<>() {
        private static final Set<String> APPLICABLE_NAMES = Set.of("ISACTUAL", "ISACTIVE");
        private static final Map<String, String> BOOLEAN_MAP = Map.of("0", Boolean.FALSE.toString(),
                "1", Boolean.TRUE.toString());

        @Override
        public String apply(String name, String value) {
            if (!APPLICABLE_NAMES.contains(name.toUpperCase())) {
                return value;
            }
            return BOOLEAN_MAP.get(value);
        }
    };

    BiFunction<String, String, String> NOTHING = (n, v) -> v;
}
