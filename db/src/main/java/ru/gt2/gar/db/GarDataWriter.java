package ru.gt2.gar.db;

import ru.gt2.gar.domain.GarType;

import java.util.List;

public interface GarDataWriter {
    void writeEntities(GarType garType, List<? extends Record> entities);
}
