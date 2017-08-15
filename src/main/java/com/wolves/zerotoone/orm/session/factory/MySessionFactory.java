package com.wolves.zerotoone.orm.session.factory;

import javax.sql.DataSource;

import com.wolves.zerotoone.orm.parse.XMLConfigBuilder;
import com.wolves.zerotoone.orm.session.MySession;
import com.wolves.zerotoone.orm.session.Session;

public class MySessionFactory implements SessionFactory {
	private DataSource config;
	
	public MySessionFactory(String resource) {
		config = XMLConfigBuilder.build(resource);
	}

	@Override
	public <T> Session<T> openSession() {
		return new MySession<T>(config);
	}

}
