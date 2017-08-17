package com.wolves.zerotoone.orm.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class MyManagedTransaction implements MyTransaction {
	private DataSource dataSource;
	private MyTransactionIsolationLevel level;
	private Connection connection;
	private boolean closeConnection;

	public MyManagedTransaction(Connection connection, boolean closeConnection) {
		this.connection = connection;
		this.closeConnection = closeConnection;
	}

	public MyManagedTransaction(DataSource ds, MyTransactionIsolationLevel level, boolean closeConnection) {
		this.dataSource = ds;
		this.level = level;
		this.closeConnection = closeConnection;
	}

	@Override
	public Connection getConnection() throws SQLException {
		if (this.connection == null) {
			openConnection();
		}
		return this.connection;
	}

	protected void openConnection() throws SQLException {
		this.connection = this.dataSource.getConnection();
		if (this.level != null) {
			this.connection.setTransactionIsolation(this.level.getLevel());
		}
	}

	@Override
	public void commit() throws SQLException {

	}

	@Override
	public void rollback() throws SQLException {

	}

	@Override
	public void close() throws SQLException {
		if (this.closeConnection && this.connection != null) {
			this.connection.close();
		}
	}

	@Override
	public Integer getTimeout() throws SQLException {
		return null;
	}

}
