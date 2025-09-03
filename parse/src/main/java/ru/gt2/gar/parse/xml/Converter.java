package ru.gt2.gar.parse.xml;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Map;
import java.util.function.Function;

public interface Converter<T> extends Function<Map<String, String>, T> {
    static <T> Converter<T> jackson(Class<T> valueType) {
        ObjectMapper objectMapper = JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                // или можно .findAndAddModules() и поставить scope=runtime зависимости jackson-datatype-jsr310
                .addModule(new JavaTimeModule())
                .build();

        return map -> objectMapper.convertValue(map, valueType);
    }
}
