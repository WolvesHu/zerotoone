package com.wolves.zerotoone.orm.mapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.session.SqlSession;

import com.wolves.zerotoone.orm.session.MySqlSession;

public class MyMapperRegistry {
	private final MyConfiguration config;
	private final Map<Class<?>, MyMapperProxyFactory<?>> knownMappers = new HashMap<Class<?>, MyMapperProxyFactory<?>>();

	public MyMapperRegistry(MyConfiguration config) {
		this.config = config;
	}

	public void addMappers(String packageName, Class<?> superType) {
		ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<Class<?>>();
		resolverUtil.find(new ResolverUtil.IsA(superType), packageName);
		Set<Class<? extends Class<?>>> mapperSet = resolverUtil.getClasses();
		for (Class<?> mapperClass : mapperSet) {
			addMapper(mapperClass);
		}
	}

	public void addMappers(String packageName) {
		addMappers(packageName, Object.class);
	}

	public <T> void addMapper(Class<T> type) {
		if (type.isInterface()) {
			if (hasMapper(type)) {
				throw new BindingException("Type " + type + " is already known to the MapperRegistry.");
			}
			boolean loadCompleted = false;
			try {
				knownMappers.put(type, new MyMapperProxyFactory<T>(type));
				// It's important that the type is added before the parser is
				// run
				// otherwise the binding may automatically be attempted by the
				// mapper parser. If the type is already known, it won't try.
				MyMapperAnnotationBuilder parser = new MyMapperAnnotationBuilder(config, type);
				parser.parse();
				loadCompleted = true;
			} finally {
				if (!loadCompleted) {
					knownMappers.remove(type);
				}
			}
		}
	}

	public <T> boolean hasMapper(Class<T> type) {
		return knownMappers.containsKey(type);
	}

	@SuppressWarnings("unchecked")
	public <T> T getMapper(Class<T> type, MySqlSession sqlSession) {
		final MyMapperProxyFactory<T> mapperProxyFactory = (MyMapperProxyFactory<T>) knownMappers.get(type);
		if (mapperProxyFactory == null) {
			throw new BindingException("Type " + type + " is not known to the MapperRegistry.");
		}
		try {
			return mapperProxyFactory.newInstance(sqlSession);
		} catch (Exception e) {
			throw new BindingException("Error getting mapper instance. Cause: " + e, e);
		}
	}

}
