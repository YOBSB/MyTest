//package com.miniworld.utils;
//
//import com.miniworld.BaseTest;
//import com.miniworld.common.RedisConst;
//import com.miniworld.common.TaskManager;
//import com.miniworld.entity.Works;
//import com.miniworld.exception.ParamException;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.Cursor;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ScanOptions;
//import org.springframework.data.redis.core.StringRedisTemplate;
//
//import javax.annotation.Resource;
//import java.sql.Timestamp;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentSkipListSet;
//import java.util.concurrent.TimeUnit;
//
//
//public class RedisUtilTest extends BaseTest {
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//
//    @Test
//    public void testGetAndSetWorks() throws ClassCastException {
//
//        Works works1 = new Works();
//        works1.setWorksId(1000);
//        works1.setMiniId(1000);
//        works1.setName("name_" + 1000);
//        works1.setQq("123456789");
//        works1.setMail("123@qq.com");
//        works1.setPhone("18826589658");
//        works1.setTeamMates("123,1234,156");
//        works1.setWorksName("worksName_" + 1000);
//        works1.setWorksIntroduce("这是我的作品，希望大家能多多支持");
//
//        works1.setWorksState(3);
//        works1.setSubmissionTime(1529994414000L);
//        works1.setCreateTime(1529994414000L);
//        works1.setUpdateTime(1529994414000L);
//        works1.setWorksHeat(123);
//        works1.setWeekHeat(123);
//        works1.setWeekHeatTime(TimeUtil.getWeekNum());
//
//        logger.info("设置一个正常的works");
//        RedisUtil.SetWorksInRedis( works1);
//        logger.info("从redis获取works：" + RedisUtil.GetWorksByWid( 1000,1));
//
//        logger.info("设置一个空的works");
//        Works works2 = new Works();
//        try {
//            RedisUtil.SetWorksInRedis( works2);
//        } catch (ParamException e) {
//            logger.info(e.getMessage());
//        }
//
//        logger.info("设置一个wid 不为空的 works");
//        Works works3 = new Works();
//        works3.setWorksId(100);
//        RedisUtil.SetWorksInRedis( works3);
//        logger.info("从redis获取works：" + RedisUtil.GetWorksByWid( 100,1));
//
//    }
//
//
//    @Test
//    public void getTimestampTest() {
//        stringRedisTemplate.opsForValue().set("user", "user");
//        stringRedisTemplate.opsForValue().set("user1", "user1");
//        System.out.println("user is:" + stringRedisTemplate.opsForValue().get("user"));
//        System.out.println("user is:" + stringRedisTemplate.opsForValue().get("user"));
//        logger.debug("sdjfksjfdsk");
//    }
//
//
//    @Test
//    public void testKeys() {
//
//        Set<Object> set = stringRedisTemplate.opsForHash().keys("wid1");
//        logger.info(set.size() + "");
//        logger.info(set.toString());
//
//
//        logger.info(TimeUtil.getWeekNum() + "");
//
//        Timestamp timestamp = new Timestamp(1529412705L);
//
//
//        redisTemplate.opsForValue().set("test_timestamp", timestamp.getTime() + "");
//        String l = (String) redisTemplate.opsForValue().get("test_timestamp");
//        logger.info(l + "");
//
//
//        logger.debug("日志输出测试 Debug");
//        logger.trace("日志输出测试 Trace");
//        logger.info("日志输出测试 Info");
//    }
//
//
//    @Test
//    public void testCursor() {
//        Cursor<Map.Entry<Object, Object>> c = redisTemplate.opsForHash().scan(RedisConst.COLLECTION_WORKS_HEAT, ScanOptions.scanOptions().match("*").count(10000).build());
//        while (c.hasNext()) {
//            Map.Entry<Object, Object> entry = c.next();
//            try {
//                String key = entry.getKey().toString();
//                Long clickTime = Long.valueOf((String)entry.getValue());
//                if(System.currentTimeMillis()-clickTime>5000){
//                    redisTemplate.opsForHash().delete(RedisConst.COLLECTION_WORKS_HEAT,key);
//                }
//                System.out.println(entry.getKey() + ":" + entry.getValue());
//            }catch (ClassCastException e){
//                logger.error("clickTime 类型转换出错：key："+entry.getKey()+"  value:"+entry.getValue());
//            }
//
//        }
//    }
//}
