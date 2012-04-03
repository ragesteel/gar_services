package ru.gt2.rusref.stat;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.math.BigInteger;

public class StringFieldStatistics extends ObjectFieldStatistics<String> {
    private int minLen = Integer.MAX_VALUE;
    private int maxLen = Integer.MIN_VALUE;
    private BigInteger sumLen = BigInteger.ZERO;

    @Override
    protected void doUpdateStatistics(String value) {
        super.doUpdateStatistics(value);

        if (null == value) {
            return;
        }

        int length = value.length();
        minLen = Math.min(minLen, length);
        maxLen = Math.max(maxLen, length);
        sumLen = sumLen.add(BigInteger.valueOf(length));
    }

    public StringFieldStatistics(Field field) {
        super(field);
    }

    @Override
    public void print(PrintStream printStream) {
        super.print(printStream);
        if (0 == notNullCount) {
            return;
        }
        printStream.print(", length range = " + minLen + " … " + maxLen);
        printStream.print(", average length = " + getAverage(sumLen));
    }

    @Override
    public void fillReportParts(Object[] parts) {
        super.fillReportParts(parts);
        parts[5] = minLen;
        parts[6] = maxLen;
        parts[7] = getAverage(sumLen);
    }
}
