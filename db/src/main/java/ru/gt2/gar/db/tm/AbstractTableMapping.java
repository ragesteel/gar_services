package ru.gt2.gar.db.tm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.gt2.gar.domain.GarRecord;

import java.util.function.Function;

@RequiredArgsConstructor
public abstract class AbstractTableMapping<T extends GarRecord, K extends Number>
        implements TableMapping<T, K> {

    @Getter
    private final Function<T, K> primaryKey;
}
