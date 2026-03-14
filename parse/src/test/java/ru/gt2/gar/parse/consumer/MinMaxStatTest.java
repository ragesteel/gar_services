package ru.gt2.gar.parse.consumer;

import org.junit.jupiter.api.Test;

import java.util.Formatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinMaxStatTest {
    @Test
    public void testMinMaxStatEmpty() {
        assertMinMaxStat("");
    }

    @Test
    public void testMinMaxStatOne() {
        assertMinMaxStat("int=1", 1);
    }

    @Test
    public void testMinMaxStatOnes() {
        assertMinMaxStat("int=1", 1, 1, 1);
    }
    @Test
    public void testMinMaxStatOneTen() {
        assertMinMaxStat("int 1 … 10", 2, 5, 4, 10, 6, 7, 8, 1, 9, 3);
    }

    private static void assertMinMaxStat(String expected, int... values) {
        MinMaxStat<Integer> stat = new MinMaxStat<>();
        for (int value : values) {
            stat.update(value);
        }

        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        stat.format(formatter, "int=%d", "int %d … %d");
        assertEquals(expected, sb.toString());
    }
}