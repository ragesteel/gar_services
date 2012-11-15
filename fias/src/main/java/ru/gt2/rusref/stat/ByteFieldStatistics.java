package ru.gt2.rusref.stat;

import ru.gt2.rusref.FieldType;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.math.BigInteger;

public class ByteFieldStatistics extends ObjectFieldStatistics<Byte> {
    private byte min = Byte.MIN_VALUE;
    private byte max = Byte.MAX_VALUE;
    private int sum = 0;

    @Override
    protected void doUpdateStatistics(Byte value) {
        super.doUpdateStatistics(value);

        if (null == value) {
            return;
        }

        min = (byte) Math.min(min, (int) value);
        max = (byte) Math.max(max, (int) value);
        sum += value;
    }

    public ByteFieldStatistics(Field field, FieldType fieldType) {
        super(field, fieldType);
    }

    @Override
    public void print(PrintStream printStream) {
        super.print(printStream);
        if (0 == notNullCount) {
            return;
        }
        printStream.print(", range = " + min + " … " + max);
        printStream.print(", average = " + getAverage(BigInteger.valueOf(sum)));
    }

    @Override
    public void fillReportParts(Object[] parts) {
        super.fillReportParts(parts);
        fillRanges(parts, min, max, BigInteger.valueOf(sum));
    }
}
