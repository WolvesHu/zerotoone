package com.wolves.test.zerotoone.orm.session;

import java.sql.SQLException;

public interface Session<T> {
	void exec(String sql) throws SQLException;

	T getMapper(Class<T> clazz);
}
