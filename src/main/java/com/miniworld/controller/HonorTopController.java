package com.miniworld.controller;

import com.miniworld.common.GlobalManager;
import com.miniworld.common.ReMessage;
import com.miniworld.entity.WorksHonor;
import com.miniworld.service.HonorTopService;
import com.miniworld.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/honor")
public class HonorTopController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private HonorTopService honorTopService;

    @Resource
    private GlobalManager globalManager;

    private static int PAGE = 1;
    private static int NUM = 20;

    /**
     * 返回荣誉榜单后台操作页面
     * @param model
     */
    @RequestMapping(value = "/backstage", method = RequestMethod.GET)
    public ModelAndView getBackstage(Model model) {
    	model.addAttribute("sId", globalManager.getEventId());
		model.addAttribute("sName", globalManager.getEventSysName());
        return new ModelAndView("/honor/honorBackStage");
    }

    /**
     * 返回荣誉榜单前台展示页面
     */
    @RequestMapping(value = "/frontstage", method = RequestMethod.GET)
    public ModelAndView getFrontstage() {
        return new ModelAndView("/honor/honorFrontStage");
    }


    /**
     * @param page :获取页数
     * @param num  :获取个数
     * @desc 获取当前荣誉榜单列表
     */
    @RequestMapping(value = "/getHonorList", method = RequestMethod.GET)
    @ResponseBody
    public ReMessage priGetHonorList(@RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "num", required = false) Integer num) {
        if (page == null || page < 1)
            page = PAGE;

        if (num == null || num < 1)
            num = NUM;

        logger.info("获取当前荣誉榜单列表");
        List<WorksHonor> list;
        int pageTotal;
        try {
            list = honorTopService.getHonorList(globalManager.getEventId(), (page - 1) * num, num);
            int size = honorTopService.getHonorSize(globalManager.getEventId());
            logger.info("获取当前荣誉榜单列表的总数：size={}",size);
            if (size % num == 0) {
                pageTotal = size / num;
            } else {
                pageTotal = size / num + 1;
            }
        } catch (RuntimeException e) {
            logger.error("获取荣誉榜单列表错误:page={},num={},error:{}", page, num, e.getMessage());
            return new ReMessage(-1, "系统错误");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageTotal", pageTotal);
        map.put("pageCurrent", page);
        map.put("honorWorks", list);
        return new ReMessage(0, "操作成功", map);
    }

    /**
     * @param wid  :作品id
     * @param rank :将该作品设置在该序号的位置上
     * @desc 将特定的作品更新到指定位置
     */
    @RequestMapping(value = "/updateHonor", method = RequestMethod.GET)
    @ResponseBody
    public ReMessage priUpdateHonorTop(@RequestParam(value = "wid", required = false) Integer wid,
                                    @RequestParam(value = "rank", required = false) Integer rank) {
        if (wid == null || rank == null || wid==0 || rank==0) {
            return new ReMessage(1, "请传入正确的作品id和序号");
        }
        try {
            return  honorTopService.updateHonorTop(wid, rank);
        } catch (RuntimeException e) {
            logger.error("修改荣誉榜单错误:wid={},rank={},error:{}", wid, rank, e.getMessage());
            return new ReMessage(-1, "系统错误");
        }

    }

    /**
     * @param wid :作品id
     * @desc 为荣誉榜单中添加一个作品，自动添加到末尾
     */
    @RequestMapping(value = "/addHonorTop", method = RequestMethod.GET)
    @ResponseBody
    public ReMessage priAddHonorTop(@RequestParam(value = "wid", required = false) Integer wid) {
        if (wid == null || wid==0) {
            return new ReMessage(1, "wid为空");
        }
        logger.info("为荣誉榜单中添加一个作品，自动添加到末尾:wid={}",wid);
        try {
           return honorTopService.addHonorTop(wid);
        } catch (RuntimeException e) {
            logger.error("添加作品错误:wid={},error:{}", wid, e.getMessage());
            return new ReMessage(-1, "系统错误");
        }

    }


    /**
     * @param rank :榜单位置
     * @desc 为荣誉榜单中添加一个作品，自动添加到末尾
     */
    @RequestMapping(value = "/delHonorTop", method = RequestMethod.GET)
    @ResponseBody
    public ReMessage priDelHonorTop(@RequestParam(value = "rank", required = false) Integer rank) {
        if (rank == null || rank==0) {
            return new ReMessage(1, "rank为空");
        }

        try {
            return  honorTopService.delHonorTop(rank);
        } catch (RuntimeException e) {
            logger.error("删除荣誉榜单错误:rank={},error:{}", rank, e.getMessage());
            return new ReMessage(-1, "系统错误");
        }
    }


    /**
     * @desc 获取本年赛事榜单信息
     */
    @RequestMapping(value = "/getThisYearHonor", method = RequestMethod.GET)
    @ResponseBody
    public ReMessage getPreviewHonorTop() {
        try {
           return honorTopService.getThisYearHonor();
        } catch (RuntimeException e) {
            logger.error("获取本年赛事榜单信息出错:{}", e.getMessage());
            return new ReMessage(-1, "系统错误");
        }
    }

}
