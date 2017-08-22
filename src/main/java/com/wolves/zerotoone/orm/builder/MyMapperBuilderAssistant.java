package com.wolves.zerotoone.orm.builder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.IncompleteElementException;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.wolves.zerotoone.orm.mapping.MyConfiguration;
import com.wolves.zerotoone.orm.mapping.MyMappedStatement;
import com.wolves.zerotoone.orm.mapping.MyParameterMap;
import com.wolves.zerotoone.orm.mapping.MyParameterMapping;
import com.wolves.zerotoone.orm.mapping.MyResultMap;
import com.wolves.zerotoone.orm.mapping.MyResultMapping;
import com.wolves.zerotoone.orm.mapping.MyResultSetType;
import com.wolves.zerotoone.orm.mapping.MySqlSource;
import com.wolves.zerotoone.orm.mapping.MyStatementType;

public class MyMapperBuilderAssistant extends MyBaseBuilder {

	private String currentNamespace;
	private String resource;
	private Cache currentCache;
	private boolean unresolvedCacheRef;

	public MyMapperBuilderAssistant(MyConfiguration configuration, String resource) {
		super(configuration);
		ErrorContext.instance().resource(resource);
		this.resource = resource;
	}

	public String getCurrentNamespace() {
		return currentNamespace;
	}

	public void setCurrentNamespace(String currentNamespace) {
		if (currentNamespace == null) {
			throw new BuilderException("The mapper element requires a namespace attribute to be specified.");
		}

		if (this.currentNamespace != null && !this.currentNamespace.equals(currentNamespace)) {
			throw new BuilderException(
					"Wrong namespace. Expected '" + this.currentNamespace + "' but found '" + currentNamespace + "'.");
		}

		this.currentNamespace = currentNamespace;
	}

	public MyResultMapping buildResultMapping(Class<?> resultType, String property, String column, Class<?> javaType,
			JdbcType jdbcType, String nestedSelect, String nestedResultMap, String notNullColumn, String columnPrefix,
			Class<? extends TypeHandler<?>> typeHandler, List<ResultFlag> flags, String resultSet, String foreignColumn,
			boolean lazy) {
		return new MyResultMapping.Builder(configuration, property, column, null).jdbcType(jdbcType).typeHandler(null)
				.notNullColumns(parseMultipleColumnNames(notNullColumn)).foreignColumn(foreignColumn).lazy(lazy)
				.build();
	}

	private Set<String> parseMultipleColumnNames(String columnName) {
		Set<String> columns = new HashSet<String>();
		if (columnName != null) {
			if (columnName.indexOf(',') > -1) {
				StringTokenizer parser = new StringTokenizer(columnName, "{}, ", false);
				while (parser.hasMoreTokens()) {
					String column = parser.nextToken();
					columns.add(column);
				}
			} else {
				columns.add(columnName);
			}
		}
		return columns;
	}

	public MyResultMap addResultMap(String id, Class<?> type, String extend, Object object,
			List<MyResultMapping> resultMappings, Boolean autoMapping) {
		id = applyCurrentNamespace(id, false);
		MyResultMap resultMap = new MyResultMap.Builder(configuration, id, type, resultMappings, autoMapping).build();
		configuration.addResultMap(resultMap);
		return resultMap;
	}

	public String applyCurrentNamespace(String base, boolean isReference) {
		if (base == null) {
			return null;
		}
		if (isReference) {
			// is it qualified with any namespace yet?
			if (base.contains(".")) {
				return base;
			}
		} else {
			// is it qualified with this namespace yet?
			if (base.startsWith(currentNamespace + ".")) {
				return base;
			}
			if (base.contains(".")) {
				throw new BuilderException("Dots are not allowed in element names, please remove it from " + base);
			}
		}
		return currentNamespace + "." + base;
	}

	public MyMappedStatement addMappedStatement(String id, MySqlSource sqlSource, MyStatementType statementType,
			SqlCommandType sqlCommandType, Integer fetchSize, Integer timeout, String parameterMap,
			Class<?> parameterType, String resultMap, Class<?> resultType, MyResultSetType resultSetType,
			boolean flushCache, boolean useCache, boolean resultOrdered, KeyGenerator keyGenerator, String keyProperty,
			String keyColumn, String databaseId, LanguageDriver lang, String resultSets) {

		if (unresolvedCacheRef) {
			throw new IncompleteElementException("Cache-ref not yet resolved");
		}

		id = applyCurrentNamespace(id, false);
		boolean isSelect = sqlCommandType == SqlCommandType.SELECT;

		MyMappedStatement.Builder statementBuilder = new MyMappedStatement.Builder(configuration, id, sqlSource,
				sqlCommandType).resource(resource).fetchSize(fetchSize).timeout(timeout).statementType(statementType)
						.keyGenerator(keyGenerator).keyProperty(keyProperty).keyColumn(keyColumn).databaseId(databaseId)
						.resultOrdered(resultOrdered).resultSets(resultSets)
						.resultMaps(getStatementResultMaps(resultMap, resultType, id)).resultSetType(resultSetType)
						.flushCacheRequired(valueOrDefault(flushCache, !isSelect))
						.useCache(valueOrDefault(useCache, isSelect)).cache(currentCache);

		MyParameterMap statementParameterMap = getStatementParameterMap(parameterMap, parameterType, id);
		if (statementParameterMap != null) {
			statementBuilder.parameterMap(statementParameterMap);
		}

		MyMappedStatement statement = statementBuilder.build();
		configuration.addMappedStatement(statement);
		return statement;
	}

	private MyParameterMap getStatementParameterMap(String parameterMapName, Class<?> parameterTypeClass,
			String statementId) {
		parameterMapName = applyCurrentNamespace(parameterMapName, true);
		MyParameterMap parameterMap = null;
		if (parameterMapName != null) {
			try {
				parameterMap = configuration.getParameterMap(parameterMapName);
			} catch (IllegalArgumentException e) {
				throw new IncompleteElementException("Could not find parameter map " + parameterMapName, e);
			}
		} else if (parameterTypeClass != null) {
			List<MyParameterMapping> parameterMappings = new ArrayList<MyParameterMapping>();
			parameterMap = new MyParameterMap.Builder(configuration, statementId + "-Inline", parameterTypeClass,
					parameterMappings).build();
		}
		return parameterMap;
	}

	private boolean valueOrDefault(boolean flushCache, boolean b) {
		// TODO Auto-generated method stub
		return false;
	}

	private List<MyResultMap> getStatementResultMaps(String resultMap, Class<?> resultType, String statementId) {
		resultMap = applyCurrentNamespace(resultMap, true);

		List<MyResultMap> resultMaps = new ArrayList<MyResultMap>();
		if (resultMap != null) {
			String[] resultMapNames = resultMap.split(",");
			for (String resultMapName : resultMapNames) {
				try {
					resultMaps.add(configuration.getResultMap(resultMapName.trim()));
				} catch (IllegalArgumentException e) {
					throw new IncompleteElementException("Could not find result map " + resultMapName, e);
				}
			}
		} else if (resultType != null) {
			MyResultMap inlineResultMap = new MyResultMap.Builder(configuration, statementId + "-Inline", resultType,
					new ArrayList<MyResultMapping>(), null).build();
			resultMaps.add(inlineResultMap);
		}
		return resultMaps;
	}

}
