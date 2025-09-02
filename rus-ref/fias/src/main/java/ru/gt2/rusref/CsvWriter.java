package ru.gt2.rusref;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;

/**
 * Писатель в CSV.
 */
public class CsvWriter implements Closeable, Flushable {
    private Writer writer;

    private String lineSeparator;

    private Joiner fieldSeparator;

    private Function<Object, String> fieldTransformer;

    @Override
    public void close() throws IOException {
        writer.close();
    }

    @Override
    public void flush() throws IOException {
        writer.flush();
    }

    public void writeFields(Object... fields) throws IOException {
        String line = fieldSeparator.join(Iterables.transform(Arrays.asList(fields), fieldTransformer));
        writer.write(line);
        writer.write(lineSeparator);
    }

    // mysqlimport  --user=root --fields-terminated-by=\\t --fields-optionally-enclosed-by=\" --fields-escaped-by=\\ --local fias StructureStatus.csv ActualStatus.csv IntervalStatus.csv EstateStatus.csv CenterStatus.csv OperationStatus.csv HouseStateStatus.csv CurrentStatus.csv AddressObjectType.csv NormativeDocument.csv AddressObject.csv Landmark.csv HouseInterval.csv House.csv

    public static CsvWriter createMySqlCsvWriter(File file) throws FileNotFoundException {
        CsvWriter result = new CsvWriter();
        result.writer = new BufferedWriter(
                new OutputStreamWriter(
                new FileOutputStream(file), Charsets.UTF_8));
        result.lineSeparator = "\n";
        result.fieldSeparator = Joiners.TAB_SEPARATED;
        result.fieldTransformer = new MysqlCsvFormatter();

        return result;
    }

}
