package com.miniworld.controller;

import com.miniworld.common.EventSysStateEnum;
import com.miniworld.common.ReMessage;
import com.miniworld.config.SystemConfig;
import com.miniworld.entity.TestUser;
import com.miniworld.service.TestService;
import com.miniworld.utils.CreateHtmlUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/test")
public class TestController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private TestService testService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView GetTest() {
        log.debug("test");
        System.out.println("test");
        return new ModelAndView("matchconfig");
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> GetUser(@RequestParam(value = "userId") Integer id) {
        HashMap<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("id", id);
        TestUser testUser = testService.getTestUser(queryMap);
        log.debug("-------------------------");
        log.debug(testUser.toString());

        HashMap<String, Object> reMsg = new HashMap<String, Object>();
        reMsg.put("user", testUser);
        return reMsg;
    }

    @RequestMapping(value = "/t1", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> IncreaseHeat(@RequestParam(value = "wid") String wid,
                                            @RequestParam(value = "token") String token) {
        System.out.println("IncreaseHeat");

        HashMap<String, Object> reMsg = new HashMap<String, Object>();


        if (wid == null || wid.equals("") || token == null) {
            reMsg.put("code", EventSysStateEnum.FAIL);
            return reMsg;
        }

        reMsg.put("code", EventSysStateEnum.SUCCESS);
        return reMsg;
    }

    /*
    http://www.mini1.cn/?uin=1166201&ver=0.26.7&apiid=999&lang=0&country=CN&auth=d31438753572326104c279ff9e35949b&time=1530349075&s2t=1530348995&nickname=六界剑尊
     */
    @RequestMapping("/home")
    public String login(@RequestParam Map<String, Object> map,
                        Model model) {
        model.addAllAttributes(map);
        return "redirect:/html/index.html";
    }


    @Resource
    private SystemConfig systemConfig;

    private Logger logger = LoggerFactory.getLogger(TestController.class);
    @RequestMapping("/sysConfig")
    @ResponseBody
    public ReMessage loggin() {
        logger.info("zhe {} shi",systemConfig.toString());
        logger.debug(systemConfig.getUsrInfoUrl());
        logger.error(systemConfig.getMapUrl());
        logger.warn(systemConfig.getMapUrl());
        return new ReMessage(1, "操作成功", systemConfig.getMapUrl());
    }


    @RequestMapping(value = "/eventID", method = RequestMethod.GET)
    @ResponseBody
    public ReMessage TestEventID(@PathVariable String eventID) {
        return new ReMessage(1, eventID);
    }
    

    @RequestMapping(value = "/createHtml", method = RequestMethod.POST)
    @ResponseBody
    public ReMessage TestCreateHtml(@RequestBody Map<String, String> map) {
    	logger.info("--------------createHtml------------");
    	logger.info(map.toString());
    	try{
    		Map<String, Object> matchMap = new HashMap<>();
    		matchMap.put("bannerImage","123");
    		matchMap.put("themeImage","123");
    		matchMap.put("timeImage","123");
    		matchMap.put("rewardImage","1231");
    		matchMap.put("ruleImage","123");
    		matchMap.put("rule","");
    		matchMap.put("reviewImage","123");
    		matchMap.put("review","123");
    		matchMap.put("judgeGuest","123");
    		matchMap.put("gameIntroduceImage","123");
    		matchMap.put("gameIntroduce","123");
    		matchMap.put("partner","123");
    	   CreateHtmlUtil.createHtml(Integer.valueOf(map.get("state")),map.get("seasonId"),map.get("templateName"),map.get("targetFileName"),matchMap);
    	}catch (Exception e){
    		logger.error(e.getMessage());
    		return new ReMessage(-1,e.getLocalizedMessage());
    	}
    	
    	return new ReMessage(0,"");
    }
    
    
    

}