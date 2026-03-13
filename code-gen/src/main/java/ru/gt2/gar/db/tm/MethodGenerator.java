package ru.gt2.gar.db.tm;

import com.palantir.javapoet.MethodSpec;
import ru.gt2.gar.db.schema.TableVisitor;

public interface MethodGenerator extends TableVisitor {
    MethodSpec generate();
}
