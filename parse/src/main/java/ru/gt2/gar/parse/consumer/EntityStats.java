package ru.gt2.gar.parse.consumer;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import ru.gt2.gar.parse.domain.UseOptional;

import java.lang.reflect.RecordComponent;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNullElseGet;

@Slf4j
public class EntityStats<T extends Record> implements Consumer<List<T>> {
    private List<FieldStat> fieldStats;
    @Getter
    private int count;

    @Getter
    private final Stopwatch stopwatch = Stopwatch.createUnstarted();

    @Override
    public void accept(List<T> entities) {
        entities.forEach(this::accept);
        count += entities.size();
    }

    public void accept(T entity) {
        if (null == fieldStats) {
            createFieldStats(entity);
        }
        fieldStats.forEach(fieldStat -> fieldStat.accept(entity));
    }

    public List<FieldStat> getFieldStats() {
        return requireNonNullElseGet(fieldStats, List::of);
    }

    private void createFieldStats(T entity) {
        fieldStats = Arrays.stream(entity.getClass().getRecordComponents())
                .map(EntityStats::createOptionalFieldStat)
                .flatMap(Optional::stream)
                .toList();
        stopwatch.start();
    }

    @VisibleForTesting
    protected static Optional<FieldStat> createOptionalFieldStat(RecordComponent recordComponent) {
        AbstractFieldStat fieldStat = createFieldStat(recordComponent);
        if (null != fieldStat) {
            if (recordComponent.isAnnotationPresent(UseOptional.class)) {
                return Optional.of(new NullableFieldStat(fieldStat));
            }
        }
        return Optional.ofNullable(fieldStat);
    }

    @Nullable
    private static AbstractFieldStat createFieldStat(RecordComponent recordComponent) {
        Class<?> type = recordComponent.getType();
        if (long.class.equals(type) || Long.class.equals(type)) {
            return new LongFieldStat(recordComponent);
        } else if (int.class.equals(type) || Integer.class.equals(type)) {
            return new IntFieldStat(recordComponent);
        } else if (String.class.equals(type)) {
            return new StringFieldStat(recordComponent);
        } else if (boolean.class.equals(type)) {
            return new BoolFieldStat(recordComponent);
        } else if (LocalDate.class.equals(type)) {
            return new DateFieldStat(recordComponent);
        } else if (UUID.class.equals(type)) {
            return new UuidFieldStat(recordComponent);
        } else {
            log.warn("Unsupported type: {}", type);
            return null;
        }
    }
}
