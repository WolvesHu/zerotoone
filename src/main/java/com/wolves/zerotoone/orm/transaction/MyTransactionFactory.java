package com.wolves.zerotoone.orm.transaction;

import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

public interface MyTransactionFactory {
	void setProperties(Properties props);

	MyTransaction newTransaction(Connection conn);

	MyTransaction newTransaction(DataSource dataSource, MyTransactionIsolationLevel level, boolean autoCommit);
}
