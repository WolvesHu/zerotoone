package com.wolves.zerotoone.orm.session;

import java.io.Closeable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.wolves.zerotoone.orm.mapping.MyConfiguration;

public interface MySqlSession extends Closeable {

	<T> T selectOne(String statement, Object parameter);

	<E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds);

	<K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds);

	void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler);

	int insert(String statement, Object parameter);

	int update(String statement, Object parameter);

	int delete(String statement, Object parameter);

	void commit();

	void rollback();

	@Override
	void close();

	void clearCache();

	MyConfiguration getConfiguration();

	Connection getConnection();

	<T> T getMapper(Class<T> clazz);
}
