package com.wolves.zerotoone.orm.transaction;

import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

public class MyManagedTransactionFactory implements MyTransactionFactory {
	private boolean closeConnection = true;

	@Override
	public void setProperties(Properties props) {
		if (props != null) {
			String closeConnectionProperty = props.getProperty("closeConnection");
			if (closeConnectionProperty != null) {
				closeConnection = Boolean.valueOf(closeConnectionProperty);
			}
		}
	}

	@Override
	public MyTransaction newTransaction(Connection conn) {
		return new MyManagedTransaction(conn, closeConnection);
	}

	@Override
	public MyTransaction newTransaction(DataSource dataSource, MyTransactionIsolationLevel level, boolean autoCommit) {
		return new MyManagedTransaction(dataSource, level, closeConnection);
	}

}
