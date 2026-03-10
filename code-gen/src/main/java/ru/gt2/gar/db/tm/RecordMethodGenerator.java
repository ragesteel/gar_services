package ru.gt2.gar.db.tm;

import com.palantir.javapoet.MethodSpec;

import java.lang.reflect.RecordComponent;

public interface RecordMethodGenerator {
    void onRecordComponent(RecordComponent rc);

    MethodSpec generate();
}
