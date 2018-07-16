package com.miniworld.jdbcTemplatedao;

public interface AdminUserDao {

	void createTable(String username, String password, String realname, String keychain, int roleId, long createTime);

	String getTokenById(int adminId);

	void initialDatabase();
	
	boolean isExistsDatabase();
	
}
