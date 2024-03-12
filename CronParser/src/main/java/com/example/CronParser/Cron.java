package com.example.CronParser;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Cron {

    private List<Integer> minute;
    private List<Integer> hour;
    private List<Integer> dayOfMonth;
    private List<Integer> month;
    private List<Integer> dayOfWeek;
    String command;

    public Cron(List<Integer> minute, List<Integer> hour, List<Integer> dayOfMonth, List<Integer> month, List<Integer> dayOfWeek, String command) {
        this.minute = minute;
        this.hour = hour;
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.dayOfWeek = dayOfWeek;
        this.command = command;
    }
}
