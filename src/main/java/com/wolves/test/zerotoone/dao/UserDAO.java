package com.wolves.test.zerotoone.dao;

import java.util.List;
import java.util.Map;

import com.wolves.test.zerotoone.vo.User;
import com.wolves.zerotoone.orm.annotation.Sql;

public interface UserDAO {
	@Sql(value="select * from t_user where id='{0}'")
	public User getUserById(String id);
	
	public int insertUser(Map<String, Object> map);

	public int updateUser(User user);

	public User getUserByLoginName(String loginName);



	public List<User> selectUserList();

	public int delUser();

	public List<User> selectUserListGroupByDeptName();

	public int countUser(User user);

	public List<User> queryDeptByState();

	public List<User> queryUserByUser(User user);

	public List<User> searchUserByMap(Map<String, Object> map);

	/**
	 * 获取改员工的部门领导（PD部门负责人）
	 */
	public List<User> getUserDirector(Map<String, Object> map);

	public List<User> queryDeptByUser(Map<String, Object> map);

	public List<User> returnUserByList(User user);

	public List<User> queryDeptByUserNew(Map<String, Object> map);

	public List<User> queryUserByUserNew(User user);

	public List<User> searchUserAll(Map<String, Object> map);

	public List<User> queryUserByName(String cnnName);

	/**
	 * 根据会议列表和体系查询体系会议管理员
	 */
	public List<User> getSysUserMeetingByCongressSystem(Map<String, Object> map);
}
