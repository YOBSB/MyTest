package com.miniworld.common;

import com.miniworld.entity.Works;
import com.sun.corba.se.impl.orbutil.concurrent.ReentrantMutex;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GlobalManager {

    /**
     * 人气作品集合，（默认1000个）
     */
    private WorksTreeSet<Works> populTreeSet;

    /**
     * 最新作品集合，（默认1000个）
     */
    private WorksTreeSet<Works> newestTreeSet;


    /**
     * 每周最热，（默认1000个）
     */
    private WorksTreeSet<Works> weekTreeSet;


    /**
     * 热度读写锁
     */
    private ReentrantReadWriteLock heatRWLock;

    /**
     * 作品点击保存集合
     */
    private volatile int heatChangeState = 1;

    /**
     * 本周周次
     */
    private volatile int weekNum;

    /**
     * 上一次更新数据库时间（以日为单位)
     */
    private volatile String updateTime="";

    /**
     * 当前系统赛事id
     */
    private volatile String eventId="";
    
    /**
     * 当前系统赛事名称
     */
    private volatile String eventSysName="";

    /**
     * 当前赛事开始时间
     */
    private volatile Long eventSysStrartTime;

    /**
     * 当前赛事结束时间
     */
    private volatile Long eventSysEndTime;
    
    /**
     * 当前赛事投稿时间
     */
    private volatile Long eventSysSubmitStartTime;
    
    /**
     * 当前赛事投稿结束时间
     */
    private volatile Long eventSysSubmitEndTime;
   
    /**
     * 是否初始化
     */
    private volatile Boolean isInit=false;


    /**
     * 互斥锁，荣誉榜单删除数据时使用
     */
    private ReentrantLock honorTopLock;



    /**
     * @param populTreeSet
     * @param newestTreeSet
     * @param weekTreeSet
     */
    public GlobalManager(WorksTreeSet<Works> populTreeSet, WorksTreeSet<Works> newestTreeSet,
                         WorksTreeSet<Works> weekTreeSet, ReentrantReadWriteLock heatRWLock,ReentrantLock honorTopLock,int weekNum) {
        this.heatRWLock = heatRWLock;
        this.honorTopLock=honorTopLock;
        this.populTreeSet = populTreeSet;
        this.newestTreeSet = newestTreeSet;
        this.weekTreeSet = weekTreeSet;
        this.weekNum=weekNum;
    }


    public WorksTreeSet<Works> getPopulTreeSet() {
        return populTreeSet;
    }

    public void setPopulTreeSet(WorksTreeSet<Works> populTreeSet) {
        this.populTreeSet = populTreeSet;
    }

    public WorksTreeSet<Works> getNewestTreeSet() {
        return newestTreeSet;
    }

    public void setNewestTreeSet(WorksTreeSet<Works> newestTreeSet) {
        this.newestTreeSet = newestTreeSet;
    }

    public WorksTreeSet<Works> getWeekTreeSet() {
        return weekTreeSet;
    }

    public void setWeekTreeSet(WorksTreeSet<Works> weekTreeSet) {
        this.weekTreeSet = weekTreeSet;
    }


    public ReentrantReadWriteLock getHeatRWLock() {
        return heatRWLock;
    }

    public void setHeatRWLock(ReentrantReadWriteLock heatRWLock) {
        this.heatRWLock = heatRWLock;
    }

    public int getHeatChangeState() {
        return heatChangeState;
    }

    public void setHeatChangeState(int heatChangeState) {
        this.heatChangeState = heatChangeState;
    }

    public int getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    
    

    public String getEventSysName() {
		return eventSysName;
	}


	public void setEventSysName(String eventSysName) {
		this.eventSysName = eventSysName;
	}



    public Boolean getInit() {
        return isInit;
    }

    public void setInit(Boolean init) {
        isInit = init;
    }

    public Long getEventSysStrartTime() {
		return eventSysStrartTime;
	}

	public void setEventSysStrartTime(Long eventSysStrartTime) {
		this.eventSysStrartTime = eventSysStrartTime;
	}

	public Long getEventSysEndTime() {
		return eventSysEndTime;
	}

	public void setEventSysEndTime(Long eventSysEndTime) {
		this.eventSysEndTime = eventSysEndTime;
	}

	public Long getEventSysSubmitStartTime() {
		return eventSysSubmitStartTime;
	}


	public void setEventSysSubmitStartTime(Long eventSysSubmitStartTime) {
		this.eventSysSubmitStartTime = eventSysSubmitStartTime;
	}


	public Long getEventSysSubmitEndTime() {
		return eventSysSubmitEndTime;
	}


	public void setEventSysSubmitEndTime(Long eventSysSubmitEndTime) {
		this.eventSysSubmitEndTime = eventSysSubmitEndTime;
	}


	public ReentrantLock getHonorTopLock() {
        return honorTopLock;
    }

    public void setHonorTopLock(ReentrantLock honorTopLock) {
        this.honorTopLock = honorTopLock;
    }
}
