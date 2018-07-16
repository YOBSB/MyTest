package com.miniworld.controller;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.miniworld.common.TaskManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.miniworld.common.GlobalManager;
import com.miniworld.common.ReMessage;
import com.miniworld.entity.Match;
import com.miniworld.jdbcTemplatedao.MatchDao;
import com.miniworld.service.MatchService;
import com.miniworld.utils.CreateHtmlUtil;


@Controller
@RequestMapping("/match")
public class MatchController{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private GlobalManager globalManager;
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private MatchDao matchDao;
	
	@Resource
	private TaskManager taskManager;

	
	
	/**
	 * 保存赛事配置信息，并且生成前台赛事配置主页保存
	 * @param match
	 * @param request
	 * @param state 判断前台传过来是生成移动端页面还是pc端页面 ， 0为移动端，1为pc端
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public ReMessage save(Match match,HttpServletRequest request,
			@RequestParam(value="state") int state
			){
		logger.info("进入赛事配置方法");
		if(com.miniworld.utils.AuthorityUtil.isSupperAdmin()) {
			//判断输入数据是否为空
			if(state==1){
				if(match.getBannerImage()==null||match.getBannerImage().trim().equals("")||match.getGameIntroduce()==null
						||match.getGameIntroduce().trim().equals("")||match.getGameIntroduceImage()==null||match.getGameIntroduceImage().trim().equals("")
						||match.getJudgeGuest()==null||match.getJudgeGuest().trim().equals("")||match.getPartner()==null||match.getPartner().trim().equals("")
						||match.getReview()==null||match.getReview().trim().equals("")||match.getReviewImage()==null||match.getReviewImage().trim().equals("")
						||match.getRewardImage()==null||match.getRewardImage().trim().equals("")||match.getRule()==null||match.getRule().trim().equals("")
						||match.getRuleImage()==null||match.getRuleImage().trim().equals("")||match.getSubmissionIntroduce()==null||match.getSubmissionIntroduce().trim().equals("")
						||match.getSubmissionIntroduceImage().trim().equals("")||match.getSubmissionIntroduceImage()==null||match.getThemeImage()==null||match.getThemeImage().trim().equals("")
						||match.getTimeImage()==null||match.getTimeImage().trim().equals("")||match.getWorksIntroduce()==null||match.getWorksIntroduce().trim().equals("")
						||match.getWorksIntroduceImage()==null||match.getWorksIntroduceImage().trim().equals("")){
					logger.info("输入数据存在空值");
					return new ReMessage(3,"输入数据不能为空");
				}
			}else if(state==0){
				if(match.getMobileBannerImage()==null||match.getMobileBannerImage().trim().equals("")||match.getMobileGameIntroduce()==null
						||match.getMobileGameIntroduce().trim().equals("")||match.getMobileGameIntroduceImage()==null||match.getMobileGameIntroduceImage().trim().equals("")
						||match.getMobileJudgeGuest()==null||match.getMobileJudgeGuest().trim().equals("")||match.getMobilePartner()==null||match.getMobilePartner().trim().equals("")
						||match.getMobileReview()==null||match.getMobileReview().trim().equals("")||match.getMobileReviewImage()==null||match.getMobileReviewImage().trim().equals("")
						||match.getMobileRewardImage()==null||match.getMobileRewardImage().trim().equals("")||match.getMobileRule()==null||match.getMobileRule().trim().equals("")
						||match.getMobileRuleImage()==null||match.getMobileRuleImage().trim().equals("")||match.getMobileSubmissionIntroduce()==null||match.getMobileSubmissionIntroduce().trim().equals("")
						||match.getMobileSubmissionIntroduceImage().trim().equals("")||match.getMobileSubmissionIntroduceImage()==null||match.getMobileThemeImage()==null||match.getMobileThemeImage().trim().equals("")
						||match.getMobileTimeImage()==null||match.getMobileTimeImage().trim().equals("")||match.getMobileWorksIntroduce()==null||match.getMobileWorksIntroduce().trim().equals("")
						||match.getMobileWorksIntroduceImage()==null||match.getMobileWorksIntroduceImage().trim().equals("")){
					logger.info("输入数据存在空值");
					return new ReMessage(3,"输入数据不能为空");
				}
			}
	    	String seasonId = match.getSeasonId();
			Match m = matchService.selectMatchBySeasonId(seasonId);
			if(match.getId()==null) {
				if(m == null) {
					match.setCreateTime(System.currentTimeMillis());
					matchService.addInfo(match);
					Map<String, Object> map = new HashMap<>();
					map.put("match", match); 
					try {
						//state 为前端传过来得判断提交的配置信息是移动端还是pc端， 0为移动端，1为pc端
						if(state==1){
							CreateHtmlUtil.createHtml(state,seasonId,"index.ftl","index.html" , map);   //生产pc端页面
			        		CreateHtmlUtil.createHtml(state,seasonId,"list.ftl","list.html" , map);
			        		CreateHtmlUtil.createHtml(state,seasonId,"works.ftl","works.html" , map);
			        		CreateHtmlUtil.createHtml(state,seasonId,"contribute.ftl","contribute.html" , map);
						}else if(state==0){
							CreateHtmlUtil.createHtml(state,seasonId,"index.ftl","index.html", map);    //生成移动端页面
			        		CreateHtmlUtil.createHtml(state,seasonId,"list.ftl","list.html" , map);
			        		CreateHtmlUtil.createHtml(state,seasonId,"works.ftl","works.html" , map);
			        		CreateHtmlUtil.createHtml(state,seasonId,"contribute.ftl","contribute.html" , map);
						}
					} catch (Exception e) {
						logger.error(e.getMessage());
						return new ReMessage(2,"操作失败，服务器异常");
					}
				}else {
						return new ReMessage(2,"重复提交");
				}
			}else{
				match.setUpdateTime(System.currentTimeMillis());
				matchService.updateInfo(match);
				Map<String, Object> map = new HashMap<>();
		        map.put("match", match); 
		        try {
		        	if(state==1){
		        		CreateHtmlUtil.createHtml(state,seasonId,"index.ftl","index.html" , map);
		        		CreateHtmlUtil.createHtml(state,seasonId,"list.ftl","list.html" , map);
		        		CreateHtmlUtil.createHtml(state,seasonId,"works.ftl","works.html" , map);
		        		CreateHtmlUtil.createHtml(state,seasonId,"contribute.ftl","contribute.html" , map);
		        	}else if(state==0){
		        		CreateHtmlUtil.createHtml(state,seasonId,"index.ftl","index.html", map);
		        		CreateHtmlUtil.createHtml(state,seasonId,"list.ftl","list.html" , map);
		        		CreateHtmlUtil.createHtml(state,seasonId,"works.ftl","works.html" , map);
		        		CreateHtmlUtil.createHtml(state,seasonId,"contribute.ftl","contribute.html" , map);
		        	}
		        } catch (Exception e) {
					logger.error(e.getMessage());
					return new ReMessage(2,"操作失败,服务器异常");
				}
			}
			Map<String,Object> map=new HashMap<String,Object>();
			Match match1=matchDao.queryMatchBySeasonId(match.getSeasonId());
			map.put("match", match1);
			return new ReMessage(0,"操作成功",map);
		}else {
			return new ReMessage(1,"权限不足");
		}
	}
	
	/**
	 * 
	 * 跳转到pc赛事配置页面
	 * @param seasonId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/configPc")
	public String matchconfigView(@RequestParam(value="seasonId") String seasonId,HttpServletRequest request,HttpServletResponse response,Model model) throws IOException{
		HttpSession session=request.getSession();
		if(session==null||session.getAttribute("adminUserId")==null){
			return "redirect:/admin/logout";
		}
		if(com.miniworld.utils.AuthorityUtil.isSupperAdmin()) {
			Match match = matchService.selectMatchBySeasonId(seasonId);
			if(match==null) {
				model.addAttribute("seasonId", seasonId);
			}else {
				model.addAttribute("Id", match.getId());
				model.addAttribute("seasonId", seasonId);
				model.addAttribute("sId", globalManager.getEventId());
				model.addAttribute("sName", globalManager.getEventSysName());
				model.addAttribute("match", match);
			}
			return "season/configPc";
		}else {
			return "redirect:/admin/logout";
		}

	}
	
	/**
	 * 更新赛事配置方法
	 * @param seasonId
	 * @return
	 */
	@RequestMapping("/updateMatch")
	public ReMessage updateMatch(String seasonId){
		logger.info("进入更新赛事配置方法");
		Match match=matchService.selectMatchBySeasonId(seasonId);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("match", match);
		try {
			//生成移动端页面
			CreateHtmlUtil.createHtml(0,seasonId,"index.ftl","index.html" , map);
    		CreateHtmlUtil.createHtml(0,seasonId,"list.ftl","list.html" , map);
    		CreateHtmlUtil.createHtml(0,seasonId,"works.ftl","works.html" , map);
    		CreateHtmlUtil.createHtml(0,seasonId,"contribute.ftl","contribute.html" , map);
    		//生成pc端页面
    		CreateHtmlUtil.createHtml(1,seasonId,"index.ftl","index.html" , map);
    		CreateHtmlUtil.createHtml(1,seasonId,"list.ftl","list.html" , map);
    		CreateHtmlUtil.createHtml(1,seasonId,"works.ftl","works.html" , map);
    		CreateHtmlUtil.createHtml(1,seasonId,"contribute.ftl","contribute.html" , map);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			return new ReMessage(1,"操作失败，服务异常");
		}
		return new ReMessage(0,"操作成功");
	}
	
	
	/**
	 * 跳转到mobile赛事配置页面
	 * @param seasonId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/configMobile",method=RequestMethod.GET)
	public ModelAndView configMobile(@RequestParam(value="seasonId") String seasonId,HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView();
		HttpSession session=request.getSession();
		if(session==null||session.getAttribute("adminUserId")==null){
			 mv.setViewName("redirect:/admin/logout");
			 return mv;
		}
		if(com.miniworld.utils.AuthorityUtil.isSupperAdmin()) {
			Match match = matchService.selectMatchBySeasonId(seasonId);
			if(match==null) {
				mv.addObject("seasonId", seasonId);
			}else {
				mv.addObject("Id", match.getId());
				mv.addObject("seasonId", seasonId);
				mv.addObject("sId", globalManager.getEventId());
				mv.addObject("sName", globalManager.getEventSysName());
				mv.addObject("match", match);
			}
			mv.setViewName("season/configMobile");
			return mv;
		}else {
			mv.setViewName("redirect:/admin/logout");
			return mv;
		}
		
	}
}
