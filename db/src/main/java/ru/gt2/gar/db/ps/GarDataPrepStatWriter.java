package ru.gt2.gar.db.ps;

import ru.gt2.gar.db.GarDataWriter;
import ru.gt2.gar.domain.GarType;

import java.util.List;

public class GarDataPrepStatWriter implements GarDataWriter {
    @Override
    public void writeEntities(GarType garType, List<? extends Record> entities) {
        if (entities.isEmpty()) {
            return;
        }

        for (Record entity : entities) {

        }
    }
}
