package com.wolves.zerotoone.orm.builder;

import org.apache.ibatis.type.TypeAliasRegistry;

import com.wolves.zerotoone.orm.mapping.Configuration;

public abstract class BaseBuilder {
	protected final Configuration configuration;
	protected final TypeAliasRegistry typeAliasRegistry;

	public BaseBuilder(Configuration configuration) {
		this.configuration = configuration;
		this.typeAliasRegistry = this.configuration.getTypeAliasRegistry();
	}

	public Configuration getConfiguration() {
		return configuration;
	}
}
