package com.wolves.zerotoone.orm.datasource.pooled;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.reflection.ExceptionUtil;

class MyPooledConnection implements InvocationHandler {
	private static final String CLOSE = "close";
	private static final Class<?>[] IFACES = new Class<?>[] { Connection.class };
	private MyPooledDataSource dataSource;
	private Connection realConnection;
	private Connection proxyConnection;
	private long createdTimestamp;
	private long lastUsedTimestamp;
	private int hashCode = 0;
	private boolean valid;
	private int connectionTypeCode;
	private long checkoutTimestamp;

	public MyPooledConnection(Connection connection, MyPooledDataSource dataSource) {
		this.dataSource = dataSource;
		this.realConnection = connection;
		/*
		 * public static Object newProxyInstance(ClassLoader loader, Class<?>[]
		 * interfaces, InvocationHandler h)
		 * loader:一个ClassLoader对象，定义了由哪个ClassLoader对象来对生成的代理对象进行加载
		 * interfaces:一个Interface对象的数组，表示的是我将要给我需要代理的对象提供一组什么接口，如果我提供了一组接口给它，
		 * 那么这个代理对象就宣称实现了该接口(多态)，这样我就能调用这组接口中的方法了
		 * h:表示的是当我这个动态代理对象在调用方法的时候，会关联到哪一个InvocationHandler对象上
		 */
		this.valid = true;
		this.proxyConnection = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(), IFACES, this);
		this.createdTimestamp = System.currentTimeMillis();
		this.lastUsedTimestamp = System.currentTimeMillis();
		this.hashCode = connection.hashCode();
	}

	/*
	 * (非 Javadoc) <p>Title: invoke</p> <p>Description: </p>
	 * 
	 * @param proxy 代我们所代理的那个真实对象
	 * 
	 * @param method 指代的是我们所要调用真实对象的某个方法的Method对象
	 * 
	 * @param arg 指代的是调用真实对象某个方法时接受的参数
	 * 
	 * @return
	 * 
	 * @throws Throwable
	 * 
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 * java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String methodName = method.getName();
		if (CLOSE.hashCode() == methodName.hashCode() && CLOSE.equals(methodName)) {
			dataSource.pushConnection(this);
			return null;
		} else {
			try {
				if (!Object.class.equals(method.getDeclaringClass())) {
					// issue #579 toString() should never fail
					// throw an SQLException instead of a Runtime
					checkConnection();
				}
				return method.invoke(realConnection, args);
			} catch (Throwable t) {
				throw ExceptionUtil.unwrapThrowable(t);
			}
		}
	}

	private void checkConnection() throws SQLException {
		if (!valid) {
			throw new SQLException("Error accessing PooledConnection. Connection is invalid.");
		}
	}

	public boolean isValid() {
		return valid && realConnection != null && dataSource.pingConnection(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MyPooledConnection) {
			return realConnection.hashCode() == (((MyPooledConnection) obj).realConnection.hashCode());
		} else if (obj instanceof Connection) {
			return hashCode == obj.hashCode();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	public Connection getRealConnection() {
		return realConnection;
	}

	public long getTimeElapsedSinceLastUse() {
		return System.currentTimeMillis() - lastUsedTimestamp;
	}

	public int getConnectionTypeCode() {
		return connectionTypeCode;
	}

	public void setConnectionTypeCode(int connectionTypeCode) {
		this.connectionTypeCode = connectionTypeCode;
	}

	public long getCheckoutTime() {
		return System.currentTimeMillis() - checkoutTimestamp;
	}

	public long getCheckoutTimestamp() {
		return checkoutTimestamp;
	}

	public void setCheckoutTimestamp(long checkoutTimestamp) {
		this.checkoutTimestamp = checkoutTimestamp;
	}

	public long getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(long createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public long getLastUsedTimestamp() {
		return lastUsedTimestamp;
	}

	public void setLastUsedTimestamp(long lastUsedTimestamp) {
		this.lastUsedTimestamp = lastUsedTimestamp;
	}

	public void invalidate() {
		valid = false;
	}

	public Connection getProxyConnection() {
		return proxyConnection;
	}

}
