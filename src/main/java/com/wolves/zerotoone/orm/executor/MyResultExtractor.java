package com.wolves.zerotoone.orm.executor;

import java.lang.reflect.Array;
import java.util.List;

import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.ObjectFactory;

import com.wolves.zerotoone.orm.mapping.MyConfiguration;

public class MyResultExtractor {
	private final MyConfiguration configuration;
	private final ObjectFactory objectFactory;

	public MyResultExtractor(MyConfiguration configuration, ObjectFactory objectFactory) {
		this.configuration = configuration;
		this.objectFactory = objectFactory;
	}

	public Object extractObjectFromList(List<Object> list, Class<?> targetType) {
		Object value = null;
		if (targetType != null && targetType.isAssignableFrom(list.getClass())) {
			value = list;
		} else if (targetType != null && objectFactory.isCollection(targetType)) {
			value = objectFactory.create(targetType);
			MetaObject metaObject = configuration.newMetaObject(value);
			metaObject.addAll(list);
		} else if (targetType != null && targetType.isArray()) {
			Class<?> arrayComponentType = targetType.getComponentType();
			Object array = Array.newInstance(arrayComponentType, list.size());
			if (arrayComponentType.isPrimitive()) {
				for (int i = 0; i < list.size(); i++) {
					Array.set(array, i, list.get(i));
				}
				value = array;
			} else {
				value = list.toArray((Object[]) array);
			}
		} else {
			if (list != null && list.size() > 1) {
				throw new ExecutorException(
						"Statement returned more than one row, where no more than one was expected.");
			} else if (list != null && list.size() == 1) {
				value = list.get(0);
			}
		}
		return value;
	}
}
