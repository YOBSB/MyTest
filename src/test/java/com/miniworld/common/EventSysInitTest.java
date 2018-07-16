package com.miniworld.common;

import com.miniworld.BaseTest;
import com.miniworld.dao.EventSysDao;
import com.miniworld.dao.WorksDao;
import com.miniworld.entity.Works;
import com.miniworld.utils.RedisUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EventSysInitTest extends BaseTest {

    @Resource
    private EventSysDao eventSysDao;

    @Resource
    private WorksDao worksDao;



    @Resource
    private GlobalManager globalManager;

    @Resource
    private Jedis jedis;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testIsTableExist() {

        logger.info("一个不存在的表:{}", eventSysDao.isTableExist("iss"));
        logger.info("表名为空:{}", eventSysDao.isTableExist(""));
        logger.info("存在的表:{}", eventSysDao.isTableExist("admin_user"));
    }

    @Test
    public void testGetWorksList() {
//        JedisPool jedisPool = new JedisPool("10.0.0.95", 6379);
//        Jedis jedis =jedisPool.getResource();
        jedis.set("sjdkfjlks","123465465");

        List<Works> worksList = worksDao.getWorksByLimit("233", 0, 1000);
//        for (Works works : worksList) {
//            RedisUtil.SetWorksInRedis(redisTemplate, works);
//            RedisUtil.AddWorksState(redisTemplate, works.getWorksId(), works.getWorksState());
//        }
    }


}
