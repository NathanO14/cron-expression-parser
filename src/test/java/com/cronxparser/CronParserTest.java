package com.cronxparser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CronParserTest {

    private CronParser cronParser = new CronParser();

    @Test
    public void extractNumberRangeWithRange() {
        String output = cronParser.extractNumberRange("1-5", 1, 7);
        assertEquals("1 2 3 4 5", output);
    }

    @Test
    public void extractNumberRangeWithExceededRange() {
        String output = cronParser.extractNumberRange("1-9", 1, 7);
        assertEquals("1 2 3 4 5 6 7", output);
    }

    @Test
    public void extractNumberRangeWithBelowRange() {
        String output = cronParser.extractNumberRange("0-9", 1, 7);
        assertEquals("1 2 3 4 5 6 7", output);
    }

    @Test
    public void extractNumberRangeWithWildcard() {
        String output = cronParser.extractNumberRange("*", 1, 12);
        assertEquals("1 2 3 4 5 6 7 8 9 10 11 12", output);
    }

    @Test
    public void extractNumberRangeWithSingleNumber() {
        String output = cronParser.extractNumberRange("11", 1, 12);
        assertEquals("11", output);
    }

    @Test
    public void extractNumberRangeWithSingleNumberBelowRange() {
        String output = cronParser.extractNumberRange("0", 1, 12);
        assertEquals("1", output);
    }

    @Test
    public void extractNumberRangeWithSingleNumberExceededRange() {
        String output = cronParser.extractNumberRange("13", 1, 12);
        assertEquals("12", output);
    }

    @Test
    public void extractNumberRangeWithDivider() {
        String output = cronParser.extractNumberRange("*/15", 0, 60);
        assertEquals("0 15 30 45", output);
    }

    @Test
    public void extractNumberRangeWithZeroDivider() {
        String output = cronParser.extractNumberRange("*/0", 1, 10);
        assertEquals("1 2 3 4 5 6 7 8 9 10", output);
    }

    @Test
    public void extractNumberRangeWithDividerGreaterThanMax() {
        String output = cronParser.extractNumberRange("*/90", 0, 60);
        assertEquals("0", output);
    }

    @Test
    public void extractNumberRangeWithComma() {
        String output = cronParser.extractNumberRange("1,15", 0, 60);
        assertEquals("1 15", output);
    }

    @Test
    public void extractNumberRangeWithCommaAndExceededRange() {
        String output = cronParser.extractNumberRange("1,3,4,8", 0, 7);
        assertEquals("1 3 4", output);
    }

    @Test
    public void formattedOutput() {
        String output = cronParser.formattedOutput(
                "0 15 30 45",
                "0",
                "1 15",
                "1 2 3 4 5 6 7 8 9 10 11 12",
                "1 2 3 4 5",
                "/usr/bin/find");
        assertEquals(
                "minute\t\t\t0 15 30 45\n" +
                        "hour\t\t\t0\n" +
                        "dayOfMonth\t\t1 15\n" +
                        "month\t\t\t1 2 3 4 5 6 7 8 9 10 11 12\n" +
                        "day of week\t\t1 2 3 4 5\n" +
                        "command\t\t\t/usr/bin/find",
                output);

    }
}