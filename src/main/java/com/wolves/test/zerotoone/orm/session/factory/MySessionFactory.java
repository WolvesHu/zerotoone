package com.wolves.test.zerotoone.orm.session.factory;

import javax.sql.DataSource;

import com.wolves.test.zerotoone.orm.parse.XMLConfigBuilder;
import com.wolves.test.zerotoone.orm.session.MySession;
import com.wolves.test.zerotoone.orm.session.Session;

public class MySessionFactory implements SessionFactory {
	private DataSource config;
	
	public MySessionFactory(String resource) {
		config = XMLConfigBuilder.build(resource);
	}

	@Override
	public Session openSession() {
		return new MySession(config);
	}

}
