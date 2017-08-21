package com.wolves.zerotoone.orm.proxy;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.SqlSession;

import com.wolves.zerotoone.orm.annotation.Sql;
import com.wolves.zerotoone.orm.mapping.MyMapperMethod;
import com.wolves.zerotoone.orm.session.MySqlSession;

public class MyMapperProxy<T> implements InvocationHandler, Serializable {

	private static final long serialVersionUID = -6424540398559729838L;
	private final Class<T> mapperInterface;
	private final Map<Method, MyMapperMethod> methodCache;
	private MySqlSession sqlSession;

	public MyMapperProxy(Class<T> mapperInterface, Map<Method, MyMapperMethod> methodCache, MySqlSession sqlSession) {
		this.mapperInterface = mapperInterface;
		this.methodCache = methodCache;
		this.sqlSession = sqlSession;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		MyMapperMethod mapperMethod = methodCache.get(method);
		if (mapperMethod == null) {
			mapperMethod = new MyMapperMethod(mapperInterface, method, sqlSession.getConfiguration());
			methodCache.put(method, mapperMethod);
		}

		return mapperMethod.execute(sqlSession, args);
	}

}
