package com.wolves.zerotoone.orm.builder;

import java.io.InputStream;
import java.util.Map;

import com.wolves.zerotoone.orm.mapping.MyConfiguration;
import com.wolves.zerotoone.orm.parse.MyXNode;
import com.wolves.zerotoone.orm.parse.MyXPathParser;
import com.wolves.zerotoone.orm.xml.MyXMLMapperEntityResolver;

public class MyXMLMapperBuilder extends MyBaseBuilder {
	private MyXPathParser parser;
	private MyMapperBuilderAssistant builderAssistant;
	private Map<String, MyXNode> sqlFragments;
	private String resource;

	public MyXMLMapperBuilder(InputStream inputStream, MyConfiguration configuration, String resource,
			Map<String, MyXNode> sqlFragments) {
		super(configuration);
		this.parser = new MyXPathParser(inputStream, true, configuration.getVariables(),
				new MyXMLMapperEntityResolver());
		this.builderAssistant = new MyMapperBuilderAssistant(configuration, resource);
		this.sqlFragments = sqlFragments;
		this.resource = resource;
	}

}
