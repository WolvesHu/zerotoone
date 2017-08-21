package com.wolves.zerotoone.orm.mapping;

import java.util.Collections;
import java.util.List;

public class MyParameterMap {

	private String id;
	private Class<?> type;
	private List<MyParameterMapping> parameterMappings;

	private MyParameterMap() {
	}

	public static class Builder {
		private MyParameterMap parameterMap = new MyParameterMap();

		public Builder(MyConfiguration configuration, String id, Class<?> type,
				List<MyParameterMapping> parameterMappings) {
			parameterMap.id = id;
			parameterMap.type = type;
			parameterMap.parameterMappings = parameterMappings;
		}

		public Class<?> type() {
			return parameterMap.type;
		}

		public MyParameterMap build() {
			// lock down collections
			parameterMap.parameterMappings = Collections.unmodifiableList(parameterMap.parameterMappings);
			return parameterMap;
		}
	}

	public String getId() {
		return id;
	}

	public Class<?> getType() {
		return type;
	}

	public List<MyParameterMapping> getParameterMappings() {
		return parameterMappings;
	}

}
