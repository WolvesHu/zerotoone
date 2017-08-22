package com.wolves.zerotoone.orm.mapping;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperMethod.MethodSignature;
import org.apache.ibatis.binding.MapperMethod.SqlCommand;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.reflection.ParamNameResolver;
import org.apache.ibatis.reflection.TypeParameterResolver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.wolves.zerotoone.orm.session.MySqlSession;

public class MyMapperMethod {
	private final MySqlCommand command;
	private final MyMethodSignature method;

	public MyMapperMethod(Class<?> mapperInterface, Method method, MyConfiguration config) {
		this.command = new MySqlCommand(config, mapperInterface, method);
		this.method = new MyMethodSignature(config, mapperInterface, method);
	}

	public Object execute(MySqlSession sqlSession, Object[] args) {
		Object result;
		switch (command.getType()) {
		case INSERT: {
			Object param = method.convertArgsToSqlCommandParam(args);
			result = rowCountResult(sqlSession.insert(command.getName(), param));
			break;
		}
		case UPDATE: {
			Object param = method.convertArgsToSqlCommandParam(args);
			result = rowCountResult(sqlSession.update(command.getName(), param));
			break;
		}
		case DELETE: {
			Object param = method.convertArgsToSqlCommandParam(args);
			result = rowCountResult(sqlSession.delete(command.getName(), param));
			break;
		}
		case SELECT:
			// if (method.returnsVoid() && method.hasResultHandler()) {
			// executeWithResultHandler(sqlSession, args);
			// result = null;
			// } else if (method.returnsMany()) {
			// result = executeForMany(sqlSession, args);
			// } else if (method.returnsMap()) {
			// result = executeForMap(sqlSession, args);
			// } else if (method.returnsCursor()) {
			// result = executeForCursor(sqlSession, args);
			// } else {
			Object param = method.convertArgsToSqlCommandParam(args);
			result = sqlSession.selectOne(command.getName(), param);
			// }
			break;
		// case FLUSH:
		//// result = sqlSession.flushStatements();
		// break;
		default:
			throw new BindingException("Unknown execution method for: " + command.getName());
		}
		if (result == null && method.getReturnType().isPrimitive() && !method.returnsVoid()) {
			throw new BindingException("Mapper method '" + command.getName()
					+ " attempted to return null from a method with a primitive return type (" + method.getReturnType()
					+ ").");
		}
		return result;
	}

	private Object rowCountResult(int rowCount) {
		final Object result;
		if (method.returnsVoid()) {
			result = null;
		} else if (Integer.class.equals(method.getReturnType()) || Integer.TYPE.equals(method.getReturnType())) {
			result = rowCount;
		} else if (Long.class.equals(method.getReturnType()) || Long.TYPE.equals(method.getReturnType())) {
			result = (long) rowCount;
		} else if (Boolean.class.equals(method.getReturnType()) || Boolean.TYPE.equals(method.getReturnType())) {
			result = rowCount > 0;
		} else {
			throw new BindingException("Mapper method '" + command.getName() + "' has an unsupported return type: "
					+ method.getReturnType());
		}
		return result;
	}

	public static class MySqlCommand {

		private final String name;
		private final SqlCommandType type;

		public MySqlCommand(MyConfiguration configuration, Class<?> mapperInterface, Method method) {
			final String methodName = method.getName();
			final Class<?> declaringClass = method.getDeclaringClass();
			MyMappedStatement ms = resolveMappedStatement(mapperInterface, methodName, declaringClass, configuration);
			if (ms == null) {
				if (method.getAnnotation(Flush.class) != null) {
					name = null;
					type = SqlCommandType.FLUSH;
				} else {
					throw new BindingException(
							"Invalid bound statement (not found): " + mapperInterface.getName() + "." + methodName);
				}
			} else {
				name = ms.getId();
				type = ms.getSqlCommandType();
				if (type == SqlCommandType.UNKNOWN) {
					throw new BindingException("Unknown execution method for: " + name);
				}
			}
		}

		public String getName() {
			return name;
		}

		public SqlCommandType getType() {
			return type;
		}

		private MyMappedStatement resolveMappedStatement(Class<?> mapperInterface, String methodName,
				Class<?> declaringClass, MyConfiguration configuration) {
			String statementId = mapperInterface.getName() + "." + methodName;
			if (configuration.hasStatement(statementId)) {
				return configuration.getMappedStatement(statementId);
			} else if (mapperInterface.equals(declaringClass)) {
				return null;
			}
			for (Class<?> superInterface : mapperInterface.getInterfaces()) {
				if (declaringClass.isAssignableFrom(superInterface)) {
					MyMappedStatement ms = resolveMappedStatement(superInterface, methodName, declaringClass,
							configuration);
					if (ms != null) {
						return ms;
					}
				}
			}
			return null;
		}
	}

	public static class MyMethodSignature {

		private final boolean returnsMany;
		private final boolean returnsMap;
		private final boolean returnsVoid;
		private final boolean returnsCursor;
		private final Class<?> returnType;
		private final String mapKey;
		private final Integer resultHandlerIndex;
		private final Integer rowBoundsIndex;
		private final MyParamNameResolver paramNameResolver;

		public MyMethodSignature(MyConfiguration configuration, Class<?> mapperInterface, Method method) {
			Type resolvedReturnType = TypeParameterResolver.resolveReturnType(method, mapperInterface);
			if (resolvedReturnType instanceof Class<?>) {
				this.returnType = (Class<?>) resolvedReturnType;
			} else if (resolvedReturnType instanceof ParameterizedType) {
				this.returnType = (Class<?>) ((ParameterizedType) resolvedReturnType).getRawType();
			} else {
				this.returnType = method.getReturnType();
			}
			this.returnsVoid = void.class.equals(this.returnType);
			this.returnsMany = false;
			this.returnsCursor = Cursor.class.equals(this.returnType);
			this.mapKey = getMapKey(method);
			this.returnsMap = (this.mapKey != null);
			this.rowBoundsIndex = getUniqueParamIndex(method, RowBounds.class);
			this.resultHandlerIndex = getUniqueParamIndex(method, ResultHandler.class);
			this.paramNameResolver = new MyParamNameResolver(configuration, method);
		}

		public Object convertArgsToSqlCommandParam(Object[] args) {
			return paramNameResolver.getNamedParams(args);
		}

		public boolean hasRowBounds() {
			return rowBoundsIndex != null;
		}

		public RowBounds extractRowBounds(Object[] args) {
			return hasRowBounds() ? (RowBounds) args[rowBoundsIndex] : null;
		}

		public boolean hasResultHandler() {
			return resultHandlerIndex != null;
		}

		public ResultHandler extractResultHandler(Object[] args) {
			return hasResultHandler() ? (ResultHandler) args[resultHandlerIndex] : null;
		}

		public String getMapKey() {
			return mapKey;
		}

		public Class<?> getReturnType() {
			return returnType;
		}

		// public boolean returnsMany() {
		// return returnsMany;
		// }

		public boolean returnsMap() {
			return returnsMap;
		}

		public boolean returnsVoid() {
			return returnsVoid;
		}

		public boolean returnsCursor() {
			return returnsCursor;
		}

		private Integer getUniqueParamIndex(Method method, Class<?> paramType) {
			Integer index = null;
			final Class<?>[] argTypes = method.getParameterTypes();
			for (int i = 0; i < argTypes.length; i++) {
				if (paramType.isAssignableFrom(argTypes[i])) {
					if (index == null) {
						index = i;
					} else {
						throw new BindingException(method.getName() + " cannot have multiple "
								+ paramType.getSimpleName() + " parameters");
					}
				}
			}
			return index;
		}

		private String getMapKey(Method method) {
			String mapKey = null;
			if (Map.class.isAssignableFrom(method.getReturnType())) {
				final MapKey mapKeyAnnotation = method.getAnnotation(MapKey.class);
				if (mapKeyAnnotation != null) {
					mapKey = mapKeyAnnotation.value();
				}
			}
			return mapKey;
		}
	}
}
