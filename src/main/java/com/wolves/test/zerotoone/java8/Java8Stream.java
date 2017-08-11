package com.wolves.test.zerotoone.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Java8Stream {

	public static void main(String[] args) {
		// List<String> strList = Arrays.asList("aaa","ssa","ee","s","sdfsfa");
		// List<String> containA = new ArrayList<String>();
		// for (String str : strList) {
		// if(str.contains("a")) {
		// containA.add(str);
		// }
		// }
		//
		// Collections.sort(containA, new Comparator<String>() {
		// @Override
		// public int compare(String o1, String o2) {
		// return o1.compareTo(o2);
		// }
		// });
		//
		// for (String string : containA) {
		// System.out.println(string);
		// }
		//
		// Comparator<String> comparing =
		//
		// //JAVA8新特性
		// strList.parallelStream().filter(t->t.contains("a")).sorted(comparing(t->
		// t.compareTo(a))).forEach(System.out::println);;

		// 构造流的方法
		// individual values
		Stream<String> stream = Stream.of("a", "b", "c", "c");
		// Arrays
		String[] arr = new String[] { "d", "h", "g", "g" };
		Stream<String> stream2 = Stream.of(arr);
		stream2 = Arrays.stream(arr);
		// Collections
		// stream2 = Arrays.asList(arr).stream();
		// stream.forEach(System.out::print);
		stream2.forEach(System.out::print);

		IntStream.of(1, 2, 3, 4).forEach(System.out::print);
		System.out.println();
		IntStream.range(1, 5).forEach(System.out::print);
		System.out.println();
		IntStream.rangeClosed(1, 5).forEach(System.out::print);
		System.out.println();
		stream.map(String::toUpperCase).collect(Collectors.toList()).forEach(System.out::print);
		System.out.println();
		IntStream.of(1, 2, 3, 4).map(n -> n * 2).forEach(System.out::print);
		System.out.println();
		Stream<List<Integer>> sInt = Stream.of(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6), Arrays.asList(7, 8, 9));
		sInt.flatMap((childList)->childList.stream()).forEach(System.out::print);
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("1", "a");
		map.put("2", "b");
		map.put("3", "c");
		list.add(map);
		Map<String,String> map2 = new HashMap<String,String>();
		map.put("5", "d");
		map.put("6", "e");
		map.put("7", "f");
		list.add(map2);
		Map<String,String> map3 = new HashMap<String,String>();
		map.put("8", "g");
		map.put("9", "h");
		map.put("10", "i");
		list.add(map3);
		
		List<Map<String,String>> list2 = new ArrayList<Map<String,String>>();
		Map<String,String> map4 = new HashMap<String,String>();
		map.put("1", "a");
		map.put("2", "b");
		map.put("3", "c");
		list.add(map4);
		Map<String,String> map5 = new HashMap<String,String>();
		map.put("5", "d");
		map.put("6", "e");
		map.put("7", "f");
		list.add(map5);
		Map<String,String> map6 = new HashMap<String,String>();
		map.put("8", "g");
		map.put("9", "h");
		map.put("10", "i");
		list.add(map6);
		
		List<Map<String,String>> list3 = new ArrayList<Map<String,String>>();
		Map<String,String> map7 = new HashMap<String,String>();
		map.put("1", "a");
		map.put("2", "b");
		map.put("3", "c");
		list.add(map7);
		Map<String,String> map8= new HashMap<String,String>();
		map.put("5", "d");
		map.put("6", "e");
		map.put("7", "f");
		list.add(map8);
		Map<String,String> map9= new HashMap<String,String>();
		map.put("8", "g");
		map.put("9", "h");
		map.put("10", "i");
		list.add(map9);
		System.out.println();
		Stream<List<Map<String,String>>> stream3 = Stream.of(list,list2,list3);
		Stream<Map<String,String>> flatMap = stream3.flatMap(cl->cl.stream());
		
	}

}
