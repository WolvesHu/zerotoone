package com.wolves.zerotoone.orm.builder;

import java.util.Locale;

import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.StatementType;

import com.wolves.zerotoone.orm.mapping.MyConfiguration;
import com.wolves.zerotoone.orm.mapping.MyResultSetType;
import com.wolves.zerotoone.orm.mapping.MyStatementType;
import com.wolves.zerotoone.orm.parse.MyXNode;

public class MyXMLStatementBuilder extends MyBaseBuilder {
	private MyMapperBuilderAssistant builderAssistant;
	private MyXNode context;
	private String requiredDatabaseId;

	public MyXMLStatementBuilder(MyConfiguration configuration, MyMapperBuilderAssistant builderAssistant,
			MyXNode context) {
		this(configuration, builderAssistant, context, null);
	}

	public MyXMLStatementBuilder(MyConfiguration configuration, MyMapperBuilderAssistant builderAssistant,
			MyXNode context, String databaseId) {
		super(configuration);
		this.builderAssistant = builderAssistant;
		this.context = context;
		this.requiredDatabaseId = databaseId;
	}

	public void parseStatementNode() {
		String id = context.getStringAttribute("id");
		String parameterMap = context.getStringAttribute("parameterMap");
		String parameterType = context.getStringAttribute("parameterType");
		Class<?> parameterTypeClass = resolveClass(parameterType);
		String resultMap = context.getStringAttribute("resultMap");
		String resultType = context.getStringAttribute("resultType");

		Class<?> resultTypeClass = resolveClass(resultType);
		String resultSetType = context.getStringAttribute("resultSetType");
		MyStatementType statementType = MyStatementType
				.valueOf(context.getStringAttribute("statementType", MyStatementType.PREPARED.toString()));
		MyResultSetType resultSetTypeEnum = resolveResultSetType(resultSetType);

		String nodeName = context.getNode().getNodeName();
		SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));
		boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
		boolean flushCache = context.getBooleanAttribute("flushCache", !isSelect);
		boolean useCache = context.getBooleanAttribute("useCache", isSelect);
		boolean resultOrdered = context.getBooleanAttribute("resultOrdered", false);

		String resultSets = context.getStringAttribute("resultSets");
		String keyProperty = context.getStringAttribute("keyProperty");
		String keyColumn = context.getStringAttribute("keyColumn");

		builderAssistant.addMappedStatement(id, null, statementType, sqlCommandType, null, null, parameterMap,
				parameterTypeClass, resultMap, resultTypeClass, resultSetTypeEnum, flushCache, useCache, resultOrdered,
				null, keyProperty, keyColumn, null, null, resultSets);
	}

}
