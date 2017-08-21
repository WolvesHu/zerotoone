package com.wolves.zerotoone.orm.mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.StatementType;

public class MyMappedStatement {
	private String resource;
	private MyConfiguration configuration;
	private String id;
	private Integer fetchSize;
	private Integer timeout;
	private MyStatementType statementType;
	private MyResultSetType resultSetType;
	private MySqlSource sqlSource;
	private Cache cache;
	private MyParameterMap parameterMap;
	private List<MyResultMap> resultMaps;
	private boolean flushCacheRequired;
	private boolean useCache;
	private boolean resultOrdered;
	private SqlCommandType sqlCommandType;
	private KeyGenerator keyGenerator;
	private String[] keyProperties;
	private String[] keyColumns;
	private boolean hasNestedResultMaps;
	private String databaseId;
	private String[] resultSets;

	MyMappedStatement() {
		// constructor disabled
	}

	public static class Builder {
		private MyMappedStatement mappedStatement = new MyMappedStatement();

		public Builder(MyConfiguration configuration, String id, MySqlSource sqlSource, SqlCommandType sqlCommandType) {
			mappedStatement.configuration = configuration;
			mappedStatement.id = id;
			mappedStatement.sqlSource = sqlSource;
			mappedStatement.statementType = MyStatementType.PREPARED;
			mappedStatement.parameterMap = new MyParameterMap.Builder(configuration, "defaultParameterMap", null,
					new ArrayList<MyParameterMapping>()).build();
			mappedStatement.resultMaps = new ArrayList<MyResultMap>();
			mappedStatement.sqlCommandType = sqlCommandType;
			mappedStatement.keyGenerator = configuration.isUseGeneratedKeys()
					&& SqlCommandType.INSERT.equals(sqlCommandType) ? Jdbc3KeyGenerator.INSTANCE
							: NoKeyGenerator.INSTANCE;
			// String logId = id;
			// if (configuration.getLogPrefix() != null) {
			// logId = configuration.getLogPrefix() + id;
			// }
			// mappedStatement.statementLog = LogFactory.getLog(logId);
			// mappedStatement.lang =
			// configuration.getDefaultScriptingLanguageInstance();
		}

		public Builder resource(String resource) {
			mappedStatement.resource = resource;
			return this;
		}

		public String id() {
			return mappedStatement.id;
		}

		public Builder parameterMap(MyParameterMap parameterMap) {
			mappedStatement.parameterMap = parameterMap;
			return this;
		}

		public Builder resultMaps(List<MyResultMap> resultMaps) {
			mappedStatement.resultMaps = resultMaps;
			for (MyResultMap resultMap : resultMaps) {
				mappedStatement.hasNestedResultMaps = mappedStatement.hasNestedResultMaps
						|| resultMap.hasNestedResultMaps();
			}
			return this;
		}

		public Builder fetchSize(Integer fetchSize) {
			mappedStatement.fetchSize = fetchSize;
			return this;
		}

		public Builder timeout(Integer timeout) {
			mappedStatement.timeout = timeout;
			return this;
		}

		public Builder statementType(MyStatementType statementType) {
			mappedStatement.statementType = statementType;
			return this;
		}

		public Builder resultSetType(MyResultSetType resultSetType) {
			mappedStatement.resultSetType = resultSetType;
			return this;
		}

		public Builder cache(Cache cache) {
			mappedStatement.cache = cache;
			return this;
		}

		public Builder flushCacheRequired(boolean flushCacheRequired) {
			mappedStatement.flushCacheRequired = flushCacheRequired;
			return this;
		}

		public Builder useCache(boolean useCache) {
			mappedStatement.useCache = useCache;
			return this;
		}

		public Builder resultOrdered(boolean resultOrdered) {
			mappedStatement.resultOrdered = resultOrdered;
			return this;
		}

		public Builder keyGenerator(KeyGenerator keyGenerator) {
			mappedStatement.keyGenerator = keyGenerator;
			return this;
		}

		public Builder keyProperty(String keyProperty) {
			mappedStatement.keyProperties = delimitedStringToArray(keyProperty);
			return this;
		}

		public Builder keyColumn(String keyColumn) {
			mappedStatement.keyColumns = delimitedStringToArray(keyColumn);
			return this;
		}

		public Builder databaseId(String databaseId) {
			mappedStatement.databaseId = databaseId;
			return this;
		}

		public Builder resultSets(String resultSet) {
			mappedStatement.resultSets = delimitedStringToArray(resultSet);
			return this;
		}

		/** @deprecated Use {@link #resultSets} */
		@Deprecated
		public Builder resulSets(String resultSet) {
			mappedStatement.resultSets = delimitedStringToArray(resultSet);
			return this;
		}

		public MyMappedStatement build() {
			assert mappedStatement.configuration != null;
			assert mappedStatement.id != null;
			assert mappedStatement.sqlSource != null;
			mappedStatement.resultMaps = Collections.unmodifiableList(mappedStatement.resultMaps);
			return mappedStatement;
		}
	}

	public KeyGenerator getKeyGenerator() {
		return keyGenerator;
	}

	public SqlCommandType getSqlCommandType() {
		return sqlCommandType;
	}

	public String getResource() {
		return resource;
	}

	public MyConfiguration getConfiguration() {
		return configuration;
	}

	public String getId() {
		return id;
	}

	public boolean hasNestedResultMaps() {
		return hasNestedResultMaps;
	}

	public Integer getFetchSize() {
		return fetchSize;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public MyStatementType getStatementType() {
		return statementType;
	}

	public MyResultSetType getResultSetType() {
		return resultSetType;
	}

	public MySqlSource getSqlSource() {
		return sqlSource;
	}

	public MyParameterMap getParameterMap() {
		return parameterMap;
	}

	public List<MyResultMap> getResultMaps() {
		return resultMaps;
	}

	public Cache getCache() {
		return cache;
	}

	public boolean isFlushCacheRequired() {
		return flushCacheRequired;
	}

	public boolean isUseCache() {
		return useCache;
	}

	public boolean isResultOrdered() {
		return resultOrdered;
	}

	public String getDatabaseId() {
		return databaseId;
	}

	public String[] getKeyProperties() {
		return keyProperties;
	}

	public String[] getKeyColumns() {
		return keyColumns;
	}

	public String[] getResultSets() {
		return resultSets;
	}

	/** @deprecated Use {@link #getResultSets()} */
	@Deprecated
	public String[] getResulSets() {
		return resultSets;
	}

	public MyBoundSql getBoundSql(Object parameterObject) {
		MyBoundSql boundSql = sqlSource.getBoundSql(parameterObject);
		List<MyParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings == null || parameterMappings.isEmpty()) {
			boundSql = new MyBoundSql(configuration, boundSql.getSql(), parameterMap.getParameterMappings(),
					parameterObject);
		}

		// check for nested result maps in parameter mappings (issue #30)
		for (MyParameterMapping pm : boundSql.getParameterMappings()) {
			String rmId = pm.getResultMapId();
			if (rmId != null) {
				MyResultMap rm = configuration.getResultMap(rmId);
				if (rm != null) {
					hasNestedResultMaps |= rm.hasNestedResultMaps();
				}
			}
		}

		return boundSql;
	}

	private static String[] delimitedStringToArray(String in) {
		if (in == null || in.trim().length() == 0) {
			return null;
		} else {
			return in.split(",");
		}
	}
}
