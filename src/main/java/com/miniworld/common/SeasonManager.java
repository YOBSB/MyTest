package com.miniworld.common;

import com.miniworld.dao.WorksDao;
import com.miniworld.entity.Works;
import com.miniworld.systask.DataSynTask;
import com.miniworld.utils.RedisUtil;
import com.miniworld.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

public class SeasonManager {


    @Resource
    private GlobalManager globalManager;

    @Resource
    private SeasonManager seasonManager;


    @Resource
    private WorksDao worksDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void eventStart(String eventId,String eventSysName,Long startTime, Long endTime, Long submitStartTime, Long submitEndTime) {
        globalManager.setEventId(eventId);
        globalManager.setEventSysName(eventSysName);
        globalManager.setEventSysStrartTime(startTime);
        globalManager.setEventSysEndTime(endTime);
        globalManager.setEventSysSubmitStartTime(submitStartTime);
        globalManager.setEventSysSubmitEndTime(submitEndTime);
        dataInit(eventId);
    }

    public void eventEnd() {
        globalManager.setEventId("");
        globalManager.setEventSysName("");
        globalManager.setEventSysStrartTime(0L);
        globalManager.setEventSysEndTime(0L);
        globalManager.setEventSysSubmitStartTime(0L);
        globalManager.setEventSysSubmitEndTime(0L);
        //将点击数更新到数据库，这里是同步操作，非异步
        new DataSynTask(2).run();
        RedisUtil.DelEventSysCol();
        CleanWorksTreeSet();
    }

    public void treeSetRemove(Works works) {
        if(works==null) return;
        globalManager.getNewestTreeSet().remove(works);
        globalManager.getPopulTreeSet().remove(works);
        globalManager.getWeekTreeSet().remove(works);

    }

    public void treeSetAdd(Works works) {
        works=removePriInfo(works);
        globalManager.getNewestTreeSet().add(works);
        globalManager.getPopulTreeSet().add(works);
        globalManager.getWeekTreeSet().add(works);
    }

    public Works removePriInfo(Works works){
        works.setPhone("");
        works.setQq("");
        works.setMail("");
        return works;
    }

    public void dataInit(String seasonId){
        logger.info("删除redis中赛事系统的集合");
        RedisUtil.DelEventSysCol();
        logger.info("将数据同步到redis和treeSet中");
        int offset = 0;
        int limit = 1000;
        while (true) {
            List<Works> worksList = worksDao.getWorksByLimit(seasonId, offset, limit);
            Iterator<Works> iterator = worksList.iterator();
            while (iterator.hasNext()) {
                Works works = iterator.next();
                RedisUtil.SetWorksInRedis(works);
                RedisUtil.AddWorksState(works.getWorksId(), works.getWorksState());
                if (works.getWorksState().equals(1)) {
                    works=seasonManager.removePriInfo(works);
                    globalManager.getNewestTreeSet().add(works);
                    globalManager.getPopulTreeSet().add(works);
                    if (works.getWeekHeatTime().equals(TimeUtil.getWeekNum())) {
                        globalManager.getWeekTreeSet().add(works);
                    }
                }
            }
            if (worksList.size() < limit) {
                break;
            }
            offset += limit;
        }
    }
    /**
     * 清空作品队列
     */
    public void CleanWorksTreeSet() {
        globalManager.getPopulTreeSet().clear();
        globalManager.getWeekTreeSet().clear();
        globalManager.getNewestTreeSet().clear();
    }
}
