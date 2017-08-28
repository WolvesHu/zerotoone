package com.wolves.test.zerotoone.dao;

import java.util.Map;

import com.wolves.test.zerotoone.vo.User;

public interface UserDAO {
	public User getUserById(String id);
	
	public int insertUser(Map<String, Object> map);

	public int updateUser(User user);

}
