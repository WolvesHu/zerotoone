package com.wolves.zerotoone.orm.session.factory.impl;

import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;

import com.wolves.zerotoone.orm.executor.MyExecutor;
import com.wolves.zerotoone.orm.mapping.MyConfiguration;
import com.wolves.zerotoone.orm.mapping.MyEnvironment;
import com.wolves.zerotoone.orm.session.MyDefaultSqlSession;
import com.wolves.zerotoone.orm.session.MySqlSession;
import com.wolves.zerotoone.orm.transaction.MyManagedTransactionFactory;
import com.wolves.zerotoone.orm.transaction.MyTransaction;
import com.wolves.zerotoone.orm.transaction.MyTransactionFactory;
import com.wolves.zerotoone.orm.transaction.MyTransactionIsolationLevel;

public class MySessionFactory implements SessionFactory {
	private final MyConfiguration configuration;

	public MySessionFactory(MyConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	public <T> MySqlSession openSession() {
		return openSessionFromDataSource(null, null, false);
	}

	private MySqlSession openSessionFromDataSource(ExecutorType execType, MyTransactionIsolationLevel level,
			boolean autoCommit) {
		// ExecutorType execType = configuration.getDefaultExecutorType();
		MyTransaction tx = null;
		try {
			final MyEnvironment environment = configuration.getEnvironment();
			final MyTransactionFactory transactionFactory = getTransactionFactoryFromEnvironment(environment);
			tx = transactionFactory.newTransaction(environment.getDataSource(), level, autoCommit);
			final MyExecutor executor = configuration.newExecutor(tx, execType);
			return new MyDefaultSqlSession(configuration, executor, autoCommit);
		} catch (Exception e) {
			throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
		} finally {
			ErrorContext.instance().reset();
		}
	}

	private MyTransactionFactory getTransactionFactoryFromEnvironment(MyEnvironment environment) {
		if (environment == null || environment.getTransactionFactory() == null) {
			return new MyManagedTransactionFactory();
		}
		return environment.getTransactionFactory();
	}

}
