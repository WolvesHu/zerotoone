package com.wolves.test.zerotoone.optimize;

import java.util.Enumeration;
import java.util.Vector;

public class Customer {
	private String _name;
	private Vector<Rental> _rentals = new Vector<Rental>();

	public Customer(String _name) {
		this._name = _name;
	}

	public String statement() {
		double totalAmount = 0;
		int frequentRenterPoints = 0;
		Enumeration<Rental> rentals = _rentals.elements();
		String result = "Rental Record for" + get_name() + "\n";
		while (rentals.hasMoreElements()) {
			double thisAmount = 0;
			Rental rental = rentals.nextElement();
			thisAmount = rental.getCharge();
			frequentRenterPoints++;
			if((rental.get_movie().get_priceCode()==Movie.NEW_RELEASE) && rental.get_daysRented()>1) {
				frequentRenterPoints++;
			}
			result += "\t" +rental.get_movie().get_title() + "\t" + String.valueOf(thisAmount)+"\n";
			totalAmount += thisAmount;
		}
		
		result +="Amount owed is " + String.valueOf(totalAmount)+"\n";
		result +="You earned " +String.valueOf(frequentRenterPoints)+" frequent renter points";
		return result;
	}

	

	public void addRental(Rental _rental) {
		_rentals.add(_rental);
	}

	public String get_name() {
		return _name;
	}

}
