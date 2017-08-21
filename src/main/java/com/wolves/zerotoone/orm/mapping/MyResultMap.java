package com.wolves.zerotoone.orm.mapping;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.ibatis.mapping.Discriminator;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMapping;

public class MyResultMap {
	private MyConfiguration configuration;
	private String id;
	private Class<?> type;
	private Set<String> mappedColumns;
	private Set<String> mappedProperties;
	private List<MyResultMapping> resultMappings;

	private MyResultMap() {
	}

	public static class Builder {
		private MyResultMap resultMap = new MyResultMap();

		public Builder(MyConfiguration configuration, String id, Class<?> type, List<MyResultMapping> resultMappings) {
			this(configuration, id, type, resultMappings, null);
		}

		public Builder(MyConfiguration configuration, String id, Class<?> type, List<MyResultMapping> resultMappings,
				Boolean autoMapping) {
			resultMap.configuration = configuration;
			resultMap.id = id;
			resultMap.type = type;
			resultMap.resultMappings = resultMappings;
			// resultMap.autoMapping = autoMapping;
		}

		public MyResultMap build() {
			if (resultMap.id == null) {
				throw new IllegalArgumentException("ResultMaps must have an id");
			}
			resultMap.mappedColumns = new HashSet<String>();
			resultMap.mappedProperties = new HashSet<String>();
			for (MyResultMapping resultMapping : resultMap.resultMappings) {
				final String column = resultMapping.getColumn();
				if (column != null) {
					resultMap.mappedColumns.add(column.toUpperCase(Locale.ENGLISH));
				} 
				final String property = resultMapping.getProperty();
				if (property != null) {
					resultMap.mappedProperties.add(property);
				}
			}
			return resultMap;
		}

		public Class<?> type() {
			return resultMap.type;
		}

	}

	public String getId() {
		return id;
	}

	public Class<?> getType() {
		return type;
	}

	public List<MyResultMapping> getResultMappings() {
		return resultMappings;
	}

	public Set<String> getMappedColumns() {
		return mappedColumns;
	}

	public Set<String> getMappedProperties() {
		return mappedProperties;
	}

	public boolean hasNestedResultMaps() {
		return false;
	}

}
