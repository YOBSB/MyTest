package com.miniworld.service.impl;

import com.miniworld.BaseTest;
import com.miniworld.common.TaskManager;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisCommands;

import javax.annotation.Resource;

public class HeatServiceImplTest extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testDebug(){
        logger.debug("debug");
        logger.error("error");
        logger.info("info");

    }

    @Resource
    private TaskManager taskManager;

    @Test
    public void test(){
      taskManager.Init();
    }

}
