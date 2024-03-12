package com.example.CronParser;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class CronParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(CronParserApplication.class, args);

		String cronStr = args[0];
		try {
			Cron cron = parseCron(cronStr);
			printCron(cron);
		} catch (IllegalArgumentException e) {
			log.error("Error: " + e.getMessage());
			System.exit(1);
		}
	}

	public static Cron parseCron(String cronStr) {
		String[] cronFields = cronStr.split(" ");
		if (cronFields.length != 6) {
			throw new IllegalArgumentException("String is invalid");
		}

		List<Integer> minute = parseField(cronFields[0], 0, 59, "minute");
		List<Integer> hour = parseField(cronFields[1], 0, 23, "hour");
		List<Integer> dayOfMonth = parseField(cronFields[2], 1, 31, "day of month");
		List<Integer> month = parseField(cronFields[3], 1, 12, "month");
		List<Integer> dayOfWeek = parseField(cronFields[4], 0, 7, "day of week");

		return new Cron(minute, hour, dayOfMonth, month, dayOfWeek, cronFields[5]);
	}

	public static List<Integer> parseField(String field, int minVal, int maxVal, String fieldName) {
		List<Integer> expanded = new ArrayList<>();
		if (field.equals("*")) {
			for (int i = minVal; i <= maxVal; i++) {
				expanded.add(i);
			}
		} else if (field.contains(",")) {
			String[] parts = field.split(",");
			for (String part : parts) {
				int value = Integer.parseInt(part);
				validateRange(value, minVal, maxVal, fieldName);
				expanded.add(value);
			}
		} else if (field.contains("-")) {
			String[] parts = field.split("-");
			int start = Integer.parseInt(parts[0]);
			int end = Integer.parseInt(parts[1]);
			for (int i = start; i <= end; i++) {
				validateRange(i, minVal, maxVal, fieldName);
				expanded.add(i);
			}
		} else if (field.contains("/")) {
			String[] parts = field.split("/");
			int step = Integer.parseInt(parts[1]);
			for (int i = minVal; i <= maxVal; i += step) {
				validateRange(i, minVal, maxVal, fieldName);
				expanded.add(i);
			}
		} else {
			int value = Integer.parseInt(field);
			validateRange(value, minVal, maxVal, fieldName);
			expanded.add(value);
		}
		return expanded;
	}

	private static void printCron(Cron cron) {
		System.out.printf("%-14s %s%n", "minute", printList(cron.getMinute()));
		System.out.printf("%-14s %s%n", "hour", printList(cron.getHour()));
		System.out.printf("%-14s %s%n", "day of month", printList(cron.getDayOfMonth()));
		System.out.printf("%-14s %s%n", "month", printList(cron.getMonth()));
		System.out.printf("%-14s %s%n", "day of week", printList(cron.getDayOfWeek()));
		System.out.printf("%-14s %s%n", "command", cron.command);
	}

	public static String printList(List<Integer> list) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				sb.append(" ");
			}
			sb.append(list.get(i));
		}
		return sb.toString();
	}

	public static void validateRange(int value, int minVal, int maxVal, String fieldName) {
		if (value < minVal || value > maxVal) {
			throw new IllegalArgumentException("Invalid value in field '" + fieldName + "': " + value);
		}
	}

}
