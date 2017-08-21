package com.wolves.zerotoone.orm.builder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.transaction.TransactionFactory;

import com.wolves.zerotoone.orm.datasource.MyDataSourceFactory;
import com.wolves.zerotoone.orm.mapping.MyConfiguration;
import com.wolves.zerotoone.orm.mapping.MyEnvironment;
import com.wolves.zerotoone.orm.parse.MyXNode;
import com.wolves.zerotoone.orm.parse.MyXPathParser;
import com.wolves.zerotoone.orm.transaction.MyTransactionFactory;
import com.wolves.zerotoone.orm.xml.MyXMLMapperEntityResolver;

public class MyXMLConfigBuilder extends MyBaseBuilder {
	private MyXPathParser parser;
	private String environment;

	public MyXMLConfigBuilder(InputStream inputStream, String environment, Properties props) {
		this(new MyXPathParser(inputStream, true, props, new MyXMLMapperEntityResolver()), environment, props);
	}

	private MyXMLConfigBuilder(MyXPathParser parser, String environment, Properties props) {
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

	private void parseConfiguration(MyXNode root) {
		try {
			propertiesElement(root.evalNode("properties"));
			environmentsElement(root.evalNode("environments"));
			mapperElement(root.evalNode("mappers"));
		} catch (Exception e) {
			throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
		}

	}

	private void mapperElement(MyXNode parent) throws IOException {
		if (parent != null) {
			for (MyXNode child : parent.getChildren()) {
				if ("package".equals(child.getName())) {
					String mapperPackage = child.getStringAttribute("name");
					configuration.addMappers(mapperPackage);
				} else {
					String resource = child.getStringAttribute("resource");
					InputStream inputStream = Resources.getResourceAsStream(resource);
					MyXMLMapperBuilder mapperParser = new MyXMLMapperBuilder(inputStream, configuration, resource,
							configuration.getSqlFragments());
					mapperParser.parse();
				}
			}
		}
	}

	private void propertiesElement(MyXNode context) throws IOException {
		if (context != null) {
			Properties defaults = context.getChildrenAsProperties();
			String resource = context.getStringAttribute("resource");
			String url = context.getStringAttribute("url");
			if (resource != null && url != null) {
				throw new BuilderException(
						"The properties element cannot specify both a URL and a resource based property file reference.  Please specify one or the other.");
			}
			if (resource != null) {
				defaults.putAll(Resources.getResourceAsProperties(resource));
			} else if (url != null) {
				defaults.putAll(Resources.getUrlAsProperties(url));
			}
			Properties vars = configuration.getVariables();
			if (vars != null) {
				defaults.putAll(vars);
			}
			parser.setVariables(defaults);
			configuration.setVariables(defaults);
		}
	}

	private void environmentsElement(MyXNode context) throws Exception {
		if (context != null) {
			if (environment == null) {
				environment = context.getStringAttribute("default");
			}
			for (MyXNode child : context.getChildren()) {
				String id = child.getStringAttribute("id");
				if (isSpecifiedEnvironment(id)) {
					MyTransactionFactory txFactory = transactionManagerElement(child.evalNode("transactionManager"));
					MyDataSourceFactory dsFactory = dataSourceElement(child.evalNode("dataSource"));
					DataSource dataSource = dsFactory.getDataSource();
					MyEnvironment.Builder environmentBuilder = new MyEnvironment.Builder(id).dataSource(dataSource).transactionFactory(txFactory);
					configuration.setEnvironment(environmentBuilder.build());
				}
			}
		}
	}

	private MyTransactionFactory transactionManagerElement(MyXNode context) throws Exception {
		if (context != null) {
			String type = context.getStringAttribute("type");
			Properties props = context.getChildrenAsProperties();
			MyTransactionFactory factory = (MyTransactionFactory) resolveClass(type).newInstance();
			factory.setProperties(props);
			return factory;
		}
		throw new BuilderException("Environment declaration requires a TransactionFactory.");
	}

	private MyDataSourceFactory dataSourceElement(MyXNode context) throws Exception {
		if (context != null) {
			String type = context.getStringAttribute("type");
			Properties props = context.getChildrenAsProperties();
			MyDataSourceFactory factory = (MyDataSourceFactory) resolveClass(type).newInstance();
			factory.setProperties(props);
			return factory;
		}
		throw new BuilderException("Environment declaration requires a DataSourceFactory.");
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
