package com.wolves.test.zerotoone.java8;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Java8StreamOptional {
	public static void main(String[] args) {
		String strA = " abcd ", strB = null;
		print(strA);
		print("");
		print(strB);
		getLength(strA);
		getLength("");
		getLength(strB);
		
		Random r = new Random();
		Supplier<Integer> random = r :: nextInt;
		Stream.generate(random).limit(10).forEach(System.out::println);
		IntStream.generate(()->(int)(System.nanoTime()%100)).limit(10).forEach(System.out::println);
	}

	private static void getLength(String strA) {
		Integer intd = Optional.ofNullable(strA).map(String::length).orElse(-1);
		System.out.println(intd);
	}

	private static void print(String strA) {
		Optional.ofNullable(strA).ifPresent(System.out::print);;
	}
}
