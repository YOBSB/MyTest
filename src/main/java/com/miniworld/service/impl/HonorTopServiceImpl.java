package com.miniworld.service.impl;

import com.miniworld.common.GlobalManager;
import com.miniworld.common.PreHonorTopManager;
import com.miniworld.common.ReMessage;
import com.miniworld.dao.HonorTopMapper;
import com.miniworld.dao.SeasonMapper;
import com.miniworld.dao.WorksDao;
import com.miniworld.entity.PreHonorTop;
import com.miniworld.entity.Season;
import com.miniworld.entity.Works;
import com.miniworld.entity.WorksHonor;
import com.miniworld.exception.ParamException;
import com.miniworld.service.HonorTopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.rmi.Remote;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service("honorTopService")
public class HonorTopServiceImpl implements HonorTopService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private HonorTopMapper honorTopMapper;

    @Resource
    private GlobalManager globalManager;

    @Resource
    private WorksDao worksDao;

    @Resource
    private SeasonMapper seasonMapper;

    @Resource
    private PreHonorTopManager preHonorTopManager;

    public List<WorksHonor> getHonorList(String eventId, int page, int num) {
        return honorTopMapper.getHonorList(eventId, page, num);
    }

    public int getHonorSize(String eventId) {
        return honorTopMapper.getHonorSize(eventId);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ReMessage updateHonorTop(Integer wid, Integer rank) {
        try {
            globalManager.getHonorTopLock().lock();
            //判断是否有该作品
            Works works = worksDao.getWorksByWorksId(wid, globalManager.getEventId());
            if (works == null) {
                logger.info("作品不存在:wid={}",wid);
                return new ReMessage(2, "作品不存在");
            }
            //判断该作品的状态是否是“已审核”
            if (works.getWorksState() != 1) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("works", works);
                logger.info("作品状态不是已审核:wid={}",wid);
                return new ReMessage(3, "作品不是已审核状态，无法添加", map);
            }

            //判断rank是否超出范围
            int honorSize = honorTopMapper.getHonorSize(globalManager.getEventId());
            if(rank>honorSize){
                logger.info("rank不在范围内,wid={},rank={},size={]",wid,rank,honorSize);
                return new ReMessage(4, "rank不在范围内");
            }

            //查看该作品是否在榜单中
            WorksHonor worksHonor = honorTopMapper.getHonorByWid(globalManager.getEventId(), wid);
            if (worksHonor == null) {
                //作品不在榜单中，直接修改
                HashMap<String, Object> queryMap = new HashMap<>();
                queryMap.put("eventId", globalManager.getEventId());
                queryMap.put("worksId", wid);
                queryMap.put("rank", rank);
                queryMap.put("miniId", works.getMiniId());
                queryMap.put("updateTime", System.currentTimeMillis());
                honorTopMapper.updateHonorTop(queryMap);
                logger.info("榜单更新中:wid={}",wid);
                return new ReMessage(0, "操作成功");
            }

            //清空原作品rank位置的数据
            honorTopMapper.cleanHonorTopByRank(globalManager.getEventId(), worksHonor.getRank());

            //更新数据到指定的rank中
            //作品不在榜单中，直接修改
            HashMap<String, Object> queryMap = new HashMap<>();
            queryMap.put("eventId", globalManager.getEventId());
            queryMap.put("worksId", wid);
            queryMap.put("rank", rank);
            queryMap.put("miniId", works.getMiniId());
            queryMap.put("updateTime", System.currentTimeMillis());
            honorTopMapper.updateHonorTop(queryMap);
            logger.info("榜单更新中:wid={}",wid);
            return new ReMessage(0, "操作成功");
        } finally {
            globalManager.getHonorTopLock().unlock();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ReMessage addHonorTop(Integer wid) {
        try {
            globalManager.getHonorTopLock().lock();
            Works works = worksDao.getWorksByWorksId(wid, globalManager.getEventId());
            if (works == null) {
                logger.info("作品不存在:wid={}",wid);
                return new ReMessage(2, "作品不存在");
            }

            if (honorTopMapper.getHonorByWid(globalManager.getEventId(), wid) != null) {
                logger.info("作品已在榜单中:wid={}",wid);
                return new ReMessage(3, "作品已在榜单中");
            }

            //判断该作品的状态是否是“已审核”
            if (works.getWorksState() != 1) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("works", works);
                logger.info("作品不是已审核状态，无法添加:wid={}",wid);
                return new ReMessage(4, "作品不是已审核状态，无法添加", map);
            }

            WorksHonor worksHonor = honorTopMapper.getLastHonor(globalManager.getEventId());
            int rank = 1;
            if (worksHonor != null) {
                rank = worksHonor.getRank() + 1;
            }
            HashMap<String, Object> queryMap = new HashMap<>();
            queryMap.put("eventId", globalManager.getEventId());
            queryMap.put("worksId", wid);
            queryMap.put("rank", rank);
            queryMap.put("miniId", works.getMiniId());
            queryMap.put("createTime", System.currentTimeMillis());
            queryMap.put("updateTime", System.currentTimeMillis());
            honorTopMapper.addHonorTop(queryMap);
            logger.info("操作成功:wid={}",wid);
            return new ReMessage(0, "操作成功", queryMap);
        } finally {
            globalManager.getHonorTopLock().unlock();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ReMessage delHonorTop(Integer rank) {
        try {
            globalManager.getHonorTopLock().lock();
            //判断该作品是否在榜单中
            WorksHonor worksHonor = honorTopMapper.getHonorByRank(globalManager.getEventId(), rank);
            if (worksHonor == null) {
                logger.info("该rank位置不在榜单中，无法删除，rank={}",rank);
                return new ReMessage(2, "该rank位置不在榜单中，无法删除");
            }
            //判断是否是最后一个
            WorksHonor lastHonor = honorTopMapper.getLastHonor(globalManager.getEventId());
            if(lastHonor.getWorksId().equals(worksHonor.getWorksId())){
                honorTopMapper.delHonorTopByRank(globalManager.getEventId(), worksHonor.getRank());
                return new ReMessage(0, "操作成功");
            }

            //清空原作品rank位置的数据
            honorTopMapper.cleanHonorTopByRank(globalManager.getEventId(), worksHonor.getRank());
        } finally {
            globalManager.getHonorTopLock().unlock();
        }
        logger.info("删除榜单作品成功，rank={}",rank);
        return new ReMessage(0, "操作成功");
    }

    public ReMessage getPreHonorTopByTime(Long startTime, Long endTime) {
        if (startTime == null || endTime == null || startTime > endTime) throw new ParamException("参数数值不对");
        List<Season> seasonList = seasonMapper.selectByTime(startTime, endTime);
        HashMap<String, Object> map = new HashMap<>();
        if (seasonList == null) {
            map.put("total", 0);
        } else {
            map.put("total", seasonList.size());
        }
        map.put("seasons", seasonList);
        return new ReMessage(0, "操作成功", map);
    }

    public ReMessage getPreHonorTopByEventId(String eventId) {
        if (eventId == null) throw new ParamException("参数数值不对");
        Season season = seasonMapper.selectByPrimaryKey(eventId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("season", season);
        return new ReMessage(0, "操作成功", map);
    }

    public ReMessage getThisYearHonor() {
        if (!preHonorTopManager.getInit()) {
            preHonorTopManager.update();
        }
        PreHonorTop preHonorTop = preHonorTopManager.getPreHonorTop();
        return new ReMessage(0, "操作成功", preHonorTop);
    }



    public void worksStateUpdateByWid(Integer worksId){
        logger.info("作品状态改变，查看是否在榜单中");
        WorksHonor worksHonor = honorTopMapper.getHonorByWid(globalManager.getEventId(), worksId);
        if(worksHonor!=null){
            logger.info("作品在榜单中，将其从榜单中删除");
            delHonorTop(worksHonor.getWorksId());
        }

    }

    public void worksStateUpdateByUid(Integer miniId){
        logger.info("作品状态改变，查看是否在榜单中");
        WorksHonor worksHonor = honorTopMapper.getHonorByMiniId(globalManager.getEventId(), miniId);
        if(worksHonor!=null){
            logger.info("作品在榜单中，将其从榜单中删除");
            delHonorTop(worksHonor.getWorksId());
        }

    }
}
