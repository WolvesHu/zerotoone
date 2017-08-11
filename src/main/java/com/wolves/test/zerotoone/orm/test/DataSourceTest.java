package com.wolves.test.zerotoone.orm.test;

import com.wolves.test.zerotoone.dao.UserDAO;
import com.wolves.test.zerotoone.orm.session.Session;
import com.wolves.test.zerotoone.orm.session.factory.MySessionFactory;
import com.wolves.test.zerotoone.orm.session.factory.SessionFactory;

public class DataSourceTest {

	public static void main(String args[]) throws Exception {
		SessionFactory factory = new MySessionFactory("conf/orm.xml");
		Session<UserDAO> session = factory.openSession();
		UserDAO userDao = session.getMapper(UserDAO.class);
		userDao.getUserById("297e7fa53bae1441013bb28e73835ffb");
	}

}
