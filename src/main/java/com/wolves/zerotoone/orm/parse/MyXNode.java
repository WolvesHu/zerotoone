package com.wolves.zerotoone.orm.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.parsing.PropertyParser;
import org.apache.ibatis.parsing.XNode;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Clinton Begin
 */
public class MyXNode {

	private Node node;
	private String name;
	private String body;
	private Properties attributes;
	private Properties variables;
	private MyXPathParser xpathParser;

	public MyXNode(MyXPathParser xpathParser, Node node, Properties variables) {
		this.xpathParser = xpathParser;
		this.node = node;
		this.name = node.getNodeName();
		this.variables = variables;
		this.attributes = parseAttributes(node);
		this.body = parseBody(node);
	}

	private Properties parseAttributes(Node n) {
		Properties attributes = new Properties();
		NamedNodeMap attributeNodes = n.getAttributes();
		if (attributeNodes != null) {
			for (int i = 0; i < attributeNodes.getLength(); i++) {
				Node attribute = attributeNodes.item(i);
				String value = PropertyParser.parse(attribute.getNodeValue(), variables);
				attributes.put(attribute.getNodeName(), value);
			}
		}
		return attributes;
	}

	private String parseBody(Node node) {
		String data = getBodyData(node);
		if (data == null) {
			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				data = getBodyData(child);
				if (data != null) {
					break;
				}
			}
		}
		return data;
	}

	private String getBodyData(Node child) {
		if (child.getNodeType() == Node.CDATA_SECTION_NODE || child.getNodeType() == Node.TEXT_NODE) {
			String data = ((CharacterData) child).getData();
			data = PropertyParser.parse(data, variables);
			return data;
		}
		return null;
	}

	public String getValueBasedIdentifier() {
		StringBuilder builder = new StringBuilder();
		MyXNode current = this;
		while (current != null) {
			if (current != this) {
				builder.insert(0, "_");
			}
			//TODO BD
			String value = current.getStringAttribute("id",current.getStringAttribute("value", current.getStringAttribute("property", null)));
			if (value != null) {
				value = value.replace('.', '_');
				builder.insert(0, "]");
				builder.insert(0, value);
				builder.insert(0, "[");
			}
			builder.insert(0, current.getName());
			current = current.getParent();
		}
		return builder.toString();
	}

	public MyXNode getParent() {
		Node parent = node.getParentNode();
		if (parent == null || !(parent instanceof Element)) {
			return null;
		} else {
			return new MyXNode(xpathParser, parent, variables);
		}
	}

	public List<MyXNode> evalNodes(String expression) {
		return xpathParser.evalNodes(node, expression);
	}

	public MyXNode evalNode(String expression) {
		return xpathParser.evalNode(node, expression);
	}

	public Node getNode() {
		return node;
	}

	public String getName() {
		return name;
	}

	public String getStringBody() {
		return getStringBody(null);
	}

	public String getStringBody(String def) {
		if (body == null) {
			return def;
		} else {
			return body;
		}
	}

	public Boolean getBooleanBody() {
		return getBooleanBody(null);
	}

	public Boolean getBooleanBody(Boolean def) {
		if (body == null) {
			return def;
		} else {
			return Boolean.valueOf(body);
		}
	}

	public Integer getIntBody() {
		return getIntBody(null);
	}

	public Integer getIntBody(Integer def) {
		if (body == null) {
			return def;
		} else {
			return Integer.parseInt(body);
		}
	}

	public Long getLongBody() {
		return getLongBody(null);
	}

	public Long getLongBody(Long def) {
		if (body == null) {
			return def;
		} else {
			return Long.parseLong(body);
		}
	}

	public Double getDoubleBody() {
		return getDoubleBody(null);
	}

	public Double getDoubleBody(Double def) {
		if (body == null) {
			return def;
		} else {
			return Double.parseDouble(body);
		}
	}

	public Float getFloatBody() {
		return getFloatBody(null);
	}

	public Float getFloatBody(Float def) {
		if (body == null) {
			return def;
		} else {
			return Float.parseFloat(body);
		}
	}

	public <T extends Enum<T>> T getEnumAttribute(Class<T> enumType, String name) {
		return getEnumAttribute(enumType, name, null);
	}

	public <T extends Enum<T>> T getEnumAttribute(Class<T> enumType, String name, T def) {
		String value = getStringAttribute(name);
		if (value == null) {
			return def;
		} else {
			return Enum.valueOf(enumType, value);
		}
	}

	public String getStringAttribute(String name) {
		return getStringAttribute(name, null);
	}

	public String getStringAttribute(String name, String def) {
		String value = attributes.getProperty(name);
		if (value == null) {
			return def;
		} else {
			return value;
		}
	}

	public Boolean getBooleanAttribute(String name) {
		return getBooleanAttribute(name, null);
	}

	public Boolean getBooleanAttribute(String name, Boolean def) {
		String value = attributes.getProperty(name);
		if (value == null) {
			return def;
		} else {
			return Boolean.valueOf(value);
		}
	}

	public Integer getIntAttribute(String name) {
		return getIntAttribute(name, null);
	}

	public Integer getIntAttribute(String name, Integer def) {
		String value = attributes.getProperty(name);
		if (value == null) {
			return def;
		} else {
			return Integer.parseInt(value);
		}
	}

	public Long getLongAttribute(String name) {
		return getLongAttribute(name, null);
	}

	public Long getLongAttribute(String name, Long def) {
		String value = attributes.getProperty(name);
		if (value == null) {
			return def;
		} else {
			return Long.parseLong(value);
		}
	}

	public Double getDoubleAttribute(String name) {
		return getDoubleAttribute(name, null);
	}

	public Double getDoubleAttribute(String name, Double def) {
		String value = attributes.getProperty(name);
		if (value == null) {
			return def;
		} else {
			return Double.parseDouble(value);
		}
	}

	public Float getFloatAttribute(String name) {
		return getFloatAttribute(name, null);
	}

	public Float getFloatAttribute(String name, Float def) {
		String value = attributes.getProperty(name);
		if (value == null) {
			return def;
		} else {
			return Float.parseFloat(value);
		}
	}

	public Properties getChildrenAsProperties() {
		Properties properties = new Properties();
		for (MyXNode child : getChildren()) {
			String name = child.getStringAttribute("name");
			String value = child.getStringAttribute("value");
			if (name != null && value != null) {
				properties.setProperty(name, value);
			}
		}
		return properties;
	}

	public List<MyXNode> getChildren() {
		List<MyXNode> children = new ArrayList<MyXNode>();
		NodeList nodeList = node.getChildNodes();
		if (nodeList != null) {
			for (int i = 0, n = nodeList.getLength(); i < n; i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					children.add(new MyXNode(xpathParser, node, variables));
				}
			}
		}
		return children;
	}
}