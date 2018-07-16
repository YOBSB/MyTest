package com.miniworld.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RedisLock {

    /**
     * 存储到redis中的锁标志
     */
    private static final String LOCKED = "LOCKED";

    /**
     * 默认请求锁超时时间,单位毫秒(ms)
     */
    private static Long TIME_OUT = 100L;

    /**
     * 默认锁有效时间，单位秒(s）
     */
    private static Integer EXPIRE = 60;

    /**
     * 锁标志对应的key
     */
    private String key;

    /**
     * 锁的有效时间(s)
     */
    private int expireTime = EXPIRE;

    /**
     * 请求锁的超时时间(ms)
     */
    private long timeOut = TIME_OUT;

    /**
     * 锁flag
     */
    private volatile boolean isLocked = false;


    private Jedis jedis;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 构造方法
     *
     * @param jedis Redis管理模板
     * @param key           锁定key
     * @param expireTime    锁过期时间 （秒）
     * @param timeOut       请求锁超时时间 (毫秒)
     */
    public RedisLock(Jedis jedis, String key, int expireTime, long timeOut) {
        this.key = key;
        this.expireTime = expireTime;
        this.timeOut = timeOut;
        this.jedis = jedis;
    }

    /**
     * 构造方法
     *
     * @param jedis Redis管理模板
     * @param key           锁定key
     * @param expireTime    锁过期时间
     */
    public RedisLock(Jedis jedis, String key, int expireTime) {
        this.key = key;
        this.expireTime = expireTime;
        this.jedis = jedis;
    }

    /**
     * 构造方法(默认请求锁超时时间30秒，锁过期时间60秒)
     *
     * @param jedis Redis管理模板
     * @param key           锁定key
     */
    public RedisLock(Jedis jedis, String key) {
        this.key = key;
        this.jedis = jedis;
    }

    public boolean lock() {
        // 系统当前时间，纳秒
        long nowTime = System.nanoTime();
        // 请求锁超时时间，纳秒
        long timeout = timeOut * 1000000;
        final Random random = new Random();


        // 不断循环向Master节点请求锁，当请求时间(System.nanoTime() - nano)超过设定的超时时间则放弃请求锁
        while (System.nanoTime() - nowTime< timeout) {
            /**
             * 使用set获得redis的锁，但redisTemplate目前不支持使用set加入过期时间，所以通过 jedis操作
             * set(key, "锁定的资源", "NX", "PX", expireTime);
             * NX: 表示只有当锁定资源不存在的时候才能 SET 成功。
             * PX: expire 表示锁定的资源的自动过期时间，单位是毫秒。
             * result数值：成功为：OK，失败为null
             */
            String result = jedis.set(key, LOCKED, "NX", "PX", expireTime);
            if (result != null && result.equals("OK")) {
                //TODO 获取锁成功
                isLocked = true;
                break;
            }
            // 获取锁失败时，应该在随机延时后进行重试，避免不同客户端同时重试导致谁都无法拿到锁的情况出现
            // 睡眠10毫秒后继续请求锁
            try {
                Thread.sleep(10, random.nextInt(50000));
            } catch (InterruptedException e) {
                logger.error("获取锁休眠被中断：", e);
            }
        }
        return isLocked;
    }

    public boolean isLock() {
        return jedis.exists(key);
    }

    public void unlock() {
        // 释放锁
        // 不管请求锁是否成功，只要已经上锁，客户端都会进行释放锁的操作
        if (isLocked) {
            jedis.del(key);
        }
    }
}
