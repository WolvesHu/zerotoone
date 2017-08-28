package com.wolves.test.zerotoone.optimize;


public class OptimizeDemo1 {
	public static void main(String[] args) {
		Customer Customer = new Customer("胡王飞");
		Movie movie = new Movie("空中飞人", 2);
		Rental _rental = new Rental(movie,4);
		Customer.addRental(_rental);
		String statement = Customer.statement();
		System.out.println(statement);
	}
}
