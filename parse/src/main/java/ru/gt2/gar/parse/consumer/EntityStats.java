package ru.gt2.gar.parse.consumer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

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

    @Override
    public void accept(List<T> entities) {
        entities.forEach(this::accept);
        count += entities.size();
    }

    public void accept(T entity) {
        if (null == fieldStats) {
            createFieldStats(entity);
        }
        fieldStats.forEach(fieldStat -> {
            fieldStat.accept(entity);
        });
    }

    public List<FieldStat> getFieldStats() {
        return requireNonNullElseGet(fieldStats, List::of);
    }

    private void createFieldStats(T entity) {
        fieldStats = Arrays.stream(entity.getClass().getRecordComponents())
                .map(EntityStats::createFieldStat)
                .flatMap(Optional::stream)
                .toList();
    }

    private static Optional<FieldStat> createFieldStat(RecordComponent recordComponent) {
        Class<?> type = recordComponent.getType();
        if (long.class.equals(type)) {
            return Optional.of(new LongFieldStat(recordComponent));
        } else if (Long.class.equals(type)) {
            return Optional.of(new NullableFieldStat(new LongFieldStat(recordComponent)));
        } else if (int.class.equals(type)) {
            return Optional.of(new IntFieldStat(recordComponent));
        } else if (String.class.equals(type)) {
            return Optional.of(new NullableFieldStat(new StringFieldStat(recordComponent)));
        } else if (boolean.class.equals(type)) {
            return Optional.of(new BoolFieldStat(recordComponent));
        } else if (LocalDate.class.equals(type)) {
            return Optional.of(new DateFieldStat(recordComponent));
        } else if (UUID.class.equals(type)) {
            return Optional.of(new NullableFieldStat(new UuidFieldStat(recordComponent)));
        } else {
            log.warn("Unsupported type: {}", type);
            return Optional.empty();
        }
    }
}
