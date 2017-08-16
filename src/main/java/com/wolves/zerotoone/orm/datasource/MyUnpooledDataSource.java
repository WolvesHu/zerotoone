package com.wolves.zerotoone.orm.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class MyUnpooledDataSource implements DataSource {
	private String driver;
	private String url;
	private String username;
	private String password;
	
	@Override
	public Connection getConnection() throws SQLException {
		 return doGetConnection(username, password);
	}
	
	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return doGetConnection(username, password);
	}
	
	private Connection doGetConnection(String username, String password) throws SQLException {
		Properties props = new Properties();
	    if (username != null) {
	      props.setProperty("user", username);
	    }
	    if (password != null) {
	      props.setProperty("password", password);
	    }
		return doGetConnection(props);
	}
	private Connection doGetConnection(Properties properties) throws SQLException {
		Connection connection = DriverManager.getConnection(url, properties);
		return connection;
	}


	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {

	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {

	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		return null;
	}


	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
