package com.miniworld.controller;


import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.miniworld.common.SeasonManager;
import com.miniworld.entity.WorksHonor;
import com.miniworld.service.HonorTopService;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.miniworld.common.GlobalManager;
import com.miniworld.common.ReMessage;
import com.miniworld.config.SystemConfig;
import com.miniworld.entity.Works;
import com.miniworld.service.FormReviewService;
import com.miniworld.service.SubmitService;
import com.miniworld.utils.RedisUtil;

/**
 * 后台审核页面
 */
@Controller
@RequestMapping("/formReview")
public class FormReviewController {

    @Autowired
    private FormReviewService formReviewService;

    @Resource
    private SubmitService submitService;

    @Resource
    private GlobalManager globalManager;

    @Resource
    private SystemConfig systemConfig;


    @Resource
    private HonorTopService honorTopService;

    @Resource
    private SeasonManager seasonManager;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static List<String> RESULTLIST = Arrays.asList("成功", "参数不正确", "签名不正确", "邮件不存在", "附件不存在", "条件不正确", "结束时间不正确", "附件数据不正确", "ip受限", "迷你号受限");
    private static Integer DAY = 7;

    /**
     * 进入审核页面
     *
     * @return worksdata.jsp
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getreview(HttpSession session, Model model) {
        Integer adminRoleId = (Integer) session.getAttribute("adminRoleId");
        model.addAttribute("adminRoleId", adminRoleId);
        model.addAttribute("sId", globalManager.getEventId());
        model.addAttribute("sName", globalManager.getEventSysName());
        return "review";
    }

    /**
     * 进入审核页面
     */
    @RequestMapping(value = "/getAllWorks", method = RequestMethod.GET)
    @ResponseBody
    public ReMessage<Map<String, Object>> getAllWorks(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize
    ) {
        logger.info("FormReview");
        String eventId = globalManager.getEventId();
        ReMessage<Map<String,Object>> reMessage = new ReMessage<Map<String,Object>>();
        try {
            reMessage = formReviewService.getAllWorks(eventId,pageNum,pageSize);
        } catch (Exception e) {
            logger.error("getAllWorks error:%s\n", e.getMessage());
            return new ReMessage<>(-1, e.getMessage());
        }
        return reMessage;
    }

    /**
     * 根据查询作品状态查询
     *
     * @param worksState
     */
    @RequestMapping(value = "/getWorksByWorksState", method = RequestMethod.GET)
    @ResponseBody
    public ReMessage<Map<String, Object>> getWorksByWorksState(
            @RequestParam(value = "worksState", required = false, defaultValue = "0") Integer worksState,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize
    ) {
        logger.info("GetWorksByWorksState");
        String eventId = globalManager.getEventId();
        ReMessage<Map<String,Object>> reMessage = new ReMessage<Map<String,Object>>();
        

        try {
            reMessage = formReviewService.getWorksByWorksState(worksState, eventId,pageNum,pageSize);
        } catch (Exception e) {
            logger.error("getWorksByWorksState error:%s\n", e.getMessage());
            return new ReMessage<>(-1, e.getMessage());
        }

        return reMessage;
    }
    /**
     * 根据迷你号或作品ID查询作品
     *
     * @param Id 完整的迷你号或者是作品ID
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ReMessage<Works> search(@RequestParam(value = "id", required = false) String Id) {
        logger.info("Search");
        if (Id == null || Id.isEmpty()) return new ReMessage<>(1, "请输入ID");
        String eventId = globalManager.getEventId();
        boolean matchresult = Id.matches("\\d{4}$");
        Integer id = Integer.parseInt(Id);
        Works works = new Works();

        try {
            if (matchresult) {
                works = formReviewService.getWorksByWorksId(id, eventId);
            } else {
                works = formReviewService.getWorksByMiniId(id, eventId);
            }
        } catch (Exception e) {
            logger.error("search error:%s\n", e.getMessage());
            return new ReMessage<>(-1, e.getMessage());
        }

        if (works == null) {
            return new ReMessage<Works>(1, "数据不存在", works);
        } else {
            return new ReMessage<Works>(0, "成功", works);
        }

    }

    /**
     * 修改作品状态
     *
     * @param miniId     迷你号
     * @param worksState 更改的状态
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED)
    public ReMessage update(@RequestParam(value = "miniId", required = false) Integer miniId, 
    		@RequestParam(value = "worksState", required = false) Integer worksState,
    		@RequestParam(value = "pageWorksState", required = false) Integer pageWorksState) {
        logger.info("Update");
        String title = new String();
        String body = new String();
        Date date = new Date();
        Works works = new Works();
        Integer result;
        String eventId = globalManager.getEventId();
        
        if(eventId == null || eventId.isEmpty()) {
        	return new ReMessage<>(1, "当前无开始赛季");
        }
        
        if(worksState == 0) {
        	return new ReMessage<>(1,"不能返回未审核状态");
        }
        
        ReMessage checkMsg = formReviewService.checkState(miniId, worksState, eventId);
        //end_time	邮件有效期
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
//        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);//将时间格式转换成符合Timestamp要求的格式.  
//        Timestamp newdate = Timestamp.valueOf(nowTime);//把时间转换
        Long timeStamp = date.getTime();
        if(checkMsg.getCode() == 0) {
	        try {
	            works = submitService.getWorksIdByMiniId(miniId, eventId);
	            result = formReviewService.updateWorks(miniId, worksState, timeStamp, eventId);
	        } catch (Exception e) {
	            logger.error("update error:%s\n", e.getMessage());
	            return new ReMessage<>(-1, e.getMessage());
	        }
	        System.out.println(result);
	        switch (worksState) {
	            case 1:
	                title = "筑梦师建造大赛作品审核结果";
	                body = "恭喜您，本次参赛作品通过审核，将展示在本届作品页面，预祝获得好成绩。";
	                break;
	            case 2:
	                title = "筑梦师建造大赛作品审核结果";
	                body = "很遗憾，您本次参赛的作品未能通过审核，请继续努力。";
	                break;
	            case 3:
	                title = "筑梦师建造大赛作品审核通告";
	                body = "抱歉，因您违反本次大赛规则，您的参赛作品将被取消本次参赛资格。";
	                break;
	        }
	        if (result > 0) {
	            RedisUtil.ChangeWorksState(works.getWorksId(), worksState);
	            RestTemplate template = new RestTemplate();
	            ObjectMapper objectMapper = new ObjectMapper();
	            //String url = "http://120.24.64.132:8080/miniw/mail";
	            String url = systemConfig.getEmailUrl();
	            String param = "?cmd=" + "send_mail_to_player" 
	            				+ "&attach=" + "" 
	            				+ "&title=" + title 
	            				+ "&body="+ body 
	            				+ "&uin=" + miniId 
	            				+ "&end_time=" + ((date.getTime() / 1000) + DAY * 24 * 60 * 60)
	            				+ "&jump_to=" + 0;
	            String emailResultstr = template.getForObject(url + param, String.class);
	            try {
	                resultMap = objectMapper.readValue(emailResultstr, HashMap.class);
	                //查看榜单中是否有该作品
	                if (worksState != 1) {
	                    honorTopService.worksStateUpdateByWid(works.getWorksId());
	                    seasonManager.treeSetRemove(works);
	                } else {
	                    seasonManager.treeSetAdd(works);
	                }
	
	
	            } catch (JsonParseException e) {
	                e.printStackTrace();
	            } catch (JsonMappingException e) {
	                e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            Integer resultNum = (Integer) resultMap.get("result");
	            if(resultNum != 0) {
	            	return new ReMessage<>(1,RESULTLIST.get(resultNum));
	            }
	            return new ReMessage<>(0,RESULTLIST.get(resultNum));
	        } else {
	            return new ReMessage<>(1, "更新失败");
	        }
        }else {
        	//return new ReMessage<>(1,"数据与数据库不统一，请刷新页面");
        	if(checkMsg.getCode() == 3) {
        		checkMsg.setCode(0);
        		System.out.println(checkMsg.toString());
        	}
        	return checkMsg;
        }
    }

    /**
     * 删除作品
     *
     * @param miniId 作品的迷你ID
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ReMessage deleteWorks(@RequestParam(value = "miniId", required = false) Integer miniId) {
        logger.info("Delete");
        String eventId = globalManager.getEventId();
        Integer delresult = 0;

        if (miniId == null || miniId == 0) {
            return new ReMessage<>(1, "迷你号为空");
        }

        try {
            Works works = submitService.getWorksIdByMiniId(miniId, eventId);
            delresult = formReviewService.deleteWorks(miniId, eventId);
            if (delresult > 0) {
                //查看榜单中是否有该作品
                honorTopService.worksStateUpdateByUid(miniId);
                seasonManager.treeSetRemove(works);
            }
        } catch (Exception e) {
            logger.error("deleteWorks error:%s" + e.getMessage());
            return new ReMessage<>(-1, e.getMessage());
        }
        if (delresult > 0) {
            return new ReMessage<>(0, "删除成功");
        } else {
            return new ReMessage<>(1, "删除失败,作品不存在");
        }

    }
}
