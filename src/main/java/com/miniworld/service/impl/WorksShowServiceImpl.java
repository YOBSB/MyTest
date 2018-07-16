package com.miniworld.service.impl;

import com.miniworld.common.GlobalManager;
import com.miniworld.common.ReMessage;
import com.miniworld.common.RedisConst;
import com.miniworld.common.SeasonManager;
import com.miniworld.dao.WorksDao;
import com.miniworld.entity.Works;
import com.miniworld.exception.ParamException;
import com.miniworld.service.WorksShowService;
import com.miniworld.utils.RedisLock;
import com.miniworld.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.*;

@Service("worksShowService")
public class WorksShowServiceImpl implements WorksShowService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private GlobalManager globalManager;

    @Resource
    private SeasonManager seasonManager;

    @Resource
    private JedisPool jedisPool;

    @Resource
    private WorksDao worksDao;

    public ReMessage getDefaultList(Integer num) {
        ReMessage reMessage = new ReMessage();

        List<String> worksIdList = RedisUtil.srandmember(RedisConst.COLLECTION_AUDITED, num);
        List<Works> worksList = new ArrayList<>();
        for (String widObj : worksIdList) {
            Integer wid = Integer.valueOf(widObj);
            Works works = new Works();
            try {
                works = RedisUtil.GetWorksByWid(wid, 1);
            } catch (ClassCastException e) {
                logger.error("解析works对象失败:%s\n", e.getMessage());
            }
            worksList.add(works);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("pageTotal", RedisUtil.scard(RedisConst.COLLECTION_AUDITED) / num + 1);
        map.put("worksNum", worksList.size());
        map.put("works", worksList);
        reMessage.setCode(0);
        reMessage.setMsg("操作成功");
        reMessage.setData(map);
        return reMessage;
    }

    public ReMessage getPopularList(Integer num, Integer page) {
        ReMessage reMessage = new ReMessage();
        List<Works> list = globalManager.getPopulTreeSet().getElemsHead(num, page);
        Map<String, Object> map = new HashMap<>();
        int pageTotal;
        if (globalManager.getPopulTreeSet().size() % num == 0) {
            pageTotal = globalManager.getPopulTreeSet().size() / num;
        } else {
            pageTotal = globalManager.getPopulTreeSet().size() / num + 1;
        }
        map.put("pageTotal", pageTotal);
        map.put("currPage", page);
        map.put("worksNum", list.size());
        map.put("works", list);
        reMessage.setCode(0);
        reMessage.setMsg("操作成功");
        reMessage.setData(map);
        return reMessage;
    }

    public ReMessage getNewestList(Integer num, Integer page) {
        ReMessage reMessage = new ReMessage();
        List<Works> list = globalManager.getNewestTreeSet().getElemsHead(num, page);

        Iterator<Works> iterator = list.iterator();
        while (iterator.hasNext()) {
            Works w = iterator.next();
            w.setWorksHeat(Integer.valueOf(RedisUtil.hget(RedisConst.COLLECTION_WORKS, w.getWorksId() + RedisConst.SUF_WORKS_HEAT)));
            w.setWeekHeat(Integer.valueOf(RedisUtil.hget(RedisConst.COLLECTION_WORKS, w.getWorksId() + RedisConst.SUF_WORKS_WEEK_HEATE)));
            w.setWeekHeatTime(Integer.valueOf(RedisUtil.hget(RedisConst.COLLECTION_WORKS, w.getWorksId() + RedisConst.SUF_WORKS_WEEK_HEATE_TIME)));
        }
        Map<String, Object> map = new HashMap<>();
        int pageTotal;
        if (globalManager.getNewestTreeSet().size() % num == 0) {
            pageTotal = globalManager.getNewestTreeSet().size() / num;
        } else {
            pageTotal = globalManager.getNewestTreeSet().size() / num + 1;
        }
        map.put("works", list);
        map.put("pageTotal", pageTotal);
        map.put("currPage", page);
        reMessage.setCode(0);
        reMessage.setMsg("操作成功");
        reMessage.setData(map);
        return reMessage;
    }

    public ReMessage getWeekList(Integer num, Integer page) {
        ReMessage reMessage = new ReMessage();
        List<Works> list = globalManager.getWeekTreeSet().getElemsHead(num, page);
        Map<String, Object> map = new HashMap<>();
        int pageTotal;
        if (globalManager.getWeekTreeSet().size() % num == 0) {
            pageTotal = globalManager.getWeekTreeSet().size() / num;
        } else {
            pageTotal = globalManager.getWeekTreeSet().size() / num + 1;
        }
        map.put("pageTotal", pageTotal);
        map.put("currPage", page);
        map.put("works", list);
        reMessage.setCode(0);
        reMessage.setMsg("操作成功");
        reMessage.setData(map);
        return reMessage;
    }

    public Works getByUid(Integer uid) {
        return worksDao.getByUid(globalManager.getEventId(), uid);
    }

    public ReMessage incrHet(Integer wid, Integer uid) {
        ReMessage reMessage = new ReMessage();

        /**
         * 获取点击的读锁
         * 判断是否存在该作品以及该作品的状态是否是已审核
         * 判断短时间内是否重复点击
         * 获取该产品的redis锁
         * 作品总点击量增加
         * 作品周点击量增加
         */
        RedisLock redisLock = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis == null) {
                throw new NullPointerException("jedis is null");
            }
            redisLock = new RedisLock(jedis, RedisConst.PRE_LOCKED + wid, 1, 1000);
            globalManager.getHeatRWLock().readLock().lock();
            Boolean isMember = jedis.sismember(RedisConst.COLLECTION_AUDITED, wid + "");
            if (!isMember) {
                return new ReMessage(4, "该作品不存在或未审核");
            }
            // Object clickObj = redisTemplate.opsForHash().get(RedisConst.COLLECTION_WORKS_HEAT, wid + "_" + uid);
            String clickStr = jedis.hget(RedisConst.COLLECTION_WORKS_HEAT, wid + "_" + uid);
            if (clickStr != null) {
                try {
                    Long clickTime = Long.valueOf(clickStr);
                    Long now = System.currentTimeMillis();
                    if (now - clickTime < 5000) {
                        reMessage.setMsg("多次点击");
                        reMessage.setCode(3);
                        logger.info("多次点击");
                        logger.info("popSize={},weekSize={}", globalManager.getPopulTreeSet().size(), globalManager.getWeekTreeSet().size());
                        return reMessage;
                    }
                } catch (ClassCastException e) {
                    logger.error("系统错误，时间转换错误：clickStr=" + clickStr + "  :" + e.getMessage());
                    reMessage.setCode(-1);
                    reMessage.setMsg("系统错误，时间转换错误：" + e.getMessage());
                    return reMessage;
                }
            }

            if (redisLock.lock()) {
                Works works = RedisUtil.GetWorksByWid(wid, 1);
                globalManager.getPopulTreeSet().remove(works);
                globalManager.getWeekTreeSet().remove(works);

                Integer heat = works.getWorksHeat();
                if (heat == null) {
                    works.setWorksHeat(1);
                    works.setWeekHeatTime(globalManager.getWeekNum());
                } else {
                    works.setWorksHeat(++heat);
                }

                Integer weekHeat = works.getWeekHeat();
                Integer weekHeatTime = works.getWeekHeatTime();
                if (weekHeat == null || weekHeatTime == null) {
                    works.setWeekHeat(1);
                    works.setWeekHeatTime(globalManager.getWeekNum());
                } else if (weekHeatTime != globalManager.getWeekNum()) {
                    works.setWeekHeat(1);
                    works.setWeekHeatTime(globalManager.getWeekNum());
                } else {
                    works.setWeekHeat(++weekHeat);
                    works.setWeekHeatTime(globalManager.getWeekNum());
                }

                //将该用户id放入作品点击列表中，并放进现在的时间
                jedis.hset(RedisConst.COLLECTION_WORKS_HEAT, wid + "_" + uid, System.currentTimeMillis() + "");

                //更新redis中作品的worksHeat，worksWeekHeat，worksWeekHeatTime
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_HEAT, works.getWorksHeat() + "");
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_WEEK_HEATE, works.getWeekHeat() + "");
                jedis.hset(RedisConst.COLLECTION_WORKS, wid + RedisConst.SUF_WORKS_WEEK_HEATE_TIME, works.getWeekHeatTime() + "");

                //更新人气、本周热门列表
                globalManager.getPopulTreeSet().add(works);
                globalManager.getWeekTreeSet().add(works);
                logger.info("点击成功");
                logger.info("popSize={},weekSize={}", globalManager.getPopulTreeSet().size(), globalManager.getWeekTreeSet().size());
                //更新作品改动集
                if (globalManager.getHeatChangeState() == 1) {
                    jedis.sadd(RedisConst.COLLECTION_HEAT_CHANGE_1, works.getWorksId().toString());
                } else {
                    jedis.sadd(RedisConst.COLLECTION_HEAT_CHANGE_2, works.getWorksId().toString());
                }
            } else {
                logger.error("incrHet，获取锁超时:wid={},uid={}", wid, uid);
                return new ReMessage(-1, "incrHet获取锁超时");
            }

        } finally {

            globalManager.getHeatRWLock().readLock().unlock();
            if (redisLock != null) {
                redisLock.unlock();
            }

            if (jedis != null) {
                jedis.close();
            }
        }
        reMessage.setCode(0);
        reMessage.setMsg("操作成功");
        return reMessage;
    }
}
