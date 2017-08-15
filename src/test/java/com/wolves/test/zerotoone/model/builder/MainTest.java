package com.wolves.test.zerotoone.model.builder;

public class MainTest {
	
	public static void main(String[] args) {
		PersonDirector pd = new PersonDirector();
		Person person = pd.constructPerson(new ManBuilder());
		System.out.println(person.getBody());  
        System.out.println(person.getFoot());  
        System.out.println(person.getHead());  
	}
	
}
