package com.wolves.test.zerotoone.model.builder;

public class PersonDirector {
	
	public Person constructPerson(PersonBuilder pb) {
		pb.buildBody();
		pb.buildFoot();
		pb.buildHead();
		return pb.buildPerson();
	}
	
}
