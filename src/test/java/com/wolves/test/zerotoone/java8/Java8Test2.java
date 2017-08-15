package com.wolves.test.zerotoone.java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Java8Test2 {
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7);
		System.out.println("All of the numbers:");
		eval(list, n->true);
		System.out.println("Even numbers");
		eval(list, n->n%2==0);
	}
	
	public static void eval(List<Integer> list, Predicate<Integer> predicate) {
		for (Integer n : list) {
			if(predicate.test(n)) {
				System.out.println(n);
			}
		}
	}
}
