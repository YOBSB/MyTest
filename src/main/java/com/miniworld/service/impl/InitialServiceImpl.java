package com.miniworld.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniworld.jdbcTemplatedao.AdminUserDao;
import com.miniworld.service.InitialService;

@Service("initialService")
public class InitialServiceImpl implements InitialService {
	
	@Autowired
	private AdminUserDao adminUserDao;
	
	

	public AdminUserDao getAdminUserDao() {
		return adminUserDao;
	}



	public void setAdminUserDao(AdminUserDao adminUserDao) {
		this.adminUserDao = adminUserDao;
	}



	@Override
	public void createTable(String username, String password, String realname, String keychain, int roleId,
			long createTime) {
		// TODO Auto-generated method stub
		adminUserDao.createTable(username,password,realname,keychain,roleId,createTime);
		
	}



	@Override
	public void createTable() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void initialDatabase() {
		// TODO Auto-generated method stub
		adminUserDao.initialDatabase();
	}



	@Override
	public boolean existsDatabase() {
		//存在数据库，返回false,不存在返回true
		if(adminUserDao.isExistsDatabase()) {
			return true;
		}else {
			return false;
		}
	}
}
