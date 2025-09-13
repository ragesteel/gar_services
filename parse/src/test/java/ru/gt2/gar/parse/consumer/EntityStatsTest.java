package ru.gt2.gar.parse.consumer;

import org.junit.jupiter.api.Test;
import ru.gt2.gar.domain.House;

import java.lang.reflect.RecordComponent;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntityStatsTest {
    @Test
    public void createOptionalFieldStat() {
        Optional<RecordComponent> optionalHouseNum = findByName("houseNum");
        assertTrue(optionalHouseNum.isPresent());
        RecordComponent houseNum = optionalHouseNum.get();
        Optional<FieldStat> optionalFieldStat = EntityStats.createOptionalFieldStat(houseNum);
        assertTrue(optionalFieldStat.isPresent());
        FieldStat fieldStat = optionalFieldStat.get();
        assertInstanceOf(NullableFieldStat.class, fieldStat);
    }

    @Test
    public void testSum() {
        MinMaxStat<Integer> minMaxStat = new MinMaxStat();
        minMaxStat.update(1);
        minMaxStat.update(10);
        IntFieldStat fieldStat = new IntFieldStat("name", minMaxStat);
        EntityStats entityStats = new EntityStats(List.of(fieldStat), 1, Duration.ofSeconds(1L));

        EntityStats sum = EntityStats.sum(entityStats, entityStats);
        assertEquals(2, sum.getCount());
    }

    private static Optional<RecordComponent> findByName(String name) {
        return Stream.of(House.class.getRecordComponents())
                .filter(rc -> name.equals(rc.getName()))
                .findFirst();
    }
}