package com.miniworld.service;

public interface InitialService {

	void createTable(String username, String password, String realname, String keychain, int roleId, long createTime);

	void createTable();

	void initialDatabase();
	
	boolean existsDatabase();
}
