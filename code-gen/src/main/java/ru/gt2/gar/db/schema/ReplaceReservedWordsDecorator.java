package ru.gt2.gar.db.schema;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import ru.gt2.gar.db.NamingStrategy;

import java.util.Map;

@RequiredArgsConstructor
public class ReplaceReservedWordsDecorator implements NamingStrategy {
    public static final Map<String, String> REPLACEMENTS = ImmutableMap.of(
            "desc", "description",
            "value", "param_value"
    );

    @Delegate
    private final NamingStrategy delegate;

    @Override
    public String getColumnName(String name) {
        String result = delegate.getColumnName(name);
        return REPLACEMENTS.getOrDefault(result, result);
    }
}
