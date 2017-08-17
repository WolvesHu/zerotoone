package com.wolves.zerotoone.orm.mapping;

import javax.sql.DataSource;

import com.wolves.zerotoone.orm.transaction.MyTransactionFactory;

public class MyEnvironment {
	private final DataSource dataSource;
	private final String id;
	private final MyTransactionFactory transactionFactory;

	public MyEnvironment(String id, DataSource dataSource, MyTransactionFactory transactionFactory) {
		if (id == null) {
			throw new IllegalArgumentException("Parameter 'id' must not be null");
		}
		if (transactionFactory == null) {
			throw new IllegalArgumentException("Parameter 'transactionFactory' must not be null");
		}
		this.id = id;
		if (dataSource == null) {
			throw new IllegalArgumentException("Parameter 'dataSource' must not be null");
		}
		this.dataSource = dataSource;
		this.transactionFactory = transactionFactory;
	}

	public static class Builder {
		private DataSource dataSource;
		private String id;
		private MyTransactionFactory transactionFactory;

		public Builder(String id) {
			this.id = id;
		}

		public Builder transactionFactory(MyTransactionFactory transactionFactory) {
			this.transactionFactory = transactionFactory;
			return this;
		}

		public Builder dataSource(DataSource dataSource) {
			this.dataSource = dataSource;
			return this;
		}

		public MyEnvironment build() {
			return new MyEnvironment(this.id, this.dataSource, this.transactionFactory);
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public MyTransactionFactory getTransactionFactory() {
		return transactionFactory;
	}

	public String getId() {
		return id;
	}

}
