package com.miniworld;

import com.miniworld.common.GlobalManager;
import com.miniworld.common.ReMessage;
import com.miniworld.common.RedisConst;
import com.miniworld.common.SpringContextHolder;
import com.miniworld.entity.Works;
import com.miniworld.service.SubmitService;
import com.miniworld.service.WorksShowService;
import com.miniworld.utils.RedisUtil;
import com.miniworld.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.swing.*;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class InitData {

    /**
     * 添加作品到redis缓存中
     * 作品状态：
     * 0：未审核
     * 1：审核通过
     * 2：已拒绝
     * 3：已下架
     * 未审核：1-100
     * 审核通过：101-200
     */





    private SubmitService submitService;

    private GlobalManager globalManager;
    public InitData() {
        submitService=SpringContextHolder.getBean("submitService");
        globalManager = SpringContextHolder.getBean("globalManager");
    }

    public InitData(SubmitService submitService) {
        this.submitService = submitService;
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public void initRedisWorksSet(GlobalManager globalManager, Boolean isSetRedis, Boolean isSetMysql) {
        Long time = 1529994414000L;
        int i = 1000;
        for (; i <= 1200; i++) {
            int state;
            if (i < 1100) {
                state = 0;
            } else {
                state = 1;
            }
            Works works = new Works();
            works.setWorksId(i);
            works.setMiniId(i);
            works.setName("name_" + i);
            works.setQq("123456789");
            works.setMail("123@qq.com");
            works.setPhone("18826589658");
            works.setTeamMates("123,1234,156");
            works.setWorksName("worksName_" + i);
            works.setWorksIntroduce("这是我的作品，希望大家能多多支持");
            //works.setWorksImage("http://xxxxxx.jpg,http://xxxx.jpg");
            works.setWorksMainImage("http://main.jpg");
            works.setMainSmallImage("http://main_small.jpg");
            works.setImage1("http://img_1.jpg");
            works.setImage2("http://img_2.jpg");
            works.setImage3("http://img_3.jpg");
            works.setImageSmall1("http://img_small_1.jpg");
            works.setImageSmall2("http://img_small_2.jpg");
            works.setImageSmall3("http://img_small_3.jpg");
            works.setWorksMapId("12");
            works.setWorksState(state);
            works.setSubmissionTime(time);
            works.setCreateTime(time);
            works.setUpdateTime(time);
            works.setWorksHeat(i);
            works.setWeekHeat(i);
            works.setWeekHeatTime(TimeUtil.getWeekNum());

            time++;

            globalManager.getPopulTreeSet().add(works);
            globalManager.getNewestTreeSet().add(works);
            globalManager.getWeekTreeSet().add(works);
            if (isSetRedis) {
                if (i < 1100) {
                    RedisUtil.sAdd(RedisConst.COLLECTION_UNAUDITED, i + "");
                } else {
                    RedisUtil.sAdd(RedisConst.COLLECTION_AUDITED, i + "");
                }
                try {
                    RedisUtil.SetWorksInRedis(works);
                } catch (ClassCastException e) {
                    logger.error(e.getMessage());
                }
            }
            if (isSetMysql) {
                InsertInMysql(works);
            }
        }
    }




    public void InsertInMysql(Works works) {
        HashMap<String, Object> worksMap = new HashMap<String, Object>();
        Date date = new Date();
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);//将时间格式转换成符合Timestamp要求的格式.
        Timestamp newdate = Timestamp.valueOf(nowTime);//把时间转换
        worksMap.put("mini_id", works.getMiniId());//从前端获取，前端从域中获取
        worksMap.put("works_id", works.getWorksId());//后台自动生成
        worksMap.put("name", works.getName());
        worksMap.put("qq", works.getQq());
        worksMap.put("mail", works.getMail());
        worksMap.put("phone", works.getPhone());
        worksMap.put("team_mates", works.getTeamMates());
        worksMap.put("works_map_id", works.getWorksMapId());
        worksMap.put("works_name", works.getWorksName());
        worksMap.put("works_introduce", works.getWorksIntroduce());
        //图片获取后提交接口，得到返回url值并储存至mysql
        worksMap.put("works_main_image", works.getWorksMainImage());
        worksMap.put("main_small_image", works.getMainSmallImage());
        worksMap.put("image_1", works.getImage1());
        worksMap.put("image_small_1", works.getImageSmall1());
        worksMap.put("image_2", works.getImage2());
        worksMap.put("image_small_2", works.getImageSmall2());
        worksMap.put("image_3", works.getImage3());
        worksMap.put("image_small_3", works.getImageSmall3());
        worksMap.put("works_state", works.getWorksState());
        worksMap.put("submission_time",works.getSubmissionTime());
        worksMap.put("create_time", works.getCreateTime());
        worksMap.put("update_time", works.getUpdateTime());
        worksMap.put("heat", works.getWorksHeat());
        worksMap.put("week_heat", works.getWeekHeat());
        worksMap.put("week_heat_time", works.getWeekHeatTime());
        worksMap.put("eventId", globalManager.getEventId());

        boolean result = submitService.submitWorks(worksMap);
        if (!result) {
            logger.error("works 插入数据库失败:" + works.toString());
        }
    }
}
