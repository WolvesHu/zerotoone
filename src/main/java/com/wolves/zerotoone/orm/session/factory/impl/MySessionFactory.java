package com.wolves.zerotoone.orm.session.factory.impl;

import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;

import com.wolves.zerotoone.orm.builder.MyXMLConfigBuilder;
import com.wolves.zerotoone.orm.mapping.MyConfiguration;
import com.wolves.zerotoone.orm.mapping.MyEnvironment;
import com.wolves.zerotoone.orm.session.Session;
import com.wolves.zerotoone.orm.session.factory.SessionFactory;
import com.wolves.zerotoone.orm.session.impl.MySession;

public class MySessionFactory implements SessionFactory {
	private final MyConfiguration configuration;


	public MySessionFactory(MyConfiguration configuration) {
		 this.configuration = configuration;
	}

	@Override
	public <T> Session<T> openSession() {
//		ExecutorType execType = configuration.getDefaultExecutorType();
//		Transaction tx = null;
	    try {
	      final MyEnvironment environment = configuration.getEnvironment();
//	      final Executor executor = configuration.newExecutor(tx, execType);
	      return new MySession<>(environment.getDataSource());
	    } catch (Exception e) {
	    	throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
	    } finally {
	    	ErrorContext.instance().reset();
	    }
	}

}
