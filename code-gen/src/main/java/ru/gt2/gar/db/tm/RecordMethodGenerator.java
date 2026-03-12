package ru.gt2.gar.db.tm;

import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeName;
import ru.gt2.gar.domain.GarRecord;

public interface RecordMethodGenerator {
    void onRecordComponent(String name, Class<?> type, TypeName typeName, String typeSuffix, Integer index, boolean nullable);

    MethodSpec generate(Class<? extends GarRecord> recordClass);
}
