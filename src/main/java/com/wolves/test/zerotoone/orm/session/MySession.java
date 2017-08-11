package com.wolves.test.zerotoone.orm.session;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.wolves.test.zerotoone.orm.proxy.MapperProxy;

public class MySession<T> implements Session<T> {
	private DataSource dataSource;
	private Connection conn;

	public MySession(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Connection getConnection() throws SQLException {
		if (conn == null) {
			conn = dataSource.getConnection();
		}
		return conn;
	}

	@Override
	public void exec(String sql) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 从连接中创建一个statement
			stmt = getConnection().createStatement();
			// statement执行一句sql查询
			rs = stmt.executeQuery(sql);
			// 结果的元数据，包括别名、列名、类型等
			ResultSetMetaData rsmd = rs.getMetaData();
			// 将所有结果输出
			if (rs.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					System.out.println(rsmd.getColumnLabel(i) + "=" + rs.getObject(i));
				}
				System.out.println();
			}
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
		}
	}
	
	@Override
	public T getMapper(Class<T> clazz) {
		return MapperProxy.newInstance(clazz, this);
	}    

}
