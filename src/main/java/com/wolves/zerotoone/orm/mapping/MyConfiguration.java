package com.wolves.zerotoone.orm.mapping;

import java.util.Properties;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.type.TypeAliasRegistry;

import com.wolves.zerotoone.orm.datasource.MyUnpooledDataSourceFactory;

public class MyConfiguration {
	protected MyEnvironment environment;
	protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
	protected Properties variables = new Properties();

	public MyConfiguration() {
		typeAliasRegistry.registerAlias("UNPOOLED", MyUnpooledDataSourceFactory.class);
	}

	public MyConfiguration(MyEnvironment environment) {
		this();
		this.environment = environment;
	}


	public MyEnvironment getEnvironment() {
		return environment;
	}

	public void setEnvironment(MyEnvironment environment) {
		this.environment = environment;
	}

	public TypeAliasRegistry getTypeAliasRegistry() {
		return typeAliasRegistry;
	}

	public Properties getVariables() {
		return variables;
	}

	public void setVariables(Properties variables) {
		this.variables = variables;
	}

}