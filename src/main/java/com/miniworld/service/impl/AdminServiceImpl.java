package com.miniworld.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniworld.dao.AdminMapper;
import com.miniworld.entity.AdminUser;
import com.miniworld.service.AdminService;


@Service("adminService")
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminMapper adminMapper;
	
	public AdminUser getUserByName(String adminLoginName){
		return adminMapper.selectByName(adminLoginName);
	}	

	public List<AdminUser> getAdminUserList() {
		return adminMapper.selectAll();
	}

	public AdminUser getUserById(Integer adminId) {
		return adminMapper.selectByPrimaryKey(adminId);
	}

	public Integer updateAdminUser(AdminUser adminUser) {
		return adminMapper.updateByPrimaryKeySelective(adminUser);
	}

	public Integer deleteAdminUser(Integer adminId) {
		return adminMapper.deleteByPrimaryKey(adminId);
	}

	public Integer addAdminUser(AdminUser adminUser) {
		return adminMapper.insertAdminUser(adminUser);
	}
}

