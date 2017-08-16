package com.wolves.zerotoone.orm.builder;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.transaction.TransactionFactory;

import com.wolves.zerotoone.orm.datasource.MyDataSourceFactory;
import com.wolves.zerotoone.orm.mapping.MyConfiguration;
import com.wolves.zerotoone.orm.mapping.MyEnvironment;

public class MyXMLConfigBuilder extends MyBaseBuilder {
	private XPathParser parser;
	private String environment;

	private MyXMLConfigBuilder(XPathParser parser, String environment, Properties props) {
		super(new MyConfiguration());
		ErrorContext.instance().resource("SQL Mapper Configuration");
		this.configuration.setVariables(props);
		this.environment = environment;
		this.parser = parser;
	}

	public MyConfiguration parse() {
		parseConfiguration(parser.evalNode("/configuration"));
		return configuration;
	}

	private void parseConfiguration(XNode root) {
		propertiesElement(root.evalNode("properties"));
		environmentsElement(root.evalNode("environments"));
	}

	private void environmentsElement(XNode context) {
		if (context != null) {
			if (environment == null) {
				environment = context.getStringAttribute("default");
			}
			for (XNode child : context.getChildren()) {
				String id = child.getStringAttribute("id");
				if (isSpecifiedEnvironment(id)) {
					  MyDataSourceFactory dsFactory = dataSourceElement(child.evalNode("dataSource"));
			          DataSource dataSource = dsFactory.getDataSource();
			          MyEnvironment.Builder environmentBuilder = new MyEnvironment.Builder(id).dataSource(dataSource);
			          configuration.setEnvironment(environmentBuilder.build());
				}
			}
		}
	}

	private MyDataSourceFactory dataSourceElement(XNode evalNode) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean isSpecifiedEnvironment(String id) {
		if (environment == null) {
			throw new BuilderException("No environment specified.");
		} else if (id == null) {
			throw new BuilderException("Environment requires an id attribute.");
		} else if (environment.equals(id)) {
			return true;
		}
		return false;
	}
}
