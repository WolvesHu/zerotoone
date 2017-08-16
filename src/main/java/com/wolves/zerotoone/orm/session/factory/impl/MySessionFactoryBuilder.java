package com.wolves.zerotoone.orm.session.factory.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;

import com.wolves.zerotoone.orm.builder.MyXMLConfigBuilder;
import com.wolves.zerotoone.orm.mapping.MyConfiguration;
import com.wolves.zerotoone.orm.session.factory.SessionFactory;

public class MySessionFactoryBuilder {
	public SessionFactory build(InputStream inputStream) {
		return build(inputStream, null, null);
	}

	private SessionFactory build(InputStream inputStream, String environment, Properties properties) {
		try {
			MyXMLConfigBuilder parser = new MyXMLConfigBuilder(inputStream, environment, properties);
			return build(parser.parse());
		} catch (Exception e) {
			throw ExceptionFactory.wrapException("Error building SqlSession.", e);
		} finally {
			ErrorContext.instance().reset();
			try {
				inputStream.close();
			} catch (IOException e) {
			}
		}
	}
	public SessionFactory build(MyConfiguration config) {
	    return new MySessionFactory(config);
	  }
}
