package com.miniworld.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniworld.jdbcTemplatedao.AdminUserDao;
import com.miniworld.service.AdminUserService;

@Service("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {

	@Autowired
	private AdminUserDao adminUserDao;
	
	@Override
	public String getTokenById(int adminId) {
		// TODO Auto-generated method stub
		return adminUserDao.getTokenById(adminId);
		
	}

}
