package com.wolves.zerotoone.orm.mapping;

import javax.sql.DataSource;

public final class MyEnvironment {
	private DataSource dataSource;
	private String id;

	public MyEnvironment(String id, DataSource dataSource) {
		if (id == null) {
			throw new IllegalArgumentException("Parameter 'id' must not be null");
		}
		this.id = id;
		if (dataSource == null) {
			throw new IllegalArgumentException("Parameter 'dataSource' must not be null");
		}
		this.dataSource = dataSource;
	}

	public class Builder {
		private DataSource dataSource;
		private String id;

		public Builder(String id) {
			this.id = id;
		}

		public Builder dataSource(DataSource dataSource) {
			this.dataSource = dataSource;
			return this;
		}

		public MyEnvironment build() {
			return new MyEnvironment(id, dataSource);
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public String getId() {
		return id;
	}

}
