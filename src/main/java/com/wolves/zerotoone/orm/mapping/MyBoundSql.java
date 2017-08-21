package com.wolves.zerotoone.orm.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;

public class MyBoundSql {

	private String sql;
	private List<MyParameterMapping> parameterMappings;
	private Object parameterObject;
	private Map<String, Object> additionalParameters;
	private MetaObject metaParameters;

	public MyBoundSql(MyConfiguration configuration, String sql, List<MyParameterMapping> parameterMappings,
			Object parameterObject) {
		this.sql = sql;
		this.parameterMappings = parameterMappings;
		this.parameterObject = parameterObject;
		this.additionalParameters = new HashMap<String, Object>();
		this.metaParameters = configuration.newMetaObject(additionalParameters);
	}

	public String getSql() {
		return sql;
	}

	public List<MyParameterMapping> getParameterMappings() {
		return parameterMappings;
	}

	public Object getParameterObject() {
		return parameterObject;
	}

	public boolean hasAdditionalParameter(String name) {
		String paramName = new PropertyTokenizer(name).getName();
		return additionalParameters.containsKey(paramName);
	}

	public void setAdditionalParameter(String name, Object value) {
		metaParameters.setValue(name, value);
	}

	public Object getAdditionalParameter(String name) {
		return metaParameters.getValue(name);
	}
}
