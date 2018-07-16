package com.miniworld.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniworld.common.GlobalManager;
import com.miniworld.common.RedisConst;
import com.miniworld.entity.Works;

import com.miniworld.exception.ParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ScanResult;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Component("RedisUtil")
public class RedisUtil {

    private final static Logger log = LoggerFactory.getLogger(RedisUtil.class);
    private static JedisPool jedisPool = null;

    /**
     * 初始化Redis连接池
     */
    public static void initialPool() {
        Properties prop = new Properties();
        try {
            prop.load(RedisUtil.class.getClassLoader().getResourceAsStream("config/redis.properties"));
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(Integer.valueOf(prop.getProperty("redis.pool.maxActive")));
            config.setMaxIdle(Integer.valueOf((prop.getProperty("redis.pool.maxIdle"))));
            config.setMinIdle(Integer.valueOf((prop.getProperty("redis.pool.minIdle"))));
            config.setMaxWaitMillis(Long.valueOf((prop.getProperty("redis.pool.maxWaitMillis"))));
            config.setTestOnBorrow(true);
            config.setTestOnReturn(true);
            config.setTestWhileIdle(true);
            String host = prop.getProperty("redis.host");
            String port = prop.getProperty("redis.port");
            String timeOut = prop.getProperty("redis.timeout");
            String password = prop.getProperty("redis.password");
            jedisPool = new JedisPool(config, host, Integer.valueOf(port), Integer.valueOf(timeOut), password);
        } catch (Exception e) {
            log.error("First create JedisPool error : " + e);
        }
        log.info("=====初始化redis池成功!");
    }

    /**
     * @param wid  :要获取作品id
     * @param type :如果是对外的作品数据，则不放入电话、邮箱、qq等个人信息
     *             1：对外 2：对内
     * @return works:返回操作结果，如果内部集合为空，则返回空的works
     * @MethodName : GetWorksByWid
     * @Description : 根据作品id获取作品的信息
     */
    public static Works GetWorksByWid(Integer wid, Integer type) throws ClassCastException {
        if (wid == null) throw new ParamException("wid 参数为空");
        Works works = new Works();
        works.setWorksId(wid);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_UID);
            if (str != null) {
                works.setMiniId(Integer.valueOf(str));
            }
            str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_NAME);
            if (str != null) {
                works.setName(str);
            }

            if (type != 1) {
                str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_QQ);
                if (str != null) {
                    works.setQq(str);
                }

                str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_MAIL);
                if (str != null) {
                    works.setMail(str);
                }

                str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_PHONE);
                if (str != null) {
                    works.setPhone(str);
                }
            }

            str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_MATES);
            if (str != null) {
                works.setTeamMates(str);
            }

            str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_MAP_ID);
            if (str != null) {
                works.setWorksMapId(str);
            }

            str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_WORKS_NAME);
            if (str != null) {
                works.setWorksName(str);
            }

            str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_INTR);
            if (str != null) {
                works.setWorksIntroduce(str);
            }

            str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_MAIN_IMG);
            if (str != null) {
                works.setWorksMainImage(str);
            }

            str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_MAIN_SMALL_IMG);
            if (str != null) {
                works.setMainSmallImage(str);
            }

            str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_IMG_1);
            if (str != null) {
                works.setImage1(str);
            }

            str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_SMALL_IMG_1);
            if (str != null) {
                works.setImageSmall1(str);
            }

            str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_IMG_2);
            if (str != null) {
                works.setImage2(str);
            }

            str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_SMALL_IMG_2);
            if (str != null) {
                works.setImageSmall2(str);
            }

            str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_IMG_3);
            if (str != null) {
                works.setImage3(str);
            }

            str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_SMALL_IMG_3);
            if (str != null) {
                works.setImageSmall3(str);
            }

            str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_STATE);
            if (str != null) {
                works.setWorksState(Integer.valueOf(str));
            }

            str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_SUBMIT_TIME);
            if (str != null) {
                works.setSubmissionTime(Long.valueOf(str));
            }

            str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_CREATE_TIME);
            if (str != null) {
                works.setCreateTime(Long.valueOf(str));
            }

            str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_UPDATE_TIME);
            if (str != null) {
                works.setUpdateTime(Long.valueOf(str));
            }

            str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_HEAT);
            if (str != null) {
                works.setWorksHeat(Integer.valueOf(str));
            }

            str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_WEEK_HEATE);
            if (str != null) {
                works.setWeekHeat(Integer.valueOf(str));
            }

            str = jedis.hget(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_WEEK_HEATE_TIME);
            if (str != null) {
                works.setWeekHeatTime(Integer.valueOf(str));
            }
        } catch (Exception e) {
            log.error("GetWorksByWid初始化jedis异常：" + e);
            throw e;
        } finally {
            closeJedis(jedis);
        }
        return works;
    }

    /**
     * @param works         :要获取作品id
     * @MethodName : SetWorksInRedis
     * @Description : 将works信息存入redis中
     */
    public static void SetWorksInRedis(Works works) {

        Integer wid = works.getWorksId();
        if (wid == null) throw new ParamException("works id is null");
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_UID, works.getMiniId() + "");

            if (works.getName() != null) {
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_NAME, works.getName());
            }

            if (works.getQq() != null) {
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_QQ, works.getQq());
            }

            if (works.getMail() != null) {
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_MAIL, works.getMail());
            }

            if (works.getPhone() != null) {
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_PHONE, works.getPhone());
            }

            if (works.getTeamMates() != null) {
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_MATES, works.getTeamMates());
            }

            if (works.getWorksMapId() != null) {
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_MAP_ID, works.getWorksMapId());
            }

            if (works.getWorksName() != null) {
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_WORKS_NAME, works.getWorksName());
            }

            if (works.getWorksIntroduce() != null) {
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_INTR, works.getWorksIntroduce());
            }

            if (works.getWorksMainImage() != null) {
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_MAIN_IMG, works.getWorksMainImage());
            }

            if (works.getMainSmallImage() != null) {
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_MAIN_SMALL_IMG, works.getMainSmallImage());
            }

            if (works.getImage1() != null) {
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_IMG_1, works.getImage1());
            }

            if (works.getImageSmall1() != null) {
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_SMALL_IMG_1, works.getImageSmall1());
            }

            if (works.getImage2() != null) {
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_IMG_2, works.getImage2());
            }

            if (works.getImageSmall2() != null) {
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_SMALL_IMG_2, works.getImageSmall2());
            }

            if (works.getImage3() != null) {
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_IMG_3, works.getImage3());
            }

            if (works.getImageSmall3() != null) {
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_SMALL_IMG_3, works.getImageSmall3());
            }

            if (works.getWorksState() != null) {
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_STATE, works.getWorksState() + "");
            }

            if (works.getSubmissionTime() != null) {
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_SUBMIT_TIME, works.getSubmissionTime() + "");
            }

            if (works.getCreateTime() != null) {
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_CREATE_TIME, works.getCreateTime() + "");
            }

            if (works.getUpdateTime() != null) {
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_UPDATE_TIME, works.getUpdateTime() + "");
            }
            jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_HEAT, works.getWorksHeat() + "");
            jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_WEEK_HEATE, works.getWeekHeat() + "");
            jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_WEEK_HEATE_TIME, works.getWeekHeatTime() + "");
        } catch (Exception e) {
            log.error("Pop初始化jedis异常：" + e);
            throw e;
        } finally {
            closeJedis(jedis);
        }

    }


    /**
     * @param wid
     * @param state
     * @return
     * @throws NullPointerException
     * @Desc 将该作品id存入对应的状态集合中
     */
    public static boolean AddWorksState(Integer wid, Integer state) throws NullPointerException {
        if (wid == null || state == null) throw new NullPointerException();
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            switch (state) {
                case 0:
                    jedis.sadd(RedisConst.COLLECTION_UNAUDITED, wid + "");
                    break;
                case 1:
                    jedis.sadd(RedisConst.COLLECTION_AUDITED, wid + "");
                    break;
                case 2:
                    jedis.sadd(RedisConst.COLLECTION_RESUSED, wid + "");
                    break;
                case 3:
                    jedis.sadd(RedisConst.COLLECTION_SHIELDING, wid + "");
                    break;
                default:
                    return false;
            }
            jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_STATE, state + "");
            return true;
        } catch (Exception e) {
            log.error("AddWorksState 初始化jedis异常：" + e);
            throw e;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * @param wid
     * @param state
     * @return
     * @throws NullPointerException
     * @Desc 改变作品的状态
     */
    public static boolean ChangeWorksState(Integer wid, Integer state) throws NullPointerException {
        if (wid == null || state == null) throw new NullPointerException();
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.srem(RedisConst.COLLECTION_UNAUDITED, wid + "");
            jedis.srem(RedisConst.COLLECTION_AUDITED, wid + "");
            jedis.srem(RedisConst.COLLECTION_RESUSED, wid + "");
            jedis.srem(RedisConst.COLLECTION_SHIELDING, wid + "");
            return AddWorksState(wid, state);
        } catch (Exception e) {
            log.error("AddWorksState 初始化jedis异常：" + e);
            throw e;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * @return
     * @Desc 删除redis中赛事系统用到的集合
     */
    public static void DelEventSysCol() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(RedisConst.COLLECTION_WORKS_HEAT);
            jedis.del(RedisConst.COLLECTION_WORKS_HEAT);
            jedis.del(RedisConst.COLLECTION_WORKS);
            jedis.del(RedisConst.COLLECTION_SHIELDING);
            jedis.del(RedisConst.COLLECTION_UNAUDITED);
            jedis.del(RedisConst.COLLECTION_AUDITED);
            jedis.del(RedisConst.COLLECTION_RESUSED);
            jedis.del(RedisConst.COLLECTION_HEAT_CHANGE_1);
            jedis.del(RedisConst.COLLECTION_HEAT_CHANGE_2);
        } catch (Exception e) {
            log.error("jedis异常：" + e);
            if (jedis != null) {
                jedis.close();
            }
        } finally {
            closeJedis(jedis);
        }
    }


    public static List<String> srandmember(String key, int num) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.srandmember(key, num);
        } catch (Exception e) {
            log.error("jedis异常：" + e);
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    public static Long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            log.error("jedis异常：" + e);
            if (jedis != null) {
                jedis.close();
            }
        } finally {
            closeJedis(jedis);
        }
        return 0L;
    }

    public static String hget(String key, String feild) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(key, feild);
        } catch (Exception e) {
            log.error("jedis异常：" + e);
            if (jedis != null) {
                jedis.close();
            }
        } finally {
            closeJedis(jedis);
        }
        return "";
    }

    public static Boolean sIsMember(String key, String feild) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sismember(key, feild);
        } catch (Exception e) {
            log.error("jedis异常：" + e);
            if (jedis != null) {
                jedis.close();
            }
        } finally {
            closeJedis(jedis);
        }
        return false;
    }


    public static Boolean sAdd(String key, String feild) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.sadd(key, feild);
        } catch (Exception e) {
            log.error("jedis异常：" + e);
            if (jedis != null) {
                jedis.close();
            }
        } finally {
            closeJedis(jedis);
        }
        return false;
    }

    public static Boolean srem(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.srem(key, member);
            if (jedis != null) {
                jedis.close();
            }
        } catch (Exception e) {
            log.error("jedis异常：" + e);
        } finally {
            closeJedis(jedis);
        }
        return false;
    }

    public static Set<String> smembers(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.smembers(key);
        } catch (Exception e) {
            log.error("jedis异常：" + e);
            if (jedis != null) {
                jedis.close();
            }
        } finally {
            closeJedis(jedis);
        }
        return new HashSet<String>() {};
    }

    public static String spop(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.spop(key);
        } catch (Exception e) {
            log.error("jedis异常：" + e);
            if (jedis != null) {
                jedis.close();
            }
        } finally {
            closeJedis(jedis);
        }
        return "";
    }

    public static ScanResult<Map.Entry<String, String>> hscan(String key, int cursor){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hscan(key,cursor);
        } catch (Exception e) {
            log.error("jedis异常：" + e);
            if (jedis != null) {
                jedis.close();
            }
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    public static void del(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            log.error("jedis异常：" + e);
            if (jedis != null) {
                jedis.close();
            }
        } finally {
            closeJedis(jedis);
        }
    }


    /**
     * closeJedis(释放redis资源)
     *
     * @param @param jedis
     * @return void
     * @throws
     * @Title: closeJedis
     */
    public static void closeJedis(Jedis jedis) {
        try {
            if (jedis != null) {
                jedis.close();
            }
        } catch (Exception e) {
            log.error("释放资源异常：" + e.getMessage());
        }
    }


}
