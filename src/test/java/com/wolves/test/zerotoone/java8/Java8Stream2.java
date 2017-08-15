package com.wolves.test.zerotoone.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Java8Stream2 {
	public static void main(String[] args) {
		  System.out.println("Using java7...");
		  // 统计空字符串的数量
	      List<String> strings = Arrays.asList("efg", "", "abc", "bc", "ghij","", "lmn");
	      System.out.println("List: " +strings);
	      long count = getCountEmptyStringUsingJava7(strings);
	      System.out.println("Empty Strings: " + count);
	      count = getCountLength3UsingJava7(strings);

	      System.out.println("Strings of length 3: " + count);

	      // 消除空字符串
	      List<String> filtered = deleteEmptyStringsUsingJava7(strings);
	      System.out.println("Filtered List: " + filtered);

	      // 消除空字符串，同时使用逗号来连接
	      String mergedString = getMergedStringUsingJava7(strings,", ");
	      System.out.println("Merged String: " + mergedString);
	      List<Integer> numbers = Arrays.asList(2, 3, 3, 2, 5, 2, 7);

	      // 获得不同数字的平方的列表
	      List<Integer> squaresList = getSquares(numbers);
	      System.out.println("Squares List: " + squaresList);
	      List<Integer> integers = Arrays.asList(1,2,13,4,15,6,17,8,19);

	      System.out.println("List: " +integers);
	      System.out.println("Highest number in List : " + getMax(integers));
	      System.out.println("Lowest number in List : " + getMin(integers));
	      System.out.println("Sum of all numbers : " + getSum(integers));
	      System.out.println("Average of all numbers : " + getAverage(integers));


	      // 输出10个随机数
	      System.out.println("Random Numbers: ");
	      Random random = new Random();

	      for(int i=0; i < 10; i++){
	         System.out.println(random.nextInt());
	      }
//	      count = 
	      System.out.println("Empty Strings: " + count);

	      System.out.println("Strings of length 3: " + count);

	      // 消除空字符串
	      System.out.println("Filtered List: " + filtered);

	      // 消除空字符串，同时使用逗号来连接
	      System.out.println("Merged String: " + mergedString);

	      // 获得不同数字的平方的列表
	      System.out.println("Squares List: " + squaresList);

	      System.out.println("List: " +integers);
	      System.out.println("Highest number in List : " + getMax(integers));
	      System.out.println("Lowest number in List : " + getMin(integers));
	      System.out.println("Sum of all numbers : " + getSum(integers));
	      System.out.println("Average of all numbers : " + getAverage(integers));


	      // 输出10个随机数
	      System.out.println("Random Numbers: ");
	         System.out.println(random.nextInt());
	}

	private static long getCountEmptyStringUsingJava7(List<String> strings) {
		  int count = 0;
	      for(String string: strings){

	         if(string.isEmpty()){
	            count++;
	         }
	      }
	      return count;
	}
	
	// 使用Java 7版本就提供的API来计算长度为3字符的字符串数量
	   private static int getCountLength3UsingJava7(List<String> strings){
	      int count = 0;

	      for(String string: strings){

	         if(string.length() == 3){
	            count++;
	         }
	      }
	      return count;
	   }

	   // 使用Java 7版本就提供的API来删除空串
	   private static List<String> deleteEmptyStringsUsingJava7(List<String> strings){
	      List<String> filteredList = new ArrayList<String>();

	      for(String string: strings){

	         if(!string.isEmpty()){
	             filteredList.add(string);
	         }
	      }
	      return filteredList;
	   }

	   // 使用Java 7版本就提供的API来获取合并后的字符串
	   private static String getMergedStringUsingJava7(List<String> strings, String separator){
	      StringBuilder stringBuilder = new StringBuilder();

	      for(String string: strings){

	         if(!string.isEmpty()){
	            stringBuilder.append(string);
	            stringBuilder.append(separator);
	         }
	      }
	      String mergedString = stringBuilder.toString();
	      return mergedString.substring(0, mergedString.length()-2);
	   }


	   // 自定义的用于计算数字的平方的方法
	   private static List<Integer> getSquares(List<Integer> numbers){
	      List<Integer> squaresList = new ArrayList<Integer>();

	      for(Integer number: numbers){
	         Integer square = new Integer(number.intValue() * number.intValue());

	         if(!squaresList.contains(square)){
	            squaresList.add(square);
	         }
	      }
	      return squaresList;
	   }

	   // 自定义的用于获得List中最大值的方法
	   private static int getMax(List<Integer> numbers){
	      int max = numbers.get(0);

	      for(int i=1;i < numbers.size();i++){

	         Integer number = numbers.get(i);

	         if(number.intValue() > max){
	            max = number.intValue();
	         }
	      }
	      return max;
	   }

	   // 自定义的用于获得List中最小值的方法
	   private static int getMin(List<Integer> numbers){
	      int min = numbers.get(0);

	      for(int i=1;i < numbers.size();i++){
	         Integer number = numbers.get(i);

	         if(number.intValue() < min){
	            min = number.intValue();
	         }
	      }
	      return min;
	   }

	   // 自定义的用于获得List中各个数字的和的方法
	   private static int getSum(List<Integer> numbers){
	      int sum = (int)(numbers.get(0));

	      for(int i=1;i < numbers.size();i++){
	         sum += (int)numbers.get(i);
	      }
	      return sum;
	   }

	   // 自定义的用于获得List中各个数字的平均值的方法
	   private static int getAverage(List<Integer> numbers){
	      return getSum(numbers) / numbers.size();
	   }
}
