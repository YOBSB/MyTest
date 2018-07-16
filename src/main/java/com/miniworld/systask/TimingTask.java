package com.miniworld.systask;

import com.miniworld.common.GlobalManager;
import com.miniworld.common.RedisConst;
import com.miniworld.common.SpringContextHolder;
import com.miniworld.dao.WorksDao;
import com.miniworld.exception.ParamException;
import com.miniworld.utils.RedisUtil;
import com.miniworld.utils.TimeUtil;
import jdk.nashorn.internal.runtime.Timing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import redis.clients.jedis.ScanResult;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TimingTask implements Runnable {

    /**
     * 定时执行的任务，系统最为重要的线程
     * 管理数据同步操作
     * 管理数据一致性检测
     * 1.作品状态集合一致性检测：时间定在凌晨3:00    检测已通过审核作品集、未审核作品集、已拒绝作品集、已屏蔽作品集是否有错
     * 2.
     */
    private final static String TIEM_START = "3:00:00";
    private final static String TIEM_END = "3:10:00";

    private GlobalManager globalManager;


    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public TimingTask() {
        this.globalManager = SpringContextHolder.getBean("globalManager");
    }

    @Override
    public void run() {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        try {
            if (TimeUtil.isInTimeClock(TIEM_START, TIEM_END) == 1 && !globalManager.getUpdateTime().equals(TimeUtil.getDay())
                    && !globalManager.getEventId().equals("")) {
                /**
                 * 凌晨3点，偷偷更新数据
                 * 1.检测数据集合
                 * 2.清除本周热门集合
                 * 3.更新周数
                 * 4.清除作品点击集合中时间久远的值
                 */
                try {
                    globalManager.getHeatRWLock().writeLock().lock();
                    singleThreadExecutor.execute(new CheckedTask());
                    if(TimeUtil.getWeekNum()!=globalManager.getWeekNum()) {
                        globalManager.getWeekTreeSet().clear();
                        globalManager.setWeekNum(TimeUtil.getWeekNum());
                    }
//                    CleanData();
                    RedisUtil.del(RedisConst.COLLECTION_WORKS_HEAT);
                } finally {
                    globalManager.getHeatRWLock().writeLock().unlock();
                    globalManager.setUpdateTime(TimeUtil.getDay());
                }
            }
        } catch (ParseException e) {
            logger.error("判断时间错误：%s\n", e.getMessage());
        }

        /**
         * 定时任务
         * 1.更新点击数到数据库中
         * 2.
         */
        Long startTime = globalManager.getEventSysStrartTime();
        Long endTime = globalManager.getEventSysEndTime();
        if (startTime == null || endTime == null) {
            logger.error("赛事的开始或结束时间为null；startTime={},endTime={}", startTime, endTime);
            return;
        }

        Long now = System.currentTimeMillis();
        if (now < globalManager.getEventSysStrartTime() || now > globalManager.getEventSysEndTime()) {
            logger.info("当前时间不在赛事进行时间内");
            return;
        }

        singleThreadExecutor.execute(new DataSynTask(1));
        singleThreadExecutor.shutdown();

    }
//
//    public void CleanData() {
//        ScanResult<Map.Entry<String, String>> result = RedisUtil.hscan(RedisConst.COLLECTION_WORKS_HEAT, 10000);
//
//        if(result==null){
//            logger.error(" ScanResult is null");
//            return;
//        }
//
//        List<Map.Entry<String, String>> cursor = result.get
//        while (cursor.hasNext()) {
//            Map.Entry<Object, Object> entry = cursor.next();
//            try {
//                String key = entry.getKey().toString();
//                Long clickTime = Long.valueOf((String) entry.getValue());
//                if (System.currentTimeMillis() - clickTime > 5000) {
//                    redisTemplate.opsForHash().delete(RedisConst.COLLECTION_WORKS_HEAT, key);
//                }
//                System.out.println(entry.getKey() + ":" + entry.getValue());
//            } catch (ClassCastException e) {
//                logger.error("clickTime 类型转换出错：key：" + entry.getKey() + "  value:" + entry.getValue());
//            }
//
//        }
//        try {
//            cursor.close();
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//        }
//
//    }
}
