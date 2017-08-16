package com.wolves.zerotoone.orm.mapping;

import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.type.TypeAliasRegistry;

public class Configuration {
	protected Environment environment;
	protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

	public Configuration() {
		typeAliasRegistry.registerAlias("UNPOOLED", PooledDataSourceFactory.class);
	}

	public Configuration(Environment environment) {
		this();
		this.environment = environment;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public TypeAliasRegistry getTypeAliasRegistry() {
		return typeAliasRegistry;
	}
}
