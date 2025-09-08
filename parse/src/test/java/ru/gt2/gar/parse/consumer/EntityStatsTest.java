package ru.gt2.gar.parse.consumer;

import org.junit.jupiter.api.Test;
import ru.gt2.gar.parse.domain.House;

import java.lang.reflect.RecordComponent;
import java.util.Optional;
import java.util.stream.Stream;

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

    private static Optional<RecordComponent> findByName(String name) {
        return Stream.of(House.class.getRecordComponents())
                .filter(rc -> name.equals(rc.getName()))
                .findFirst();
    }
}