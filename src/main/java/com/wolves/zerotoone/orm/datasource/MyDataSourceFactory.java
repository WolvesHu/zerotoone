package com.wolves.zerotoone.orm.datasource;

import java.util.Properties;

import javax.sql.DataSource;

public interface MyDataSourceFactory {
	void setProperties(Properties props);

	DataSource getDataSource();

}
