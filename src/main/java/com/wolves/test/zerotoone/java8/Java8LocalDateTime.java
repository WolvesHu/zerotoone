package com.wolves.test.zerotoone.java8;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class Java8LocalDateTime {
	public static void main(String[] args) {
		LocalDateTime now = LocalDateTime.now();
		System.out.println(now);
		System.out.println(now.toLocalDate());
		System.out.println(now.toLocalTime());
		System.out.println(now.getMonth());
		System.out.println(now.getMinute());
		System.out.println(now.getSecond());
		System.out.println(LocalTime.parse("12:20:22"));
		LocalDate today = LocalDate.now();
		LocalDate plus = today.plus(2,ChronoUnit.WEEKS);
		System.out.println(Period.between(today, plus));
		
		LocalTime t = LocalTime.now();
		Duration ofHours = Duration.ofHours(6);
		LocalTime plus2 = t.plus(ofHours);
		
		System.out.println(Duration.between(t, plus2));
		System.out.println();
		System.out.println(today.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)));
//		System.out.println(today.with(TemporalAdjusters.);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
	}
}
