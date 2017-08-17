package com.wolves.zerotoone.orm.datasource.pooled;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.wolves.zerotoone.orm.datasource.unpooled.MyUnpooledDataSource;

public class MyPooledDataSource implements DataSource {

	private final MyPoolState state = new MyPoolState(this);
	private final MyUnpooledDataSource dataSource;

	/**
	 * @Fields 在任意时间可以存在的活动（也就是正在使用）连接数量
	 */
	protected int poolMaximumActiveConnections = 10;
	/**
	 * @Fields 任意时间可能存在的空闲连接数。
	 */
	protected int poolMaximumIdleConnections = 5;
	/**
	 * @Fields 在被强制返回之前，池中连接被检出（checked out）时间
	 */
	protected int poolMaximumCheckoutTime = 20000;
	/**
	 * @Fields poolPingQuery : 发送到数据的侦测查询,用来验证连接是否正常工作, 并且准备 接受请求。默认是“NO PING
	 *         QUERY SET” ,这会引起许多数据库驱动连接 由一 个错误信息而导致失败。
	 */
	protected String poolPingQuery = "NO PING QUERY SET";
	/**
	 * @Fields 是否启用侦测查询
	 */
	protected boolean poolPingEnabled;
	/**
	 * @Fields 配置 poolPingQuery 的使用频度
	 */
	protected int poolPingConnectionsNotUsedFor;
	private int expectedConnectionTypeCode;

	public MyPooledDataSource() {
		dataSource = new MyUnpooledDataSource();
	}

	public MyPooledDataSource(MyUnpooledDataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Connection getConnection() throws SQLException {
		 return popConnection(dataSource.getUsername(), dataSource.getPassword()).getProxyConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return popConnection(username, password).getProxyConnection();
	}
	protected void pushConnection(MyPooledConnection conn) throws SQLException {
		synchronized (state) {
			state.activeConnections.remove(conn);
			if (conn.isValid()) {
				state.accumulatedCheckoutTime += conn.getCheckoutTime();
				if (!conn.getRealConnection().getAutoCommit()) {
					conn.getRealConnection().rollback();
				}

				if (state.idleConnections.size() < poolMaximumIdleConnections
						&& conn.getConnectionTypeCode() == expectedConnectionTypeCode) {
					MyPooledConnection newConn = new MyPooledConnection(conn.getRealConnection(), this);
					state.idleConnections.add(newConn);
					newConn.setCreatedTimestamp(conn.getCreatedTimestamp());
					newConn.setLastUsedTimestamp(conn.getLastUsedTimestamp());
					conn.invalidate();
				} else {
					conn.getRealConnection().close();
					conn.invalidate();
				}

			} else {
				state.badConnectionCount++;
			}
		}
	}

	private MyPooledConnection popConnection(String username, String password) throws SQLException {
		MyPooledConnection conn = null;
		long t = System.currentTimeMillis();
		int localBadConnectionCount = 0;
		while (conn == null) {
			synchronized (state) {
				if (!state.idleConnections.isEmpty()) {
					conn = state.idleConnections.remove(0);
				} else {
					if (state.activeConnections.size() < poolMaximumActiveConnections) {
						conn = new MyPooledConnection(dataSource.getConnection(), this);
					} else {
						MyPooledConnection oldestActiveConnection = state.activeConnections.get(0);
						long longestCheckoutTime = oldestActiveConnection.getCheckoutTime();
						if (longestCheckoutTime > poolMaximumCheckoutTime) {

						}
					}
				}

				if (conn != null) {
					if (conn.isValid()) {
						if (!conn.getRealConnection().getAutoCommit()) {
							conn.getRealConnection().rollback();
						}
						conn.setConnectionTypeCode(assembleConnectionTypeCode(dataSource.getUrl(), username, password));
						conn.setCheckoutTimestamp(System.currentTimeMillis());
						conn.setLastUsedTimestamp(System.currentTimeMillis());
						state.activeConnections.add(conn);
						state.requestCount++;
						state.accumulatedRequestTime += System.currentTimeMillis() - t;
					} else {
						state.badConnectionCount++;
						localBadConnectionCount++;
						conn = null;
						if (localBadConnectionCount > (poolMaximumIdleConnections + 3)) {
							throw new SQLException(
									"PooledDataSource: Could not get a good connection to the database.");
						}
					}
				}
			}
		}

		return conn;
	}

	private int assembleConnectionTypeCode(String url, String username, String password) {
		return ("" + url + username + password).hashCode();
	}

	protected boolean pingConnection(MyPooledConnection conn) {
		boolean result = true;
		try {
			result = !conn.getRealConnection().isClosed();
		} catch (SQLException e) {
			result = false;
		}

		if (result) {
			if (poolPingEnabled) {
				if (poolPingConnectionsNotUsedFor >= 0
						&& conn.getTimeElapsedSinceLastUse() > poolPingConnectionsNotUsedFor) {
					try {
						Connection realConn = conn.getRealConnection();
						Statement statement = realConn.createStatement();
						ResultSet rs = statement.executeQuery(poolPingQuery);
						rs.close();
						statement.close();
						if (!realConn.getAutoCommit()) {
							realConn.rollback();
						}
						result = true;
					} catch (SQLException e) {
						try {
							conn.getRealConnection().close();
						} catch (Exception e2) {
							// ignore
						}
						result = false;
					}
				}
			}
		}

		return result;
	}

	public void setPoolPingConnectionsNotUsedFor(int milliseconds) {
		this.poolPingConnectionsNotUsedFor = milliseconds;
		// forceCloseAll();
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoginTimeout(int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


}
