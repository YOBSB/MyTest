package com.miniworld.service.impl;

import com.miniworld.BaseTest;
import com.miniworld.common.GlobalManager;
import com.miniworld.common.ReMessage;
import com.miniworld.dao.HonorTopMapper;
import com.miniworld.entity.WorksHonor;
import com.miniworld.service.HonorTopService;
import com.miniworld.service.WorksShowService;
import com.miniworld.utils.TimeUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.List;

public class HonorTopServiceImplTest extends BaseTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private WorksShowService worksShowService;


    @Resource
    private GlobalManager globalManager;

    @Resource
    private HonorTopService honorTopService;

    @Resource
    private HonorTopMapper honorTopMapper;

    @Resource
    private JdbcTemplate jt;

    @Before
    public void init(){
        globalManager.setEventId("201807");
    }

    @Test
    public void addHonorTopTest() {
        /**
         * 清空数据库中的数据
         */
        clear();

        /**
         * 添加初始数据
         */
        honorTopService.addHonorTop( 1161);
        honorTopService.addHonorTop( 1162);
        honorTopService.addHonorTop( 1163);
        honorTopService.addHonorTop( 1164);
        honorTopService.addHonorTop( 1165);
        honorTopService.addHonorTop( 1166);
        honorTopService.addHonorTop( 1167);
        ReMessage reMessage;
        reMessage = honorTopService.addHonorTop( 1168);
        logger.info("添加一个正常的作品:{}", reMessage.toString());
        Assert.assertEquals("添加一个正常的作品", 0, reMessage.getCode());

        reMessage = honorTopService.addHonorTop( 1161);
        logger.info("添加一个已在榜单的作品:{}", reMessage.toString());
        Assert.assertEquals("添加一个已在榜单的作品", 3, reMessage.getCode());

        reMessage = honorTopService.addHonorTop( 260);
        logger.info("添加一个不存在的作品{}", reMessage.toString());
        Assert.assertEquals("添加一个不存在的作品", 2, reMessage.getCode());

    }

    @Test
    public void updateHonorTopTest() {
        /**
         * 清空数据库中的数据
         */
        clear();

        /**
         * 添加初始数据
         */
        honorTopService.addHonorTop( 1161);
        honorTopService.addHonorTop( 1162);
        honorTopService.addHonorTop( 1163);
        honorTopService.addHonorTop( 1164);
        honorTopService.addHonorTop( 1165);
        honorTopService.addHonorTop( 1166);
        honorTopService.addHonorTop( 1167);

        List<WorksHonor> list = honorTopMapper.getHonorListBeginRank( globalManager.getEventId(),0);
        logger.info("初始化数据:\n{}", list.toString());

        ReMessage reMessage;
        reMessage = honorTopService.updateHonorTop( 222,5);
        logger.info("榜单修改，作品不存在:{}", reMessage.toString());
        Assert.assertEquals("榜单修改，作品不存在", 2, reMessage.getCode());

        reMessage = honorTopService.updateHonorTop( 1165,1);
        logger.info("榜单修改，作品在榜单中，修改其排名 1165,5-1:{}", reMessage.toString());
        Assert.assertEquals("榜单修改，作品在榜单中，修改其排名", 0, reMessage.getCode());

        list = honorTopMapper.getHonorListBeginRank( globalManager.getEventId(),0);
        logger.info("修改后数据:\n{}", list.toString());

        reMessage = honorTopService.updateHonorTop( 1162,5);
        logger.info("榜单修改，作品在榜单中，修改其排名 1162 2-5:{}", reMessage.toString());
        Assert.assertEquals("榜单修改，作品在榜单中，修改其排名", 0, reMessage.getCode());

        list = honorTopMapper.getHonorListBeginRank( globalManager.getEventId(),0);
        logger.info("修改后数据:\n{}", list.toString());

        reMessage = honorTopService.updateHonorTop( 1165,5);
        logger.info("榜单修改，作品在榜单中，修改其排名 1165,1-5:{}", reMessage.toString());
        Assert.assertEquals("榜单修改，作品在榜单中，修改其排名", 0, reMessage.getCode());

        list = honorTopMapper.getHonorListBeginRank( globalManager.getEventId(),0);
        logger.info("修改后数据:\n{}", list.toString());


        reMessage = honorTopService.updateHonorTop( 1188,2);
        logger.info("榜单修改，作品不在在榜单中:{}", reMessage.toString());
        Assert.assertEquals("榜单修改，作品不在在榜单中", 0, reMessage.getCode());

        list = honorTopMapper.getHonorListBeginRank( globalManager.getEventId(),0);
        logger.info("修改后数据:\n{}", list.toString());

    }

    @Test
    public void delHonorTopTest() {
        /**
         * 清空数据库中的数据
         */
        clear();

        /**
         * 添加初始数据
         */
        honorTopService.addHonorTop( 1161);
        honorTopService.addHonorTop( 1162);
        honorTopService.addHonorTop( 1163);
        honorTopService.addHonorTop( 1164);
        honorTopService.addHonorTop( 1165);
        honorTopService.addHonorTop( 1166);
        honorTopService.addHonorTop( 1167);

        List<WorksHonor> list = honorTopMapper.getHonorListBeginRank( globalManager.getEventId(),0);
        logger.info("初始化数据:\n{}", list.toString());


        ReMessage reMessage;
        reMessage = honorTopService.delHonorTop( 1163);
        logger.info("正常删除中间一个作品:{}", reMessage.toString());
        Assert.assertEquals("正常删除中间一个作品", 0, reMessage.getCode());

        list = honorTopMapper.getHonorList( globalManager.getEventId(),0, 10000);
        logger.info("删除作品后情况:\n{}", list.toString());

        reMessage = honorTopService.delHonorTop( 1161);
        logger.info("删除第一个作品:{}", reMessage.toString());
        Assert.assertEquals("删除第一个作品", 0, reMessage.getCode());

        list = honorTopMapper.getHonorList( globalManager.getEventId(),0, 10000);
        logger.info("删除作品后情况:\n{}", list.toString());


        reMessage = honorTopService.delHonorTop( 1167);
        logger.info("删除最后一个作品:{}", reMessage.toString());
        Assert.assertEquals("删除最后一个作品", 0, reMessage.getCode());

        list = honorTopMapper.getHonorList( globalManager.getEventId(),0, 10000);
        logger.info("删除作品后情况:\n{}", list.toString());

        reMessage = honorTopService.delHonorTop( 1167);
        logger.info("删除不存在的作品:{}", reMessage.toString());
        Assert.assertEquals("删除不存在的作品", 2, reMessage.getCode());

    }


    @Test
    public void testGetPreHonorTopByTime(){

        ReMessage reMessage = honorTopService.getPreHonorTopByTime(TimeUtil.getCurrentYearStartTime(),TimeUtil.getCurrentYearEndTime());
        logger.info(reMessage.toString());
    }

    @Test
    public void testGetThisYearHonor(){
        setEventId("201807");
        logger.info(honorTopService.getThisYearHonor().toString());
    }

    public void clear() {
        String sql = "delete from eventSys." + globalManager.getEventId() + "_honortop";
        jt.execute(sql);
    }


    public void setEventId(String eventId){
        globalManager.setEventId(eventId);
    }
}
