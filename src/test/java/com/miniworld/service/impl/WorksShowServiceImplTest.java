package com.miniworld.service.impl;

import com.miniworld.BaseTest;
import com.miniworld.InitData;
import com.miniworld.common.GlobalManager;
import com.miniworld.common.ReMessage;
import com.miniworld.common.RedisConst;
import com.miniworld.entity.Works;
import com.miniworld.service.WorksShowService;
import com.miniworld.utils.TimeUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

public class WorksShowServiceImplTest extends BaseTest {

    private Boolean isSetRedis = false;
    private boolean isSetMysql = false;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private WorksShowService worksShowService;


    @Resource
    private GlobalManager globalManager;

    @Before
    public void init() {
        InitData initData = new InitData();
        initData.initRedisWorksSet(globalManager, isSetRedis,  isSetMysql);
    }

    @Test
    public void testGetDefaultList() {
        ReMessage reMessage;
        reMessage = worksShowService.getDefaultList(8);
        logger.info("随机作品集：" + reMessage.toString());
    }

    @Test
    public void testGetPopularList() {
        ReMessage reMessage;
        reMessage = worksShowService.getPopularList(8, 1);
        logger.info("人气作品集：" + reMessage.toString());
    }

    @Test
    public void testGetNewestList() {
        ReMessage reMessage;
        reMessage = worksShowService.getNewestList(12, 1);
        logger.info("最新作品集：" + reMessage.toString());
        logger.info(System.currentTimeMillis() + "");
    }

    @Test
    public void testGetWeekList() {
        ReMessage reMessage;
        reMessage = worksShowService.getWeekList(18, 1);
        logger.info("本周最热作品集：" + reMessage.toString());
    }


    @Test
    public void testIncrHeat() {
        ReMessage reMessage;
        //正常点击
        reMessage = worksShowService.incrHet(1190, 999);
        logger.info("正常点击" + reMessage.toString());
        Assert.assertEquals("正常点击",0,reMessage.getCode());

        //连续点击
        reMessage = worksShowService.incrHet(1190, 999);
        logger.info("连续点击" + reMessage.toString());
        Assert.assertEquals("连续点击",3,reMessage.getCode());


        //连续点击
        reMessage = worksShowService.incrHet(190, 999);
        logger.info("不存在的wid" + reMessage.toString());
        Assert.assertEquals("不存在的wid",4,reMessage.getCode());

        //将190持续点击到201
        logger.info("将作品1190连续点击11次，查看人气、本周最热作品集");
        worksShowService.incrHet(1190, 990);
        worksShowService.incrHet(1190, 991);
        worksShowService.incrHet(1190, 992);
        worksShowService.incrHet(1190, 993);
        worksShowService.incrHet(1190, 994);
        worksShowService.incrHet(1190, 995);
        worksShowService.incrHet(1190, 996);
        worksShowService.incrHet(1190, 997);
        worksShowService.incrHet(1190, 998);
        worksShowService.incrHet(1190, 999);
        worksShowService.incrHet(1190, 1000);

        logger.info("人气：num=3，page=1：" + worksShowService.getPopularList(3, 1));
        logger.info("本周最热：num=3，page=1：" + worksShowService.getWeekList(3, 1));

        logger.info("测试点击平均耗费时间");
        Long time = System.nanoTime();
        worksShowService.incrHet(1190, 990);
        worksShowService.incrHet(1190, 991);
        worksShowService.incrHet(1190, 992);
        worksShowService.incrHet(1190, 993);
        worksShowService.incrHet(1190, 994);
        worksShowService.incrHet(1190, 995);
        worksShowService.incrHet(1190, 996);
        worksShowService.incrHet(1190, 997);
        worksShowService.incrHet(1190, 998);
        worksShowService.incrHet(1190, 999);
        worksShowService.incrHet(1190, 1000);
        logger.info("点击11次耗费纳秒:" + (System.nanoTime() - time));
    }


    @Test
    public void test555() {
        logger.info(globalManager.getUpdateTime().equals(TimeUtil.getDay())+"");
    }


    @Test
    public void delHash() {

    }
}
