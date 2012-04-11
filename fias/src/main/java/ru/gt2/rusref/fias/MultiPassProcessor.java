package ru.gt2.rusref.fias;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import ru.gt2.rusref.CsvWriter;
import ru.gt2.rusref.stat.ExtractResult;

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
    private boolean writeUnresolved;

    public MultiPassProcessor(Fias fias, File[] files, CsvWriter report) {
        super(fias, files, report);
        selfReferenceFields = Fias.getSelfReferenceFields(fias);

        // FIXME Всё-таки не получилось универасального инструмента. Похожо проблема в том,
        // что в AddressObject'е есть циклические ссылки — prevId/nextId.
        // Видимо остаётся только одну из них убрать, непример nextId, что была возможность вернуться назад.
        // а потом — правильные ссылки мы расставим.
    }

    @Override
    protected void processFiles() throws JAXBException, IOException {
        System.out.println("Using multi pass processor");
        while(true) {
            resetCounters();
            System.out.println("Pass #" + pass + " started.");
            super.processFiles();
            if (writeUnresolved) {
                System.out.println("Pass #" + pass + " completed. Unresolved entries written: " + written);
            } else {
                System.out.println("Pass #" + pass + " completed. Entities written: " + written + ", unresolved: " + unresolved + ", already written: " + alreadyWritten);
            }
            pass++;
            if (0 == unresolved) {
                break;
            }
            if (0 == written) {
                System.out.println("Nothing was written. During next pass all unresolved entities will be written in separate files");
                writeUnresolved = true;
                csv.close();
                File unresolvedCsvFile = new File(fias.item.getSimpleName() + "_unresolved.csv");
                csv = CsvWriter.createMySqlCsvWriter(unresolvedCsvFile);
            }
        }
    }

    @Override
    protected void writeProcessFileReport(ExtractResult extractResult) throws IOException {
        if (0 != pass) {
            return;
        }
        super.writeProcessFileReport(extractResult);
    }

    @Override
    protected void writeEntity(Object entity) throws Exception {
        if (writeUnresolved) {
            super.writeEntity(entity);
            written++;
            return;
        }

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
