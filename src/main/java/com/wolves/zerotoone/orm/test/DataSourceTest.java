package com.wolves.zerotoone.orm.test;

import java.io.InputStream;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.wolves.test.zerotoone.dao.UserDAO;
import com.wolves.zerotoone.orm.session.MySqlSession;
import com.wolves.zerotoone.orm.session.factory.impl.MySessionFactory;
import com.wolves.zerotoone.orm.session.factory.impl.MySessionFactoryBuilder;
import com.wolves.zerotoone.orm.session.factory.impl.SessionFactory;

public class DataSourceTest {
	private static Reader reader;
	private static SqlSessionFactory sqlSessionFactory;
	private static SessionFactory sessionfactory;
	private static ClassLoader loader = ClassLoader.getSystemClassLoader();
	static {
		try {
//			 reader = Resources.getResourceAsReader("conf/orm.xml");
//			 sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			InputStream stream = loader.getResourceAsStream("conf/orm.xml");
			sessionfactory = new MySessionFactoryBuilder().build(stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws Exception {
		
		for (int i = 0; i < 20; i++) {
			MySqlSession session = sessionfactory.openSession();
			UserDAO userDao = session.getMapper(UserDAO.class);
			userDao.getUserById("297e7fa53bae1441013bb28e73835ffb");
			System.out.println("================="+i+"=================");
		}

//		 SqlSession openSession = sqlSessionFactory.openSession();
//		 UserDAO userDao = openSession.getMapper(UserDAO.class);
//		 userDao.getUserById("297e7fa53bae1441013bb28e73835ffb");

	}

}
