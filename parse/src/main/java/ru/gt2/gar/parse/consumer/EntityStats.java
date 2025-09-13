package ru.gt2.gar.parse.consumer;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.RecordComponent;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElseGet;

@Slf4j
public class EntityStats implements ListConsumer {
    private List<FieldStat> fieldStats;
    @Getter
    private int count;

    private final Stopwatch stopwatch = Stopwatch.createUnstarted();

    private final Duration duration;

    public EntityStats() {
        duration = null;
    }

    @VisibleForTesting
    protected EntityStats(List<FieldStat> fieldStats, int count, Duration duration) {
        this.fieldStats = fieldStats;
        this.count = count;
        this.duration = duration;
    }

    @Override
    public void accept(List<Record> entities) {
        entities.forEach(this::acceptEntity);
        count += entities.size();
    }

    @Override
    public void before() {
        stopwatch.start();
    }

    @Override
    public void after() {
        stopwatch.stop();
    }

    public List<FieldStat> getFieldStats() {
        return requireNonNullElseGet(fieldStats, List::of);
    }

    public Duration getDuration() {
        return requireNonNullElseGet(duration, stopwatch::elapsed);
    }

    public static EntityStats sum(EntityStats first, EntityStats second) {
        requireNonNull(first).ensureFinished();
        requireNonNull(second).ensureFinished();

        if (!first.getFieldNames().equals(second.getFieldNames())) {
            throw new IllegalArgumentException("Fields names are not equals!");
        }

        List<FieldStat> firstFields = first.fieldStats;
        List<FieldStat> resultFieldStats = new ArrayList<>(firstFields.size());
        for (int i = 0; i < firstFields.size(); i++) {
            resultFieldStats.add(firstFields.get(i).sum(second.fieldStats.get(i)));
        }
        return new EntityStats(resultFieldStats, first.count + second.count,
                first.getDuration().plus(second.getDuration()));
    }

    private void ensureFinished() {
        if (null == fieldStats) {
            throw new IllegalStateException("Field stats is not created");
        }
        if (stopwatch.isRunning()) {
            throw new IllegalStateException("Entity stats is running");
        }
    }

    protected void acceptEntity(Record entity) {
        if (null == fieldStats) {
            createFieldStats(entity);
        }
        fieldStats.forEach(fieldStat -> fieldStat.accept(entity));
    }

    private void createFieldStats(Record entity) {
        fieldStats = Arrays.stream(entity.getClass().getRecordComponents())
                .map(EntityStats::createOptionalFieldStat)
                .flatMap(Optional::stream)
                .toList();
    }

    @VisibleForTesting
    protected static Optional<FieldStat> createOptionalFieldStat(RecordComponent recordComponent) {
        AbstractFieldStat fieldStat = createFieldStat(recordComponent);
        if (null != fieldStat) {
            if (recordComponent.isAnnotationPresent(jakarta.annotation.Nullable.class)) {
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

    private List<String> getFieldNames() {
        return fieldStats.stream().map(FieldStat::getName).toList();
    }
}
