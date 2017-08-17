package com.wolves.zerotoone.orm.datasource.unpooled;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import com.wolves.zerotoone.orm.datasource.MyDataSourceFactory;

public class MyUnpooledDataSourceFactory implements MyDataSourceFactory {
	protected DataSource dataSource;

	public MyUnpooledDataSourceFactory() {
		this.dataSource = new MyUnpooledDataSource();
	}

	@Override
	public void setProperties(Properties properties) {
		MetaObject metaDataSource = SystemMetaObject.forObject(dataSource);
		for (Object key : properties.keySet()) {
			String propertyName = (String) key;
			String value = (String) properties.get(propertyName);
			Object convertedValue = convertValue(metaDataSource, propertyName, value);
			metaDataSource.setValue(propertyName, convertedValue);
		}
	}

	private Object convertValue(MetaObject metaDataSource, String propertyName, String value) {
		Object convertedValue = value;
		Class<?> targetType = metaDataSource.getSetterType(propertyName);
		if (targetType == Integer.class || targetType == int.class) {
			convertedValue = Integer.valueOf(value);
		} else if (targetType == Long.class || targetType == long.class) {
			convertedValue = Long.valueOf(value);
		} else if (targetType == Boolean.class || targetType == boolean.class) {
			convertedValue = Boolean.valueOf(value);
		}
		return convertedValue;
	}

	@Override
	public DataSource getDataSource() {
		return dataSource;
	}
}
