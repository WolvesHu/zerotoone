package com.wolves.test.zerotoone.java8;

import java.util.ArrayList;
import java.util.List;

public class Java8Test {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("11");
		list.add("22");
		list.add("33");
		list.forEach(System.out::println);
		
		Java8Test test = new Java8Test();
		MathOperation add =  (int a, int b)-> a + b;
		System.out.println(test.operate(1, 2, add));
		MathOperation delete = (a , b) -> a-b;
		System.out.println(test.operate(1, 2, delete));
		MathOperation multiplication = (a , b) -> {return a * b;};
		System.out.println(test.operate(1, 2, multiplication));
		MathOperation devision = (int a, int b) -> a/b;
		System.out.println(test.operate(4, 2, devision));
		
		GreetingService s1 = message -> System.out.println("Hello"+message);
		s1.sayMessage("ddddddddddddddd");
		
	}
	@FunctionalInterface
	interface MathOperation {
		int operation(int a, int b);
	}
	
	interface GreetingService{
		void sayMessage(String message); 
	}
	
	private int operate(int a, int b, MathOperation operation) {
		return operation.operation(a, b);
	}

}
