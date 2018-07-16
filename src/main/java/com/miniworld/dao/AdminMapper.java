package com.miniworld.dao;

import java.util.List;

import com.miniworld.entity.AdminUser;

public interface AdminMapper {

	AdminUser selectByPrimaryKey(Integer adminId);
    
	AdminUser selectByName(String adminLoginName);

	Integer updateByPrimaryKeySelective(AdminUser adminUser);

	List<AdminUser> selectAll();

	Integer deleteByPrimaryKey(Integer adminId);

	Integer insertAdminUser(AdminUser adminUser);

}
