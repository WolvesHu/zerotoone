package com.wolves.zerotoone.orm.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wolves.zerotoone.orm.annotation.Sql;
import com.wolves.zerotoone.orm.session.MySqlSession;

public class MapperProxy<T> implements InvocationHandler {
	private MySqlSession session;
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<T> clazz, MySqlSession session) {
		 MapperProxy<T> proxy = new MapperProxy<>();
		 proxy.session = session;
		 return (T)Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, proxy);
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Sql anno = method.getAnnotation(Sql.class);
		if(anno==null) {
			return null;
		}
		String sql = anno.value();
		Pattern p = Pattern.compile("\\{[^\\}]+\\}");
	    Matcher m = p.matcher(sql);
	    if(m.find()) {
	    	StringBuffer sb = new StringBuffer();
	    	do {
	    		String s = m.group();
	    		int index = Integer.valueOf(s.substring(1, s.length() - 1));
	    		m.appendReplacement(sb, args[index].toString());
	    	}while(m.find());
	    	sql = m.appendTail(sb).toString();
	    	
	    }
	    
	    System.out.println(sql);
//        session.exec(sql);
		return null;
	}

}
