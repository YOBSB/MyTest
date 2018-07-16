package com.miniworld.controller;


import java.util.ArrayList;


import java.util.List;


import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;


import com.miniworld.common.PreHonorTopManager;
import com.miniworld.common.TaskManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.servlet.ModelAndView;

import com.miniworld.common.GlobalManager;
import com.miniworld.common.ReMessage;
import com.miniworld.common.SeasonManager;
import com.miniworld.entity.AdminUser;
import com.miniworld.entity.Season;
import com.miniworld.jdbcTemplatedao.HonorTopDao;
import com.miniworld.jdbcTemplatedao.JdbcWorksDao;
import com.miniworld.jdbcTemplatedao.MatchDao;
import com.miniworld.service.AdminService;
import com.miniworld.service.MatchService;
import com.miniworld.service.SeasonService;



@Controller
@RequestMapping("/season")
public class SeasonController{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private GlobalManager globalManager;
	
	@Autowired
	private SeasonService seasonService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private JdbcWorksDao jdbcWorksDao;
	
	@Autowired
	private MatchDao matchDao;
	
	@Autowired
	private HonorTopDao honorTopDao;

	@Resource
	private TaskManager taskManager;

	@Resource
	private PreHonorTopManager preHonorTopManager;
	
	@Resource
	private SeasonManager seasonManager;

	
	/**
	 * 跳转到创建赛季页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/createSeasonView.do")
	public ModelAndView showCreateSeasonView(
			HttpServletRequest request){
		logger.info("进入跳转创建赛季页面方法");
		ModelAndView mv=new ModelAndView();
		logger.info("跳转到创建赛季页面");
		mv.addObject("sId", globalManager.getEventId());
		mv.addObject("sName", globalManager.getEventSysName());
		mv.setViewName("season/createSeason");
		
		return mv;
	}
	
	/**
	 * 创建赛季操作
	 * @param seasonId
	 * @param seasonName
	 * @param seasonDescription
	 * @param seasonKeyWords
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping("/createSeason.do")
	@ResponseBody
	public ReMessage createSeason(@RequestParam(value="seasonId") String seasonId,@RequestParam(value="seasonName") String seasonName,
			@RequestParam(value="seasonDescription") String seasonDescription,@RequestParam(value="seasonKeyWords") String seasonKeyWords,
			@RequestParam(value="startTime") String startTime,@RequestParam(value="endTime") String endTime,
			@RequestParam(value="submitStartTime") String submitStartTime,@RequestParam(value="submitEndTime") String submitEndTime
			){
		
		logger.info("进入创建赛季操作方法");
		
			//校验当前是否有活跃赛季
			String seasonId1=seasonService.judgeSeasonLife();  //用于获取当前活跃状态的赛季
			if(seasonId1!=null){
				return new ReMessage(4,"操作失败，需要手动结束当前活跃赛季");
			}
		//判断传入数据是否为空
		if(seasonId.trim().isEmpty()||seasonId==null||seasonName.trim().isEmpty()||seasonName==null
				||seasonDescription.trim().isEmpty()||seasonDescription==null||seasonKeyWords.trim().isEmpty()
				||seasonKeyWords==null||startTime.trim().isEmpty()||startTime==null||endTime.trim().isEmpty()||endTime==null
				||submitEndTime==null||submitEndTime.trim().isEmpty()||submitStartTime==null||submitStartTime.trim().isEmpty()){
			return new ReMessage(3,"输入内容不能为空");
		}
		//校验赛季id格式是否正确
		String regex="^[A-Za-z0-9]+$";
		if(!seasonId.matches(regex)){
			return new ReMessage(3,"操作失败，输入赛季id格式不对");
		}
		Season season=seasonService.querySeasonById(seasonId);
		if(season!=null){
			return new ReMessage(3,"操作失败，该赛季id已存在！！");
		}
		//校验时间合法性
		Long startTime1;
		Long endTime1;
		Long submitStartTime1;
		Long submitEndTime1;
		try{
			startTime1=Long.parseLong(startTime);
			endTime1=Long.parseLong(endTime);
			submitStartTime1=Long.parseLong(submitStartTime);
			submitEndTime1=Long.parseLong(submitEndTime);
	        if(endTime1<startTime1){
	        	return new ReMessage(3,"操作失败，赛季开始时间不能大于赛季结束时间");
	        }
	        if(submitEndTime1<submitStartTime1){
	        	return new ReMessage(3,"操作失败，投稿开始时间不能大于投稿结束时间");
	        }
		}catch(NumberFormatException e){
			return new ReMessage(3,"操作失败，输入时间格式有误");
		}
		//创建赛季，动态创建作品表以及荣誉榜单
        int seasonLife=1;     //赛季存活状态 1为存活，0为已结束赛季
        try{
        	seasonService.createSerson(seasonId,seasonName,seasonDescription,seasonKeyWords,startTime1,endTime1,System.currentTimeMillis(),System.currentTimeMillis(),seasonLife,submitStartTime1,submitEndTime1);
        	jdbcWorksDao.createTableBySeasonId(seasonId);
            honorTopDao.createTableBySeasonId(seasonId);
        }catch(Exception e){
        	logger.error(e.getMessage());
        	return new ReMessage(2,"操作失败，服务器异常");
        }
        //赋予全局赛事id.全局赛事开始时间，全局赛事结束时间。
        seasonManager.eventStart(seasonId,seasonName, startTime1,endTime1,submitStartTime1,submitEndTime1);
        preHonorTopManager.update();
        logger.info("创建赛季成功");
        return new ReMessage(0,"创建成功");
		}
	
	
	
	/**
	 * 
	 * 用于异步创建新赛季时判断赛季id合法性,判断id是否已存在，形式是否正确
	 * @param seasonId
	 * @return
	 */
	@RequestMapping("/judgeSeasonId")
	@ResponseBody
    public ReMessage judgeSeasonId(@RequestParam(value="seasonId") String seasonId) {
	        Season season=seasonService.querySeasonById(seasonId);
	        String regex="^[A-Za-z0-9]+$";
	        if(season!=null){
	        	return new ReMessage(1,"赛季id已存在！请重新输入！");
	        }else if(!seasonId.matches(regex)){
	        	return new ReMessage(2,"赛季id只能为数字跟字母！");
	        }else{
	        	return new ReMessage(0,"赛季id可用");
	        }   
	  }
	
	/**
	 * 用于异步判断是否已存在某个活跃的赛季
	 * @return
	 */
	@RequestMapping("/judgeLife")
	@ResponseBody
	public ReMessage judgeSeasonLife(){
		String seasonId=seasonService.judgeSeasonLife();
		if(seasonId!=null){
			return new ReMessage(1,"当前存在活跃的赛事,id为："+seasonId+" 请手动结束该赛季方可创建！");
		}else{
			return new ReMessage(0,"可创建赛季");
		}
		
	}
	
	
	
	/**
	 * 跳转显示season列表
	 * @param indexPage
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/showSeasonView.do")
	public ModelAndView showSeasonView(@RequestParam(value="indexPage",defaultValue="1") int indexPage,
			HttpSession session){
		ModelAndView mv=new ModelAndView();
		Integer adminRoleId=(Integer)session.getAttribute("adminRoleId");
		mv.setViewName("season/season");
	    List<Season> list=seasonService.querySeason();
	    mv.addObject("sId", globalManager.getEventId());
		mv.addObject("sName", globalManager.getEventSysName());
	    mv.addObject("adminRoleId",adminRoleId);
		mv.addObject("list", list);
		return mv;
	}
	
	
	/**
	 * 显示更新赛季页面信息
	 * @param seasonId
	 * @return
	 */
	@RequestMapping("/updateSeasonView")
	public ModelAndView updateSeasonView(@RequestParam(value="seasonId") String seasonId){
		ModelAndView mv=new ModelAndView();
		try{
			Season season=seasonService.querySeasonById(seasonId);
			mv.addObject("season", season);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		mv.addObject("sId", globalManager.getEventId());
		mv.addObject("sName", globalManager.getEventSysName());
        mv.setViewName("season/update");		
		
		return mv;
	}
	
	
	
	/**
	 * 赛季更新方法
	 * @param originalSeasonId
	 * @param updateSeasonId
	 * @param seasonName
	 * @param seasonDescription
	 * @param seasonKeyWords
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping("/updateSeason")
	@ResponseBody
	public ReMessage updateSeason(@RequestParam(value="originalSeasonId") String originalSeasonId,
			@RequestParam(value="updateSeasonId") String updateSeasonId,
			@RequestParam(value="seasonName") String seasonName,
			@RequestParam(value="seasonDescription") String seasonDescription,
			@RequestParam(value="seasonKeyWords") String seasonKeyWords,
			@RequestParam(value="startTime") String startTime,
			@RequestParam(value="endTime") String endTime,
			@RequestParam(value="submitStartTime") String submitStartTime,
			@RequestParam(value="submitEndTime") String submitEndTime){
		//判断输入内容是否为空
		if(updateSeasonId.equals("")||updateSeasonId==null||seasonName.equals("")||seasonName==null
				||seasonDescription.equals("")||seasonDescription==null||seasonKeyWords.equals("")
				||seasonKeyWords==null||startTime.equals("")||startTime==null||endTime.equals("")||endTime==null
				||submitStartTime==null||submitStartTime.equals("")||submitEndTime==null||submitEndTime.equals("")){
			return new ReMessage(3,"输入内容不能为空");
		}
		
		//判断时间输入合法性
		Long startTime1;
		Long endTime1;
		Long submitStartTime1;
		Long submitEndTime1;
		try{
			startTime1=Long.parseLong(startTime);
			endTime1=Long.parseLong(endTime);
			submitStartTime1=Long.parseLong(submitStartTime);
			submitEndTime1=Long.parseLong(submitEndTime);
	        if(endTime1<startTime1){
	        	return new ReMessage(3,"操作失败，赛季开始时间不能大于赛季结束时间");
	        }
	        if(submitEndTime1<submitStartTime1){
	        	return new ReMessage(3,"操作失败，投稿开始时间不能大于投稿结束时间");
	        }
		}catch(NumberFormatException e){
			return new ReMessage(3,"操作失败，输入时间格式有误");
		}
		Season season=new Season();
		season.setId(updateSeasonId);
		season.setSeasonName(seasonName);
		season.setSeasonDescription(seasonDescription);
		season.setSeasonKeyWords(seasonKeyWords);
		season.setStartTime(startTime1);
		season.setEndTime(endTime1);
		season.setUpdateTime((System.currentTimeMillis()));
		season.setSubmitStartTime(submitStartTime1);
		season.setSubmitEndTime(submitEndTime1);
		try{
			seasonService.updateSeason(season,originalSeasonId);
			if(globalManager.getEventId().equals(originalSeasonId)){
				//赋予全局赛事id.全局赛事开始时间，全局赛事结束时间。
				seasonManager.eventStart(originalSeasonId,seasonName, startTime1,endTime1,submitStartTime1,submitEndTime1);
			}
            preHonorTopManager.update();
			}catch(Exception e){
				logger.error("更新赛季失败"+e.getMessage());
				return new ReMessage(2,"操作失败，服务异常");
			}
			logger.info("修改赛季成功");
			return new ReMessage(0,"操作成功");
		
	}
	
	
	/**
	 * 根据id删除赛季
	 * @param seasonId
	 * @return
	 */
	@RequestMapping("/deleteSeasonView")
	@ResponseBody
	public ReMessage deleteSeason(@RequestParam(value="seasonId") String seasonId){
		logger.info("进入删除赛季方法");
		try{
			seasonService.deleteSeasonById(seasonId);
			if(seasonId.equals(globalManager.getEventId())) {
				seasonManager.eventEnd();
			}
			preHonorTopManager.update();
		}catch(Exception e){
			logger.error("删除赛季失败失败"+e.getMessage());
			return new ReMessage(2,"操作失败，服务异常");
		}
		logger.info("删除赛季成功");	
		return new ReMessage(0,"操作成功");
		
	}
	
	
	/**
	 * 结束赛季
	 * @param seasonId
	 * @return
	 */
	@RequestMapping("/stopSeasonView")
	@ResponseBody
	public ReMessage stopSeason(@RequestParam(value="seasonId") String seasonId){
		try{
			seasonService.stopSeasonById(seasonId);
			seasonManager.eventEnd();
			preHonorTopManager.update();
		}catch(RuntimeException e){
			logger.error("操作数据库异常"+e.getMessage());
			return new ReMessage(2,"操作失败，服务器异常");
		}
		
		logger.info("删除赛季成功");
		return new ReMessage(0,"操作成功");
	}
	
	/**
	 * 开启赛季
	 * @param seasonId
	 * @return
	 */
	@RequestMapping("/startSeasonView")
	@ResponseBody
	public ReMessage startSeason(@RequestParam(value="seasonId") String seasonId){
		String sid = globalManager.getEventId();
		if(com.miniworld.utils.ActiveSeasonUtil.isActiveSeason(sid)) {
		System.out.println(seasonId);
			Season season = seasonService.querySeasonById(seasonId);
				try{
					seasonService.startSeasonById(seasonId);
					seasonManager.eventStart(seasonId,season.getSeasonName(), season.getStartTime(),season.getEndTime(),season.getSubmitStartTime(),season.getSubmitEndTime());
                    preHonorTopManager.update();
				}catch(Exception e){
					logger.error("操作数据库异常"+e.getMessage());
					return new ReMessage(2,"操作失败，服务器异常");
				}
				logger.info("开启赛季成功");
				return new ReMessage(0,"操作成功");
		}else {
			return new ReMessage(1,"当前存在活跃的赛事,id为："+sid+" 请手动结束该赛季！");
		}
	}
	

	/**
	 * 校验输入时间合法性方法
	 * @param startTime 时间字符串，为时间戳数字串
	 * @param endTime   时间字符串，为时间戳数字串
	 * @return
	 * 返回1：赛季开始时间不能小于当前时间
	 * 返回2：赛季结束时间不能小于当前时间
	 * 返回3：赛季开始时间不能大于赛季结束时间
	 * 返回4：输入时间格式有误，不能转化为long型字符串
	 */
	public int judgeTime(String startTime,String endTime){
		long startTime1;
		long endTime1;
		try{
			startTime1=Long.parseLong(startTime);
			endTime1=Long.parseLong(endTime);
			
			long currentTime=System.currentTimeMillis();
			if(startTime1<currentTime){
				//return new ReMessage(4,"操作失败，赛季开始时间不能小于当前时间");
				//返回1
				return 1;     
			}
			if(endTime1<currentTime){
				//return new ReMessage(4,"操作失败，赛季结束时间不能小于当前时间");
				return 2;
	        }
	        if(endTime1<startTime1){
	        	//return new ReMessage(4,"操作失败，赛季开始时间不能大于赛季结束时间");
	        	return 3;
	        }
		}catch(NumberFormatException e){
			//return new ReMessage(4,"操作失败，输入时间格式有误");
			return 4;
		}
		return 0;
	}
	
	
}
