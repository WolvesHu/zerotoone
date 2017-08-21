package com.wolves.zerotoone.orm.builder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.IncompleteElementException;
import org.apache.ibatis.builder.xml.XMLStatementBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.wolves.zerotoone.orm.mapping.MyConfiguration;
import com.wolves.zerotoone.orm.mapping.MyResultMap;
import com.wolves.zerotoone.orm.mapping.MyResultMapResolver;
import com.wolves.zerotoone.orm.mapping.MyResultMapping;
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

	public void parse() {
		if (!configuration.isResourceLoaded(resource)) {
			configurationElement(parser.evalNode("/mapper"));
			configuration.addLoadedResource(resource);
			bindMapperForNamespace();
		}

		// parsePendingResultMaps();
		// parsePendingCacheRefs();
		// parsePendingStatements();
	}

	private void bindMapperForNamespace() {
		String namespace = builderAssistant.getCurrentNamespace();
		if (namespace != null) {
			Class<?> boundType = null;
			try {
				boundType = Resources.classForName(namespace);
			} catch (ClassNotFoundException e) {
				// ignore, bound type is not required
			}
			if (boundType != null) {
				if (!configuration.hasMapper(boundType)) {
					// Spring may not know the real resource name so we set a
					// flag
					// to prevent loading again this resource from the mapper
					// interface
					// look at MapperAnnotationBuilder#loadXmlResource
					configuration.addLoadedResource("namespace:" + namespace);
					configuration.addMapper(boundType);
				}
			}
		}
	}

	private void configurationElement(MyXNode context) {
		String namespace = context.getStringAttribute("namespace");
		if (namespace == null || namespace.equals("")) {
			throw new BuilderException("Mapper's namespace cannot be empty");
		}
		builderAssistant.setCurrentNamespace(namespace);
		resultMapElements(context.evalNodes("/mapper/resultMap"));
		buildStatementFromContext(context.evalNodes("select|insert|update|delete"));
	}

	private void buildStatementFromContext(List<MyXNode> list) {
		buildStatementFromContext(list, null);
	}

	private void buildStatementFromContext(List<MyXNode> list, String requiredDatabaseId) {
		for (MyXNode context : list) {
			final MyXMLStatementBuilder statementParser = new MyXMLStatementBuilder(configuration, builderAssistant,
					context, requiredDatabaseId);
			try {
				statementParser.parseStatementNode();
			} catch (IncompleteElementException e) {
				configuration.addIncompleteStatement(statementParser);
			}
		}
	}

	private void resultMapElements(List<MyXNode> list) {
		for (MyXNode resultMapNode : list) {
			try {
				resultMapElement(resultMapNode);
			} catch (Exception e) {
				// ignore, it will be retried
			}
		}
	}

	private MyResultMap resultMapElement(MyXNode resultMapNode) throws Exception {
		return resultMapElement(resultMapNode, Collections.<MyResultMapping>emptyList());
	}

	private MyResultMap resultMapElement(MyXNode resultMapNode, List<MyResultMapping> additionalResultMappings)
			throws Exception {

		String id = resultMapNode.getStringAttribute("id", resultMapNode.getValueBasedIdentifier());
		String type = resultMapNode.getStringAttribute("type", resultMapNode.getStringAttribute("ofType",
				resultMapNode.getStringAttribute("resultType", resultMapNode.getStringAttribute("javaType"))));
		List<MyResultMapping> resultMappings = new ArrayList<MyResultMapping>();
		Class<?> typeClass = resolveClass(type);
		resultMappings.addAll(additionalResultMappings);
		List<MyXNode> resultChildren = resultMapNode.getChildren();
		for (MyXNode resultChild : resultChildren) {
			if ("constructor".equals(resultChild.getName())) {
				// processConstructorElement(resultChild, typeClass,
				// resultMappings);
			} else if ("discriminator".equals(resultChild.getName())) {
				// discriminator = processDiscriminatorElement(resultChild,
				// typeClass, resultMappings);
			} else {
				List<ResultFlag> flags = new ArrayList<ResultFlag>();
				if ("id".equals(resultChild.getName())) {
					flags.add(ResultFlag.ID);
				}
				resultMappings.add(buildResultMappingFromContext(resultChild, typeClass, flags));
			}
		}
		MyResultMapResolver resultMapResolver = new MyResultMapResolver(builderAssistant, id, typeClass, null, null,
				resultMappings, true);
		try {
			return resultMapResolver.resolve();
		} catch (IncompleteElementException e) {
			// configuration.addIncompleteResultMap(resultMapResolver);
			throw e;
		}
	}

	private MyResultMapping buildResultMappingFromContext(MyXNode context, Class<?> resultType, List<ResultFlag> flags)
			throws Exception {
		String property;
		if (flags.contains(ResultFlag.CONSTRUCTOR)) {
			property = context.getStringAttribute("name");
		} else {
			property = context.getStringAttribute("property");
		}
		String column = context.getStringAttribute("column");
		String javaType = context.getStringAttribute("javaType");
		String jdbcType = context.getStringAttribute("jdbcType");
		String nestedSelect = context.getStringAttribute("select");
		String nestedResultMap = context.getStringAttribute("resultMap",
				processNestedResultMappings(context, Collections.<MyResultMapping>emptyList()));
		String notNullColumn = context.getStringAttribute("notNullColumn");
		String columnPrefix = context.getStringAttribute("columnPrefix");
		String typeHandler = context.getStringAttribute("typeHandler");
		String resultSet = context.getStringAttribute("resultSet");
		String foreignColumn = context.getStringAttribute("foreignColumn");
		// boolean lazy = "lazy".equals(context.getStringAttribute("fetchType",
		// configuration.isLazyLoadingEnabled() ? "lazy" : "eager"));
		Class<?> javaTypeClass = resolveClass(javaType);
		@SuppressWarnings("unchecked")
		Class<? extends TypeHandler<?>> typeHandlerClass = (Class<? extends TypeHandler<?>>) resolveClass(typeHandler);
		JdbcType jdbcTypeEnum = resolveJdbcType(jdbcType);
		return builderAssistant.buildResultMapping(resultType, property, column, javaTypeClass, jdbcTypeEnum,
				nestedSelect, nestedResultMap, notNullColumn, columnPrefix, typeHandlerClass, flags, resultSet,
				foreignColumn, false);
	}

	private String processNestedResultMappings(MyXNode context, List<MyResultMapping> resultMappings) throws Exception {
		if ("association".equals(context.getName()) || "collection".equals(context.getName())
				|| "case".equals(context.getName())) {
			if (context.getStringAttribute("select") == null) {
				MyResultMap resultMap = resultMapElement(context, resultMappings);
				return resultMap.getId();
			}
		}
		return null;
	}

}
