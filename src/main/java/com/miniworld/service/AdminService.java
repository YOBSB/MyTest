package com.miniworld.service;

import java.util.List;

import com.miniworld.entity.AdminUser;

public interface AdminService {

	AdminUser getUserByName(String adminLoginName);

	List<AdminUser> getAdminUserList();

	AdminUser getUserById(Integer adminId);

	Integer updateAdminUser(AdminUser adminUser);

	Integer deleteAdminUser(Integer adminId);

	Integer addAdminUser(AdminUser adminUser);

}
