
package com.miniworld.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Resource;

import com.miniworld.common.TaskManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



import com.miniworld.jdbcTemplatedao.MatchDao;
import com.miniworld.jdbcTemplatedao.SeasonDao;
import com.miniworld.service.InitialService;
import com.miniworld.utils.EnDecryptUtil;

@Controller
@RequestMapping("/initializeController")
public class InitialController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private InitialService initialService;

	@Resource
	private SeasonDao seasonDao;

	@Resource
	private MatchDao matchDao;

	@Resource
	private TaskManager taskManager;

	public InitialService getInitialService() {
		return initialService;
	}

	public void setInitialService(InitialService initialService) {
		this.initialService = initialService;
	}

	@RequestMapping(value ="/initial.do" ,method = RequestMethod.POST)
	public String initial(
			@RequestParam(value="super_admin_username") String username,
			@RequestParam(value="super_admin_password") String password,
			@RequestParam(value="super_admin_realname") String realname,
			@RequestParam(value="super_admin_keychain") String keychain){
		if(initialService.existsDatabase()) {
			logger.info("进入初始化方法");
			int roleId=1; //1代表超级管理员角色id，2为普通管理员
			long createTime=System.currentTimeMillis();
			//加密密码
			String password1=EnDecryptUtil.SHA1MD(password);
			try{
				initialService.initialDatabase();
				initialService.createTable(username,password1,realname,keychain,roleId,createTime);
				seasonDao.createTable();
				matchDao.createTable();
			}catch(Exception e){
				logger.error(e.getMessage());
			}
		}else {
			return "redirect:/admin/logout";
		}


		return "admin/login";
	}

	@RequestMapping("/initialView.do")
	public String initialView(){
		return "initial";
	}


}
