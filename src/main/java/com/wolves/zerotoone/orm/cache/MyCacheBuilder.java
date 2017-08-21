package com.wolves.zerotoone.orm.cache;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.builder.InitializingObject;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheException;
import org.apache.ibatis.cache.decorators.BlockingCache;
import org.apache.ibatis.cache.decorators.LoggingCache;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.cache.decorators.ScheduledCache;
import org.apache.ibatis.cache.decorators.SerializedCache;
import org.apache.ibatis.cache.decorators.SynchronizedCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;


public class MyCacheBuilder {
	private String id;
	private Class<? extends MyCache> implementation;
	private List<Class<? extends MyCache>> decorators;
	private Integer size;
	private Long clearInterval;
	private boolean readWrite;
	private Properties properties;
	private boolean blocking;

	public MyCacheBuilder(String id) {
	    this.id = id;
	    this.decorators = new ArrayList<Class<? extends MyCache>>();
	  }

	public MyCacheBuilder implementation(Class<? extends MyCache> implementation) {
		this.implementation = implementation;
		return this;
	}

	public MyCacheBuilder addDecorator(Class<? extends MyCache> decorator) {
		if (decorator != null) {
			this.decorators.add(decorator);
		}
		return this;
	}

	public MyCacheBuilder size(Integer size) {
		this.size = size;
		return this;
	}

	public MyCacheBuilder clearInterval(Long clearInterval) {
		this.clearInterval = clearInterval;
		return this;
	}

	public MyCacheBuilder readWrite(boolean readWrite) {
		this.readWrite = readWrite;
		return this;
	}

	public MyCacheBuilder blocking(boolean blocking) {
		this.blocking = blocking;
		return this;
	}

	public MyCacheBuilder properties(Properties properties) {
		this.properties = properties;
		return this;
	}
}
