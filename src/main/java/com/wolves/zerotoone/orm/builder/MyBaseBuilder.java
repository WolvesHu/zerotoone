package com.wolves.zerotoone.orm.builder;

import org.apache.ibatis.type.TypeAliasRegistry;

import com.wolves.zerotoone.orm.mapping.MyConfiguration;

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
}
