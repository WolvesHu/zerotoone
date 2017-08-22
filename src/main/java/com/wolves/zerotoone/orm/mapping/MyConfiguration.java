package com.wolves.zerotoone.orm.mapping;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.builder.xml.XMLStatementBuilder;
import org.apache.ibatis.plugin.InterceptorChain;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.type.TypeAliasRegistry;

import com.wolves.zerotoone.orm.builder.MyXMLStatementBuilder;
import com.wolves.zerotoone.orm.datasource.pooled.MyPooledDataSourceFactory;
import com.wolves.zerotoone.orm.datasource.unpooled.MyUnpooledDataSourceFactory;
import com.wolves.zerotoone.orm.executor.MyExecutor;
import com.wolves.zerotoone.orm.executor.MySimpleExecutor;
import com.wolves.zerotoone.orm.parse.MyXNode;
import com.wolves.zerotoone.orm.session.MySqlSession;
import com.wolves.zerotoone.orm.transaction.MyManagedTransactionFactory;
import com.wolves.zerotoone.orm.transaction.MyTransaction;

public class MyConfiguration {
	protected final Map<String, MyParameterMap> parameterMaps = new MyStrictMap<MyParameterMap>(
			"Parameter Maps collection");
	protected MyEnvironment environment;
	protected final Collection<MyXMLStatementBuilder> incompleteStatements = new LinkedList<MyXMLStatementBuilder>();
	  
	protected final InterceptorChain interceptorChain = new InterceptorChain();
	protected ExecutorType defaultExecutorType = ExecutorType.SIMPLE;
	protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
	protected final Map<String, MyMappedStatement> mappedStatements = new MyStrictMap<MyMappedStatement>(
			"Mapped Statements collection");
	protected final Map<String, MyResultMap> resultMaps = new MyStrictMap<MyResultMap>("Result Maps collection");
	protected Properties variables = new Properties();
	protected final MyMapperRegistry mapperRegistry = new MyMapperRegistry(this);
	protected final Map<String, MyXNode> sqlFragments = new MyStrictMap<MyXNode>(
			"XML fragments parsed from previous mappers");
	protected final Set<String> loadedResources = new HashSet<String>();

	public MyConfiguration() {
		typeAliasRegistry.registerAlias("UNPOOLED", MyUnpooledDataSourceFactory.class);
		typeAliasRegistry.registerAlias("POOLED", MyPooledDataSourceFactory.class);
		typeAliasRegistry.registerAlias("MANAGED", MyManagedTransactionFactory.class);
	}

	public MyConfiguration(MyEnvironment environment) {
		this();
		this.environment = environment;
	}

	public Map<String, MyXNode> getSqlFragments() {
		return sqlFragments;
	}

	public void addLoadedResource(String resource) {
		loadedResources.add(resource);
	}

	public boolean isResourceLoaded(String resource) {
		return loadedResources.contains(resource);
	}

	public void addMappers(String packageName) {
		mapperRegistry.addMappers(packageName);
	}

	protected static class MyStrictMap<V> extends HashMap<String, V> {
		private static final long serialVersionUID = -4950446264854982944L;
		private final String name;

		public MyStrictMap(String name, int initialCapacity, float loadFactor) {
			super(initialCapacity, loadFactor);
			this.name = name;
		}

		public MyStrictMap(String name, int initialCapacity) {
			super(initialCapacity);
			this.name = name;
		}

		public MyStrictMap(String name) {
			super();
			this.name = name;
		}

		public MyStrictMap(String name, Map<String, ? extends V> m) {
			super(m);
			this.name = name;
		}

		@SuppressWarnings("unchecked")
		public V put(String key, V value) {
			if (containsKey(key)) {
				throw new IllegalArgumentException(name + " already contains value for " + key);
			}
			if (key.contains(".")) {
				final String shortKey = getShortName(key);
				if (super.get(shortKey) == null) {
					super.put(shortKey, value);
				} else {
					super.put(shortKey, (V) new Ambiguity(shortKey));
				}
			}
			return super.put(key, value);
		}

		public V get(Object key) {
			V value = super.get(key);
			if (value == null) {
				throw new IllegalArgumentException(name + " does not contain value for " + key);
			}
			if (value instanceof Ambiguity) {
				throw new IllegalArgumentException(((Ambiguity) value).getSubject() + " is ambiguous in " + name
						+ " (try using the full name including the namespace, or rename one of the entries)");
			}
			return value;
		}

		// TODO BD
		private String getShortName(String key) {
			final String[] keyParts = key.split("\\.");
			return keyParts[keyParts.length - 1];
		}

		// TODO BD
		protected static class Ambiguity {
			final private String subject;

			public Ambiguity(String subject) {
				this.subject = subject;
			}

			public String getSubject() {
				return subject;
			}
		}
	}

	public MyEnvironment getEnvironment() {
		return environment;
	}

	public void setEnvironment(MyEnvironment environment) {
		this.environment = environment;
	}

	public TypeAliasRegistry getTypeAliasRegistry() {
		return typeAliasRegistry;
	}

	public Properties getVariables() {
		return variables;
	}

	public void setVariables(Properties variables) {
		this.variables = variables;
	}

	public void addResultMap(MyResultMap rm) {
		resultMaps.put(rm.getId(), rm);
		// checkLocallyForDiscriminatedNestedResultMaps(rm);
		// checkGloballyForDiscriminatedNestedResultMaps(rm);
	}

	public MyResultMap getResultMap(String id) {
		return resultMaps.get(id);
	}

	public boolean hasResultMap(String id) {
		return resultMaps.containsKey(id);
	}

	public MyExecutor newExecutor(MyTransaction transaction, ExecutorType executorType) {
		executorType = executorType == null ? defaultExecutorType : executorType;
		executorType = executorType == null ? ExecutorType.SIMPLE : executorType;
		// MyExecutor executor;
		// if (ExecutorType.BATCH == executorType) {
		// // executor = new BatchExecutor(this, transaction);
		// } else if (ExecutorType.REUSE == executorType) {
		// // executor = new ReuseExecutor(this, transaction);
		// } else {
		//
		// }
		// if (cacheEnabled) {
		// executor = new CachingExecutor(executor);
		// }
		// executor = (MyExecutor) interceptorChain.pluginAll(executor);
		return new MySimpleExecutor(this, transaction);
	}

	public <T> T getMapper(Class<T> type, MySqlSession sqlSession) {
		return mapperRegistry.getMapper(type, sqlSession);
	}

	public boolean hasStatement(String statementId) {
		return false;
	}

	public MyMappedStatement getMappedStatement(String statementId) {
		return null;
	}

	public boolean isUseGeneratedKeys() {
		return false;
	}

	public MetaObject newMetaObject(Map<String, Object> additionalParameters) {
		return null;
	}

	public ObjectFactory getObjectFactory() {
		return null;
	}

	public boolean hasMapper(Class<?> boundType) {
		// TODO Auto-generated method stub
		return false;
	}

	public void addMapper(Class<?> boundType) {
		// TODO Auto-generated method stub

	}

	public void addMappedStatement(MyMappedStatement ms) {
		mappedStatements.put(ms.getId(), ms);
	}

	public MyParameterMap getParameterMap(String id) {
		return parameterMaps.get(id);
	}

	public void addIncompleteStatement(MyXMLStatementBuilder incompleteStatement) {
		incompleteStatements.add(incompleteStatement);
	}

	public boolean isUseActualParamName() {
		// TODO Auto-generated method stub
		return false;
	}

	public MetaObject newMetaObject(Object value) {
		return null;
	}

}
