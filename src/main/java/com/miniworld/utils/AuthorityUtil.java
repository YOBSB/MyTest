package com.miniworld.utils;

import com.miniworld.utils.sessionaop.SysContent;

public class AuthorityUtil {
	//从session中判断用户是否为超级管理，1为超级管理员，其他则不是
	public static boolean isSupperAdmin() {
		int adminRoleId = (int) SysContent.getSession().getAttribute("adminRoleId");
		if(adminRoleId==1) {
			return true;
		}else {
			return false;
		}

	}
}
