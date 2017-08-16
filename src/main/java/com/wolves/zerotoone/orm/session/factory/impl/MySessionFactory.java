package com.wolves.zerotoone.orm.session.factory.impl;

import javax.sql.DataSource;

import com.wolves.zerotoone.orm.builder.MyXMLConfigBuilder;
import com.wolves.zerotoone.orm.session.Session;
import com.wolves.zerotoone.orm.session.factory.SessionFactory;
import com.wolves.zerotoone.orm.session.impl.MySession;

public class MySessionFactory implements SessionFactory {
	private DataSource config;
	
	public MySessionFactory(String resource) {
//		config = MyXMLConfigBuilder.build(resource);
	}

	@Override
	public <T> Session<T> openSession() {
		return new MySession<T>(config);
	}

}
