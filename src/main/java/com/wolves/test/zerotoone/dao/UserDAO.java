package com.wolves.test.zerotoone.dao;

import java.util.Map;

import com.wolves.test.zerotoone.vo.User;
import com.wolves.zerotoone.orm.annotation.Sql;

public interface UserDAO {
	public User getUserById(String id);
	
	public int insertUser(Map<String, Object> map);

	public int updateUser(User user);

}
