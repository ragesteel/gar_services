package ru.gt2.rusref.fias;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import ru.gt2.rusref.CsvWriter;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Многопроходный обработчик.
 *
 * Для справочников в которых есть ссылки сами на себя и мы хотим чтобы сначала были записаны
 * данные на которые ссылаются.
 * Предполагается что памяти на все идентификаторы нам хватит.
 */
public class MultiPassProcessor extends SinglePassProcessor {
    private final ImmutableList<Field> selfReferenceFields;
    private final Set<Object> resolved = new HashSet<Object>();
    private int pass;
    private int alreadyWritten;
    private int written;
    private int unresolved;

    public MultiPassProcessor(Fias fias, File[] files, CsvWriter report) {
        super(fias, files, report);
        selfReferenceFields = Fias.getSelfReferenceFields(fias);
    }

    @Override
    protected void processFiles() throws JAXBException, IOException {
        System.out.println("Using multi pass processor");
        while(true) {
            resetCounters();
            System.out.println("Pass #" + pass + " started.");
            super.processFiles();
            System.out.println("Pass #" + pass + " completed. Entities written: " + written + ", unresolved: " + unresolved + ", already written: " + alreadyWritten);
            pass++;
            if (0 == unresolved) {
                break;
            }
            if (0 == written) {
                throw new RuntimeException("Possible circular reference, nothing written in pass");
            }
        }
    }

    @Override
    protected void writeEntity(Object entity) throws Exception {
        ImmutableSet<Object> notNullSelfReferences = fias.getNotNullFieldValues(entity, selfReferenceFields);
        Object pk = fias.idField.get(entity);
        if (resolved.contains(pk)) {
            alreadyWritten++;
            return;
        }

        if (!resolved.containsAll(notNullSelfReferences)) {
            unresolved++;
            return;
        }

        resolved.add(pk);
        super.writeEntity(entity);
        written++;
    }

    private void resetCounters() {
        alreadyWritten = 0;
        written = 0;
        unresolved = 0;
    }

}
