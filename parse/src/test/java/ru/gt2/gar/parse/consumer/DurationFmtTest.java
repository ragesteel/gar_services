package ru.gt2.gar.parse.consumer;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DurationFmtTest {

    @Test
    public void testFormat() {
        Duration duration = Duration.ofHours(12).plusMinutes(34).plusSeconds(56);
        assertEquals("12:34:56", DurationFmt.format(duration));
    }
}