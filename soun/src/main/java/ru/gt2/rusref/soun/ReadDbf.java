package ru.gt2.rusref.soun;

import org.jamel.dbf.processor.DbfProcessor;
import org.jamel.dbf.processor.DbfRowMapper;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import static com.google.common.base.CharMatcher.WHITESPACE;
import static java.nio.charset.Charset.forName;

public class ReadDbf {
    private static final Charset WIN1251 = forName("windows-1251");
    private static final Charset IBM866 = forName("IBM866");

    public static void main(String[] args) {
        File dbf = new File("C:/Data/soun/soun_v.dbf");
        List<V> vList = DbfProcessor.loadData(dbf, new DbfRowMapper<V>() {
            @Override
            public V mapRow(Object[] row) {
                String[] stringRow = toStringArray(row);
                int id = Integer.parseInt(stringRow[0]);
                String title = stringRow[1];
                return new V(id, title);
            }
        });
        System.out.println(vList);

        dbf = new File("C:/Data/soun/soun1.dbf");
        List<One> oneList = DbfProcessor.loadData(dbf, new DbfRowMapper<One>() {
            @Override
            public One mapRow(Object[] row) {
                String[] stringRow = toStringArray(row);
                One one = new One(stringRow[0], stringRow[1], stringRow[2], stringRow[3], stringRow[4], stringRow[5],
                        stringRow[6], stringRow[7], stringRow[8], stringRow[9], stringRow[10], stringRow[11],
                        stringRow[12], stringRow[13], stringRow[14], stringRow[15], stringRow[16], stringRow[17],
                        stringRow[18], stringRow[19], stringRow[20], stringRow[21], stringRow[22], stringRow[23]);
                //System.out.println(one);
                return one;
            }
        });
    }

    private static String[] toStringArray(Object[] array) {
        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = WHITESPACE.trimTrailingFrom(new String((byte[]) array[i], IBM866));
        }
        return result;
    }
}
