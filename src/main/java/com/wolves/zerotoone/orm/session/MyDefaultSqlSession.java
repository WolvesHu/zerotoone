package com.wolves.zerotoone.orm.session;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.wolves.zerotoone.orm.executor.MyExecutor;
import com.wolves.zerotoone.orm.mapping.MyConfiguration;

public class MyDefaultSqlSession implements MySqlSession {
	private MyConfiguration configuration;
	private MyExecutor executor;
	private boolean autoCommit;
	private boolean dirty;

	public MyDefaultSqlSession(MyConfiguration configuration, MyExecutor executor, boolean autoCommit) {
		this.configuration = configuration;
		this.executor = executor;
		this.dirty = false;
		this.autoCommit = autoCommit;
	}

	@Override
	public <T> T selectOne(String statement, Object parameter) {
		List<T> list = this.<T>selectList(statement, parameter, null);
		if (list.size() == 1) {
			return list.get(0);
		} else if (list.size() > 1) {
			throw new TooManyResultsException(
					"Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
		} else {
			return null;
		}
	}

	@Override
	public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
		return null;
	}

	@Override
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
		return null;
	}

	@Override
	public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {

	}

	@Override
	public int insert(String statement, Object parameter) {
		return 0;
	}

	@Override
	public int update(String statement, Object parameter) {
		return 0;
	}

	@Override
	public int delete(String statement, Object parameter) {
		return 0;
	}

	@Override
	public void commit() {

	}

	@Override
	public void rollback() {

	}

	@Override
	public void close() {

	}

	@Override
	public void clearCache() {

	}

	@Override
	public MyConfiguration getConfiguration() {
		return null;
	}

	@Override
	public Connection getConnection() {
		return null;
	}

	@Override
	public <T> T getMapper(Class<T> type) {
		 return configuration.<T>getMapper(type, this);
	}

}
