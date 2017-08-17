package com.wolves.zerotoone.orm.session.factory.impl;


import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import com.wolves.zerotoone.orm.mapping.MyConfiguration;
import com.wolves.zerotoone.orm.mapping.MyEnvironment;
import com.wolves.zerotoone.orm.session.MySession;
import com.wolves.zerotoone.orm.session.Session;

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
