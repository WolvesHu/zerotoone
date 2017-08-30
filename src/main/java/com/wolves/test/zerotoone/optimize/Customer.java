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
		Enumeration<Rental> rentals = _rentals.elements();
		String result = "Rental Record for " + get_name() + "\n";
		while (rentals.hasMoreElements()) {
			Rental rental = rentals.nextElement();
			result += "\t" +rental.get_movie().get_title() + "\t" + String.valueOf(rental.getCharge())+"\n";
		}
		
		result +="Amount owed is " + String.valueOf(getTotalAmount())+"\n";
		result +="You earned " +String.valueOf(getTotalFrequentRenterPoints())+" frequent renter points";
		return result;
	}

	public String htmlStatement() {
		Enumeration<Rental> rentals = _rentals.elements();
		String result = "<h1>Rental Record for<em> " + get_name() + "</em><h1><p>\n";
		while (rentals.hasMoreElements()) {
			Rental rental = rentals.nextElement();
			result += rental.get_movie().get_title() + ":" + String.valueOf(rental.getCharge())+"<br>\n";
		}
		
		result +="<p>Amount owed is <em>" + String.valueOf(getTotalAmount())+"</em><p>\n";
		result +="You earned <em>" +String.valueOf(getTotalFrequentRenterPoints())+" </em>frequent renter points<p>";
		return result;
		
	}
	private int getTotalFrequentRenterPoints() {
		int result = 0;
		Enumeration<Rental> rentals = _rentals.elements();
		while (rentals.hasMoreElements()) {
			Rental rental = rentals.nextElement();
			result += rental.getFrequentRenterPoints();
		}
		
		return result;
	}

	private double getTotalAmount() {
		double result = 0;
		Enumeration<Rental> rentals = _rentals.elements();
		while (rentals.hasMoreElements()) {
			Rental rental = rentals.nextElement();
			result += rental.getCharge();
		}
		return result;
	}

	public void addRental(Rental _rental) {
		_rentals.add(_rental);
	}
 
	public String get_name() {
		return _name;
	}

}
