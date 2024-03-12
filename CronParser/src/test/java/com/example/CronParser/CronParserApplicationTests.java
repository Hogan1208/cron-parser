package com.example.CronParser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CronParserApplicationTests {

    @Test
    public void testValidCronExpression() {
        String cronStr = "*/15 0 1,15 * 1-5 /usr/bin/find";
        Cron cron = CronParserApplication.parseCron(cronStr);
        assertNotNull(cron);
        assertEquals("0 15 30 45", CronParserApplication.printList(cron.getMinute()));
        assertEquals("0", cron.getHour().get(0).toString());
        assertEquals("1", cron.getDayOfMonth().get(0).toString());
        assertEquals("15", cron.getDayOfMonth().get(1).toString());
        assertEquals("1 2 3 4 5 6 7 8 9 10 11 12", CronParserApplication.printList(cron.getMonth()));
        assertEquals("1 2 3 4 5", CronParserApplication.printList(cron.getDayOfWeek()));
        assertEquals("/usr/bin/find", cron.getCommand());
    }

    @Test
    public void testInvalidCronExpression() {
        String cronStr = "*/15 0 1,15 * 1-5"; // Missing command field
        assertThrows(IllegalArgumentException.class, () -> CronParserApplication.parseCron(cronStr));
    }

    @Test
    public void testParseField() {
        List<Integer> result = CronParserApplication.parseField("1,3,5", 0, 10, "test");
        assertEquals(3, result.size());
        assertTrue(result.contains(1));
        assertTrue(result.contains(3));
        assertTrue(result.contains(5));
    }

    @Test
    public void testValidateRange() {
        // Value within range
        CronParserApplication.validateRange(5, 0, 10, "test");
        // Value out of range
        assertThrows(IllegalArgumentException.class, () -> CronParserApplication.validateRange(15, 0, 10, "test"));
    }

}
