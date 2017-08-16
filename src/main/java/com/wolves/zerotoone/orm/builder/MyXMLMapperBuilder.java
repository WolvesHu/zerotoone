package com.wolves.zerotoone.orm.builder;

import java.io.InputStream;

import javax.sql.DataSource;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.wolves.zerotoone.orm.datasource.MyDataSourceFactory;

public class MyXMLMapperBuilder {
	private static ClassLoader loader = ClassLoader.getSystemClassLoader();

	public static DataSource build(String resource) {
		try {
			InputStream stream = loader.getResourceAsStream(resource);
			SAXReader reader = new SAXReader();
			Document document = reader.read(stream);
			Element root = document.getRootElement();
			// Element代入，生成DataSource
			return evalDataSource(root);
		} catch (Exception e) {
			throw new RuntimeException("error occured while evaling xml " + resource);
		}
	}

	private static DataSource evalDataSource(Element node) throws ClassNotFoundException {
		if (!node.getName().equals("database")) {
			throw new RuntimeException("root should be <database>");
		}

		String driverClassName = null;
		String url = null;
		String username = null;
		String password = null;
		for (Object item : node.elements("property")) {
			Element i = (Element) item;
			String value = getValue(i);
			String name = i.attributeValue("name");
			if (name == null || value == null)
				throw new RuntimeException("[database]: <property> should contain name and value");

			switch (name) {
			case "url":
				url = value;
				break;
			case "username":
				username = value;
				break;
			case "password":
				password = value;
				break;
			case "driverClassName":
				driverClassName = value;
				break;
			default:
				throw new RuntimeException("[database]: <property> unknown name");
			}
		}
		return new MyDataSourceFactory(driverClassName, url, username, password);
	}

	private static String getValue(Element element) {
		return element.getText();
	}
}
