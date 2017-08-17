package com.wolves.zerotoone.orm.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCtest {
	public static void main(String[] args) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		try {
			//加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			Long beforeTimeOffset = new Date().getTime();
			String url = "jdbc:mysql://172.16.32.103:3306/envision_track?useUnicode=true"
					+ "&characterEncoding=utf8&allowMultiQueries=true";
			String user = "root";
			String password = "23d8920HSDf7321";
			//建立并获取数据连接
			conn = DriverManager.getConnection(url, user, password);
			Long afterTimeOffset = new Date().getTime();  
			System.out.println("cost:\t" + (afterTimeOffset - beforeTimeOffset)+" ms");  
			//创建JDBCstatement对象
			String sql = "NO PING QUERY SET";
			ps = conn.prepareStatement(sql);
			//设置sql语句传入的参数
//			ps.setString(1, "xc.huahua3");
			//执行sql语句，获取查询结果
			rs = ps.executeQuery();
			ResultSetMetaData data = rs.getMetaData();
			int count = data.getColumnCount();
			//处理结果
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < count; i++) {
					String columnName = data.getColumnName(i + 1);
					map.put(columnName, rs.getString(columnName));
					System.out.println(columnName+"::"+rs.getString(columnName));
				}
				resultList.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//释放资源
			try {
				// 关闭结果集
				if (rs != null) {
					rs.close();
					rs = null;
				}
				// 关闭执行
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
