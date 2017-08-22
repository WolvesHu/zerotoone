package com.wolves.zerotoone.orm.mapping;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.wolves.zerotoone.orm.proxy.MyMapperProxy;
import com.wolves.zerotoone.orm.session.MySqlSession;

public class MyMapperProxyFactory<T> {
	private final Class<T> mapperInterface;
	private final Map<Method, MyMapperMethod> methodCache = new ConcurrentHashMap<Method, MyMapperMethod>();

	public MyMapperProxyFactory(Class<T> mapperInterface) {
		this.mapperInterface = mapperInterface;
	}

	public Class<T> getMapperInterface() {
		return mapperInterface;
	}

	public Map<Method, MyMapperMethod> getMethodCache() {
		return methodCache;
	}

	@SuppressWarnings("unchecked")
	protected T newInstance(MyMapperProxy<T> mapperProxy) {
		return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] { mapperInterface },
				mapperProxy);
	}

	public T newInstance(MySqlSession sqlSession) {
		final MyMapperProxy<T> mapperProxy = new MyMapperProxy<T>( mapperInterface, methodCache,sqlSession);
		return newInstance(mapperProxy);
	}
}
