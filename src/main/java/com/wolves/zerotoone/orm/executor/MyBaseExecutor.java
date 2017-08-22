package com.wolves.zerotoone.orm.executor;

import static org.apache.ibatis.executor.ExecutionPlaceholder.EXECUTION_PLACEHOLDER;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.ObjectFactory;

import com.wolves.zerotoone.orm.mapping.MyConfiguration;
import com.wolves.zerotoone.orm.transaction.MyTransaction;

public abstract class MyBaseExecutor implements MyExecutor {
	protected MyTransaction transaction;
	protected MyExecutor wrapper;

	protected ConcurrentLinkedQueue<DeferredLoad> deferredLoads;
	protected PerpetualCache localCache;
	protected PerpetualCache localOutputParameterCache;
	protected MyConfiguration configuration;
	
	
	

	private static class DeferredLoad {

		private final MetaObject resultObject;
		private final String property;
		private final Class<?> targetType;
		private final CacheKey key;
		private final PerpetualCache localCache;
		private final ObjectFactory objectFactory;
		private final MyResultExtractor resultExtractor;

		// issue #781
		public DeferredLoad(MetaObject resultObject, String property, CacheKey key, PerpetualCache localCache,
				MyConfiguration configuration, Class<?> targetType) {
			this.resultObject = resultObject;
			this.property = property;
			this.key = key;
			this.localCache = localCache;
			this.objectFactory = configuration.getObjectFactory();
			this.resultExtractor = new MyResultExtractor(configuration, objectFactory);
			this.targetType = targetType;
		}

		public boolean canLoad() {
			return localCache.getObject(key) != null && localCache.getObject(key) != EXECUTION_PLACEHOLDER;
		}

		public void load() {
			@SuppressWarnings("unchecked")
			// we suppose we get back a List
			List<Object> list = (List<Object>) localCache.getObject(key);
			Object value = resultExtractor.extractObjectFromList(list, targetType);
			resultObject.setValue(property, value);
		}

	}
}
