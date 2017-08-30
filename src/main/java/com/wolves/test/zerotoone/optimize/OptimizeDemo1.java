package com.wolves.test.zerotoone.optimize;


public class OptimizeDemo1 {
	public static void main(String[] args) {
		Customer customer = new Customer("胡王飞");
		Movie movie = new Movie("空中飞人", 2);
		Rental _rental = new Rental(movie,20);
		Movie movie2 = new Movie("警察故事", 1);
		Rental _rental2 = new Rental(movie2,2);
		customer.addRental(_rental2);
		customer.addRental(_rental);
		String statement = customer.htmlStatement();
		System.out.println(statement);
	}
}
