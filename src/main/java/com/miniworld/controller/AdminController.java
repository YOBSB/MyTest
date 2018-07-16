package com.miniworld.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miniworld.common.ReMessage;
import com.miniworld.entity.AdminUser;
import com.miniworld.service.AdminService;
import com.miniworld.utils.EnDecryptUtil;
import com.miniworld.utils.sessionaop.SysContent;

/**
 * 管理员模块管理
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AdminService adminService;
	
	
	/**
	 * 登录
	 * @param adminLoginName 管理员登录名
	 * @param adminPassword  管理员密码
	 * @param model          
	 * @param session
     * @return 返回是否操作成功
     * code:0 登录成功
     * code:1 登录失败
	 */
	@RequestMapping("/login")
	@ResponseBody
	public ReMessage<?> login(String adminLoginName,String adminPassword,Model model,HttpSession session){
		logger.info("登录请求，验证登录中");
		AdminUser adminUser = adminService.getUserByName(adminLoginName);
		if(adminUser == null){
			logger.info("登录请求，用户名不存在,登录失败！");
			model.addAttribute("result", "用户名不存在");
			return new ReMessage<>(1, "用户名不存在！");
		}
		if(adminUser.getAdminPassword().equals(EnDecryptUtil.SHA1MD(adminPassword))){
			logger.info("登录请求，登录成功！");
			session.setAttribute("adminUserId", adminUser.getAdminId());
			session.setAttribute("adminLoginName", adminUser.getAdminLoginName());
			session.setAttribute("adminRoleId", adminUser.getAdminRoleId());
			session.setAttribute("keyChain", adminUser.getKeyChain());
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("adminUserId", adminUser.getAdminId());
			map.put("adminLoginName", adminLoginName);
			map.put("adminRoleId",adminUser.getAdminRoleId());
			map.put("keyChain", adminUser.getKeyChain());
			if(adminUser.getAdminRoleId()==1) {
				return new ReMessage<>(0, "登录成功！",map);
			}else {
				return new ReMessage<>(0, "登录成功！",map);
			}
		}else {
			logger.info("登录请求，用户名或密码错误,登录失败！");
			model.addAttribute("result", "用户名或密码错误");
			return new ReMessage<>(1, "用户名或密码错误！");
		}
	}
	
	/**
	 * 跳转到用户管理页面
	 * @param session
	 * @param model
     * @return 返回路径
     * 超级管理员返回"admin/manage"
     * 普通管理员返回"redirect:/formReview"
	 */
	@RequestMapping("/home")
	public String index(HttpSession session,Model model){
		if(com.miniworld.utils.AuthorityUtil.isSupperAdmin()) {
			logger.info("登录跳转，检测为超级管理员，获取用户表单，准备跳转到用户管理页面");
		    List<AdminUser> list = adminService.getAdminUserList();
		    int adminUserId = (int) session.getAttribute("adminUserId");
		    AdminUser adminUser = adminService.getUserById(adminUserId);
		    model.addAttribute("adminUser", adminUser);	    
		    model.addAttribute("list", list);
			return "admin/manage";
		}else {
			return "redirect:/formReview";
		}
	}
	
	/**
	 * 跳转到修改用户页面
	 * @param adminId
	 * @param model
	 * @return 返回路径
	 * 超级管理员返回路径"admin/update"
	 * 普通管理员返回路径"admin/login"
	 */
	@RequestMapping("/upUser")
	public String upUser(Integer adminId,Model model) {
		AdminUser adminUser = adminService.getUserById(adminId);
	    model.addAttribute("adminUser", adminUser);
		logger.info("跳转修改请求，检测为超级管理员，获取用户信息，准备跳转到用户修改页面");
		return "admin/update";
	}
	
	
	/**
	 * 修改用户信息
	 * @param AdminUser
	 * @return 返回操作信息
	 * code 0:修改成功
	 * code 1:修改失败
	 */
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	@ResponseBody
	public ReMessage<?> update(AdminUser adminUser){
		logger.info("修改请求，检测为超级管理员，进行表单校验");
//		isLegel(adminUser);
		if(adminUser.getAdminLoginName()==null||adminUser.getAdminPassword()==null||adminUser.getAdminRealName()==null
				||adminUser.getAdminLoginName().trim().isEmpty()||adminUser.getAdminPassword().trim().isEmpty()
				||adminUser.getAdminRealName().trim().isEmpty()) {
			logger.info("注册表单校验，表单信息为空");
			return new ReMessage<>(1,"表单信息不能为空");
		}
		if(adminUser.getAdminLoginName().matches("^[A-Za-z0-9]{1,32}$")) {
			logger.info("注册表单登录名校验，合法");
		}else {
			logger.info("注册表单登录名校验，存在非法字符");
			return new ReMessage<>(1,"登录名存在非法字符");
		}
		if(adminUser.getAdminRealName().matches("^[\\u4e00-\\u9fa5]{1,32}$")) {
			logger.info("注册表单真实姓名校验，合法");
		}else {
			logger.info("注册表单真实姓名校验，存在非法字符");
			return new ReMessage<>(1,"真实姓名存在非法字符");
		}
		if(adminUser.getAdminPassword().matches("^.{6,20}$")) {
			logger.info("注册表单密码校验，合法");
		}else {
			logger.info("注册表单密码校验，存在非法字符");
			return new ReMessage<>(1,"密码存在非法字符");
		}
		if(adminUser.getAdminRoleId()==0) {
			logger.info("注册表单管理员权限校验，合法");
		}else {
			logger.info("注册表单管理员权限校验，非法权限");
			return new ReMessage<>(1,"非法权限");
		}
		//获取时间戳
		Long currentTime = System.currentTimeMillis();
        adminUser.setUpdateTime(currentTime);
        try {
        	adminService.updateAdminUser(adminUser);
        	return new ReMessage<>(0,"修改成功");
		} catch (Exception e) {
			logger.error("updateUser error:%s\n", e.getMessage());
			return new ReMessage<>(1,"修改失败"+e.getMessage());
		}
	}
	
	/**
	 * 删除选中用户
	 * @param adminId
	 * @return 返回操作信息
	 * code 0:删除成功
	 * code 1:权限不足，操作失败
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ReMessage<?> delete(Integer adminId){
		logger.info("删除请求，检测为超级管理员，执行删除操作");
		try {
			adminService.deleteAdminUser(adminId);
			return new ReMessage<>(0,"删除成功");
		} catch (Exception e) {
			logger.error("deleteUser error:%s\n", e.getMessage());
			return new ReMessage<>(1,"删除失败"+e.getMessage());
		}
	}
	
	/**
	 * 跳转到注册用户页面
	 * @return 返回跳转路径
	 * 超级管理员返回"admin/register"
	 * 普通管理员返回"admin/login"
	 */
	@RequestMapping("/toRegister")
	public String toRegister() {
		logger.info("跳转注册请求，检测为超级管理员，执行跳转");
		return "admin/register";
	}
	
	/**
	 * 注册用户
	 * @param adminUser 表单中的信息
	 * @param model
	 * @return 返回操作结果信息
	 * code 0: 注册成功
	 * code 1: 注册失败
	 */
	@RequestMapping("/register")
	@ResponseBody
	public ReMessage<?> register(AdminUser adminUser,Model model){
		logger.info("注册请求，检测为超级管理员，进行表单校验");
		AdminUser au = adminService.getUserByName(adminUser.getAdminLoginName());
		if(au != null){
			model.addAttribute("result", "该用户名已被注册");
			logger.info("注册表单校验，该用户名已被注册");
			return new ReMessage<>(1,"该用户名已被注册");
		}
//		isLegel(adminUser);
		if(adminUser.getAdminLoginName()==null||adminUser.getAdminPassword()==null||adminUser.getAdminRealName()==null
				||adminUser.getAdminLoginName().trim().isEmpty()||adminUser.getAdminPassword().trim().isEmpty()
				||adminUser.getAdminRealName().trim().isEmpty()) {
			logger.info("注册表单校验，表单信息为空");
			return new ReMessage<>(1,"表单信息不能为空");
		}
		if(adminUser.getAdminLoginName().matches("^[A-Za-z0-9]{1,32}$")) {
			logger.info("注册表单登录名校验，合法");
		}else {
			logger.info("注册表单登录名校验，存在非法字符");
			return new ReMessage<>(1,"登录名存在非法字符");
		}
		if(adminUser.getAdminRealName().matches("^[\\u4e00-\\u9fa5]{1,32}$")) {
			logger.info("注册表单真实姓名校验，合法");
		}else {
			logger.info("注册表单真实姓名校验，存在非法字符");
			return new ReMessage<>(1,"真实姓名存在非法字符");
		}
		if(adminUser.getAdminPassword().matches("^.{6,20}$")) {
			logger.info("注册表单密码校验，合法");
		}else {
			logger.info("注册表单密码校验，存在非法字符");
			return new ReMessage<>(1,"密码存在非法字符");
		}
		if(adminUser.getAdminRoleId()==0) {
			logger.info("注册表单管理员权限校验，合法");
		}else {
			logger.info("注册表单管理员权限校验，非法权限");
			return new ReMessage<>(1,"非法权限");
		}
        //获取时间戳
		Long currentTime = System.currentTimeMillis();
        adminUser.setCreateTime(currentTime);
        //加密密码
        adminUser.setAdminPassword(EnDecryptUtil.SHA1MD(adminUser.getAdminPassword()));
        try {
        	adminService.addAdminUser(adminUser);
        	logger.info("注册请求，注册成功");
        	model.addAttribute("result", "注册成功");
        	return new ReMessage<>(0,"注册成功");
		} catch (Exception e) {
			logger.error("registerUser error:%s\n", e.getMessage());
			return new ReMessage<>(1,"注册失败"+e.getMessage());
		}
	}

//	/**
//	 * 检测创建用户与修改用户表单的非空、合法性
//	 * @param adminUser
//	 * @return
//	 */
//	public Object isLegel(AdminUser adminUser) {
//		if(adminUser.getAdminLoginName()==null||adminUser.getAdminPassword()==null||adminUser.getAdminRealName()==null
//				||adminUser.getAdminLoginName().trim().isEmpty()||adminUser.getAdminPassword().trim().isEmpty()
//				||adminUser.getAdminRealName().trim().isEmpty()) {
//			logger.info("注册表单校验，表单信息为空");
//			return new ReMessage<>(1,"表单信息不能为空");
//		}
//		if(adminUser.getAdminLoginName().matches("^[A-Za-z0-9]{1,32}$")) {
//			logger.info("注册表单登录名校验，合法");
//		}else {
//			logger.info("注册表单登录名校验，存在非法字符");
//			return new ReMessage<>(1,"登录名存在非法字符");
//		}
//		if(adminUser.getAdminRealName().matches("^[\\u4e00-\\u9fa5]{1,32}$")) {
//			logger.info("注册表单真实姓名校验，合法");
//		}else {
//			logger.info("注册表单真实姓名校验，存在非法字符");
//			return new ReMessage<>(1,"真实姓名存在非法字符");
//		}
//		if(adminUser.getAdminPassword().matches("^.{6,20}$")) {
//			logger.info("注册表单密码校验，合法");
//		}else {
//			logger.info("注册表单密码校验，存在非法字符");
//			return new ReMessage<>(1,"密码存在非法字符");
//		}
//		if(adminUser.getAdminRoleId()==0) {
//			logger.info("注册表单管理员权限校验，合法");
//		}else {
//			logger.info("注册表单管理员权限校验，非法权限");
//			return new ReMessage<>(1,"非法权限");
//		}
//		return new ReMessage<>(0,"表单字段合法");
//	}
	
	/**
	 * 拦截跳转页面/退出登录，返回登录页面
	 * @param session
	 * @return 返回跳转路径"admin/login"
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		logger.info("删除已有session，进入登录页面");
		session.invalidate();
		return "admin/login";
	}
	
	/**
	 * 进入登录页面
	 * @return 返回路径
	 * session不存在返回跳转路径"admin/login"
	 * 若session存在：
	 * 		超级管理员返回"redirect:/admin/home"
	 * 		普通管理员返回"redirect:/formReview"
	 */
	@RequestMapping("/toLogin")
	public String toLogin() {
		Integer roleId = (Integer) SysContent.getSession().getAttribute("adminRoleId");
		if(roleId != null ){
			if(roleId == 1) {
				return "redirect:/admin/home";
			}else {
				return "redirect:/formReview";
			}
		}
		return "admin/login";
	}
	

	
}
