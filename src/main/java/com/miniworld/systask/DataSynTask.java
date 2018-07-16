package com.miniworld.systask;

import com.miniworld.common.GlobalManager;
import com.miniworld.common.RedisConst;
import com.miniworld.common.SpringContextHolder;
import com.miniworld.dao.WorksDao;
import com.miniworld.entity.Works;
import com.miniworld.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DataSynTask implements Runnable {

    /**
     * 把redis中的数据同步到mysql中
     * 同步作品总点击数，周点击数
     */

    private GlobalManager globalManager;
    private WorksDao worksDao;
    private int type; //1为定时更新，2为赛季结束的时候更新

    public DataSynTask(int type) {
        this.globalManager = SpringContextHolder.getBean("globalManager");
        this.worksDao = SpringContextHolder.getBean("worksDao");
        this.type=type;
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void run() {
        String collectName;
        if(type==1){
            if (globalManager.getHeatChangeState() == 1) {
                collectName = RedisConst.COLLECTION_HEAT_CHANGE_1;
                globalManager.setHeatChangeState(2);
            } else {
                collectName = RedisConst.COLLECTION_HEAT_CHANGE_2;
                globalManager.setHeatChangeState(1);
            }
            synRedisToMysql(collectName);
        }else if(type==2){
            synRedisToMysql(RedisConst.COLLECTION_HEAT_CHANGE_1);
            synRedisToMysql(RedisConst.COLLECTION_HEAT_CHANGE_2);
        }
    }

    private void synRedisToMysql(String collectName){
        logger.info("更新作品点击数到mysql中，本次操作的改动集合为:" + collectName);
        Set<String> set = RedisUtil.smembers(collectName);
        if (set == null || set.isEmpty()) {
            logger.info("更新作品点击数到mysql操作，本次操作的改动集合为+" + collectName + "，集合为空，不需要同步数据\n");
            return;
        }
        ArrayList<Works> worksList = new ArrayList<>(1000);
        int num = 0;
        for ( String strObj : set) {
            Integer intWid = Integer.valueOf(strObj);
            Works works = new Works();
            try {
                works = RedisUtil.GetWorksByWid(intWid,2);
            } catch (ClassCastException e) {
                logger.error("数据同步出错，作品id=%d， error：" + intWid, e.getMessage());
            }
            if (num++ >= 999) {
                worksDao.upWorksHeat(globalManager.getEventId(), worksList);
                worksList.clear();
                num = 0;
            }
            worksList.add(works);
            RedisUtil.spop(collectName);
        }

        if (num != 0) {
            worksDao.upWorksHeat(globalManager.getEventId(), worksList);
        }

        logger.info("更新作品点击数到mysql操作完成，本次操作的改动集合为:" + collectName);
    }
}