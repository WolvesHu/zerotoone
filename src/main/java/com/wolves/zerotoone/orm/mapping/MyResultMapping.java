package com.wolves.zerotoone.orm.mapping;

import java.util.ArrayList;
import java.util.Set;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

public class MyResultMapping {
	private MyConfiguration configuration;
	private String property;
	private String column;
	private Class<?> javaType;
	private JdbcType jdbcType;
	private TypeHandler<?> typeHandler;
	private Set<String> notNullColumns;
	private String foreignColumn;
	private boolean lazy;

	MyResultMapping() {
	}

	public static class Builder {
		private MyResultMapping resultMapping = new MyResultMapping();

		public Builder(MyConfiguration configuration, String property, String column, Class<?> javaType) {
			this(configuration, property);
			resultMapping.column = column;
			resultMapping.javaType = javaType;
		}

		public Builder(MyConfiguration configuration, String property) {
			resultMapping.configuration = configuration;
			resultMapping.property = property;
//			resultMapping.flags = new ArrayList<ResultFlag>();
//			resultMapping.composites = new ArrayList<ResultMapping>();
			resultMapping.lazy = false;
		}

		public Builder javaType(Class<?> javaType) {
			resultMapping.javaType = javaType;
			return this;
		}

		public Builder jdbcType(JdbcType jdbcType) {
			resultMapping.jdbcType = jdbcType;
			return this;
		}

		public Builder foreignColumn(String foreignColumn) {
			resultMapping.foreignColumn = foreignColumn;
			return this;
		}

		public Builder notNullColumns(Set<String> notNullColumns) {
			resultMapping.notNullColumns = notNullColumns;
			return this;
		}

		public Builder typeHandler(TypeHandler<?> typeHandler) {
			resultMapping.typeHandler = typeHandler;
			return this;
		}

		public Builder lazy(boolean lazy) {
			resultMapping.lazy = lazy;
			return this;
		}

		public MyResultMapping build() {
			// lock down collections
			resolveTypeHandler();
			return resultMapping;
		}

		private void resolveTypeHandler() {
			if (resultMapping.typeHandler == null && resultMapping.javaType != null) {
				MyConfiguration configuration = resultMapping.configuration;
//				 TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
//				 resultMapping.typeHandler =
//				 typeHandlerRegistry.getTypeHandler(resultMapping.javaType,
//				 resultMapping.jdbcType);
			}
		}

		public Builder column(String column) {
			resultMapping.column = column;
			return this;
		}
	}

	public String getProperty() {
		return property;
	}

	public String getColumn() {
		return column;
	}

	public Class<?> getJavaType() {
		return javaType;
	}

	public JdbcType getJdbcType() {
		return jdbcType;
	}

	public TypeHandler<?> getTypeHandler() {
		return typeHandler;
	}

	public Set<String> getNotNullColumns() {
		return notNullColumns;
	}

	public String getForeignColumn() {
		return foreignColumn;
	}

	public void setForeignColumn(String foreignColumn) {
		this.foreignColumn = foreignColumn;
	}

	public boolean isLazy() {
		return lazy;
	}

	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MyResultMapping that = (MyResultMapping) o;

		if (property == null || !property.equals(that.property)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		if (property != null) {
			return property.hashCode();
		} else if (column != null) {
			return column.hashCode();
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ResultMapping{");
		sb.append("property='").append(property).append('\'');
		sb.append(", column='").append(column).append('\'');
		sb.append(", javaType=").append(javaType);
		sb.append(", jdbcType=").append(jdbcType);
		sb.append(", notNullColumns=").append(notNullColumns);
		sb.append(", foreignColumn='").append(foreignColumn).append('\'');
		sb.append(", lazy=").append(lazy);
		sb.append('}');
		return sb.toString();
	}

}
