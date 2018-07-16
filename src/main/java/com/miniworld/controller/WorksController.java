package com.miniworld.controller;

import com.miniworld.common.GlobalManager;
import com.miniworld.common.ReMessage;
import com.miniworld.config.SystemConfig;
import com.miniworld.entity.Works;
import com.miniworld.exception.ParamException;
import com.miniworld.service.SubmitService;
import com.miniworld.utils.RedisUtil;

import com.miniworld.utils.SubmitMapUtil;

import com.miniworld.utils.TimeUtil;
import com.miniworld.utils.VerifyUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 提交页面
 */

@Controller
@RequestMapping("/submit")
public class WorksController {
    private static Logger logger = LoggerFactory.getLogger(WorksController.class);

    @Resource
    private SubmitService submitService;

    @Resource
    private GlobalManager globalManager;

    @Resource
    private SystemConfig systemConfig;


    @Resource
    private RestTemplate restTemplate;

    /**
     * 进入提交页面
     *
     * @return submit.jsp
     */

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView getsubmit() {
        return new ModelAndView("submit");
    }

    /**
     * 获取地图列表
     *
     * @param auth
     * @param uin
     * @param time
     * @param sign
     * @param s2t
     * @return
     */
    @RequestMapping(value = "/getMapList", method = RequestMethod.GET)
    @ResponseBody
    public ReMessage<ArrayList<Map<String, Object>>> getMapList(
            @RequestParam(value = "auth", required = false) String auth,
            @RequestParam(value = "uin", required = false) Integer uin,
            @RequestParam(value = "time", required = false) String time,
            @RequestParam(value = "sign", required = false) String sign,
            @RequestParam(value = "s2t", required = false) String s2t
    ) {
        logger.info("GetMapList");

        if ((auth == null || auth.isEmpty()) && (s2t == null || s2t.isEmpty()) && (sign == null || sign.isEmpty()))
            return new ReMessage<>(1, "参数错误！");

        if (uin == null || (time == null || time.isEmpty())) return new ReMessage<>(1, "参数错误！");

        String newauth = new String();
        Long startTime = globalManager.getEventSysStrartTime();

        if (startTime == null || startTime == 0) {
            logger.info("startTime is null");
            return new ReMessage<>(1, "无开始赛季");
        }

        String[] signParts = new String[2];
        String url = systemConfig.getMapUrl();

        if (auth == null || auth.isEmpty()) {
            if (sign == null || sign.isEmpty()) {
                return new ReMessage<>(1, "登录信息有误");
            } else {
                signParts = sign.split("_");
                newauth = VerifyUtil.CryptMD5(time + signParts[0] + uin);    //使用工具类完成MD5加密
            }
        } else {
            newauth = auth;
            signParts[1] = s2t;

        }

        ReMessage<ArrayList<Map<String, Object>>> reMessage = SubmitMapUtil.getMapList(restTemplate, url, time, uin, newauth, signParts[1], startTime);

        System.err.println(reMessage.toString());

        return reMessage;
    }

    /**
     * 提交作品至数据库
     *
     * @param miniId
     * @param name
     * @param qq
     * @param mail
     * @param phone
     * @param teamMates
     * @param worksName
     * @param worksIntroduce
     * @param worksMainImage
     * @param worksImage
     * @return
     * @throws IOException
     * @throws ParamException
     */
    @RequestMapping(value = "/submitWorks", method = RequestMethod.POST)
    @ResponseBody
    public ReMessage SubmitWorks(
            @RequestParam(value = "mini_id", required = false) Integer miniId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "qq", required = false) String qq,
            @RequestParam(value = "mail", required = false) String mail,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "team_mates", required = false) String teamMates,
            @RequestParam(value = "works_map_id", required = false) String worksMapId,
            @RequestParam(value = "works_name", required = false) String worksName,
            @RequestParam(value = "works_introduce", required = false) String worksIntroduce,
            @RequestParam(value = "works_main_image", required = false) String worksMainImage,
            @RequestParam(value = "main_small_image", required = false) String mainSmallImage,
            @RequestParam(value = "image_1", required = false) String image1,
            @RequestParam(value = "image_small_1", required = false) String imageSamll1,
            @RequestParam(value = "image_2", required = false) String image2,
            @RequestParam(value = "image_small_2", required = false) String imageSamll2,
            @RequestParam(value = "image_3", required = false) String image3,
            @RequestParam(value = "image_small_3", required = false) String imageSamll3,
            @RequestParam(value = "auth", required = false) String auth,
            @RequestParam(value = "time", required = false) String time,
            @RequestParam(value = "sign", required = false) String sign,
            @RequestParam(value = "s2t", required = false) String s2t
    ) throws ParamException, IOException {
        logger.info("SubmitWorks");
        String eventId = new String(globalManager.getEventId());//管理类参数获取
        String url = systemConfig.getMapUrl();
        Long startTime = globalManager.getEventSysStrartTime();
        Long submitStartTime = globalManager.getEventSysSubmitStartTime();
        Long submitEndTime = globalManager.getEventSysSubmitEndTime();
        boolean resultVerify = false;

        if (startTime == null || startTime == 0) {
            logger.info("startTime is null");
            return new ReMessage<>(1, "无开始赛季");
        }

        if (eventId == null || eventId.isEmpty() || startTime > System.currentTimeMillis()) {
            logger.info("eventId is null");
            return new ReMessage<>(1, "赛季未开始");
        }

        if (submitStartTime > System.currentTimeMillis() || submitEndTime < System.currentTimeMillis()) {
            logger.info("不在投稿时间段内");
            return new ReMessage<>(1, "不在投稿时间段内");
        }

        HashMap<String, Object> worksMap = new HashMap<String, Object>();
        //非空检验
        if (miniId == null || name == null || name.isEmpty() ||
                qq == null || qq.isEmpty() ||
                worksName == null || worksName.isEmpty() ||
                worksMapId == null || worksMapId.isEmpty() ||
                worksMainImage == null || worksMainImage.isEmpty() ||
                image1 == null || image1.isEmpty()) {
            return new ReMessage<>(1, "参数错误提交失败！");
        }

        if (auth == null || auth.isEmpty()) {
            if (sign == null || sign.isEmpty()) {
                return new ReMessage<>(1, "登录信息有误");
            } else {
                //sign 检测地图ID是否与用户ID相符合
                resultVerify = SubmitMapUtil.VerifyMap(restTemplate, url, miniId, sign, time, startTime, worksMapId);
            }
        } else {
            //auth s2t 检测地图ID是否与用户ID相符合
            resultVerify = SubmitMapUtil.VerifyMap(restTemplate, url, miniId, auth, s2t, time, startTime, worksMapId);
        }

        if (!resultVerify) {
            return new ReMessage<>(1, "提交信息不匹配");
        }

        if (!qq.matches("[1-9][0-9]{4,10}$")) {//qq
            return new ReMessage<>(1, "QQ长度有误!");
        } else if (mail != null && !mail.isEmpty() && !mail.matches("[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$")) {//mail
            return new ReMessage<>(1, "邮箱格式错误");
        } else if (phone != null && !phone.isEmpty() && !phone.matches("[0-9]\\d*")) {//phone
            return new ReMessage<>(1, "号码格式有误！");
        } else if (phone != null && !teamMates.isEmpty() && !teamMates.matches("(\\d)*(,\\d*)*")) {//teamMates
            return new ReMessage<>(1, "成员格式错误！");
        } else if (worksIntroduce != null && !worksIntroduce.isEmpty() && worksIntroduce.length() > 150) {//introduce  ^[^\.]{150}
            return new ReMessage<>(1, "地图介绍长度超出范围！");
        } else {
            Works works;
            try {
                works = submitService.getWorksIdByMiniId(miniId, eventId);
            } catch (Exception e) {
                logger.error("getWorksIdByMiniId error:%s\n", e.getMessage());
                return new ReMessage(-1, e.getMessage());
            }
            if (works != null) {
                return new ReMessage<Integer>(2, "您已提交过一次作品", works.getWorksId());
            }
            Date date = new Date();
            //String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);// 将时间格式转换成符合Timestamp要求的格式.
            //Timestamp newdate = Timestamp.valueOf(nowTime);// 把时间转换
            Long timeStamp = date.getTime();
            worksMap.put("mini_id", miniId);// 从前端获取，前端从域中获取
            worksMap.put("name", name);
            worksMap.put("qq", qq);
            worksMap.put("mail", mail);
            worksMap.put("phone", phone);
            worksMap.put("team_mates", teamMates);
            worksMap.put("works_map_id", worksMapId);
            worksMap.put("works_name", worksName);
            worksMap.put("works_introduce", worksIntroduce);
            // 图片获取后提交接口，得到返回url值并储存至mysql
            worksMap.put("works_main_image", worksMainImage);
            worksMap.put("main_small_image", mainSmallImage);
            worksMap.put("image_1", image1);
            worksMap.put("image_small_1", imageSamll1);
            worksMap.put("image_2", image2);
            worksMap.put("image_small_2", imageSamll2);
            worksMap.put("image_3", image3);
            worksMap.put("image_small_3", imageSamll3);
            worksMap.put("works_state", 0);
            worksMap.put("submission_time", timeStamp);
            worksMap.put("create_time", timeStamp);
            worksMap.put("update_time", timeStamp);
            worksMap.put("heat", 0);
            worksMap.put("week_heat", 0);
            worksMap.put("week_heat_time", TimeUtil.getWeekNum());
            worksMap.put("eventId", eventId);

            System.out.println(worksMap.toString());
            boolean result = false;

            try {
                result = submitService.submitWorks(worksMap);
            } catch (Exception e) {
                logger.error("submitWorks error:%s\n", e.getMessage());
                return new ReMessage<>(-1, e.getMessage());
            }

            if (result) {
                try {
                    works = submitService.getWorksIdByMiniId(miniId, eventId);
                } catch (ParamException e) {
                    logger.error("getWorksIdByMiniId error:%s\n", e.getMessage());
                    return new ReMessage(-1, e.getMessage());
                }
                if (works != null) {
                    RedisUtil.AddWorksState(works.getWorksId(), 0);
                    RedisUtil.SetWorksInRedis(works);
                }
            } else {
                return new ReMessage<>(1, "提交失败！");
            }

            return new ReMessage<Integer>(0, "提交成功,返回worksId", works.getWorksId());
        }
    }
}