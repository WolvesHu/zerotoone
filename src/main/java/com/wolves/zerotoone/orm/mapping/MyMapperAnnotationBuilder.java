package com.wolves.zerotoone.orm.mapping;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.session.Configuration;

public class MyMapperAnnotationBuilder {
	private Configuration configuration;
	private MapperBuilderAssistant assistant;
	private Class<?> type;

	public MyMapperAnnotationBuilder(MyConfiguration config, Class<?> type) {
	}

	public void parse() {

	}

}
