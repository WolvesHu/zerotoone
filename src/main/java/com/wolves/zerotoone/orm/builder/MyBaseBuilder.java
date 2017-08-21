package com.wolves.zerotoone.orm.builder;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeAliasRegistry;

import com.wolves.zerotoone.orm.mapping.MyConfiguration;
import com.wolves.zerotoone.orm.mapping.MyResultSetType;

public abstract class MyBaseBuilder {
	protected final MyConfiguration configuration;
	protected final TypeAliasRegistry typeAliasRegistry;

	public MyBaseBuilder(MyConfiguration configuration) {
		this.configuration = configuration;
		this.typeAliasRegistry = this.configuration.getTypeAliasRegistry();
	}

	public MyConfiguration getConfiguration() {
		return configuration;
	}

	protected Class<?> resolveClass(String alias) {
		if (alias == null) {
			return null;
		}
		try {
			return resolveAlias(alias);
		} catch (Exception e) {
			throw new BuilderException("Error resolving class. Cause: " + e, e);
		}
	}

	protected MyResultSetType resolveResultSetType(String alias) {
		if (alias == null) {
			return null;
		}
		try {
			return MyResultSetType.valueOf(alias);
		} catch (IllegalArgumentException e) {
			throw new BuilderException("Error resolving ResultSetType. Cause: " + e, e);
		}
	}

	protected Class<?> resolveAlias(String alias) {
		return typeAliasRegistry.resolveAlias(alias);
	}

	protected JdbcType resolveJdbcType(String alias) {
		if (alias == null) {
			return null;
		}
		try {
			return JdbcType.valueOf(alias);
		} catch (IllegalArgumentException e) {
			throw new BuilderException("Error resolving JdbcType. Cause: " + e, e);
		}
	}
}
