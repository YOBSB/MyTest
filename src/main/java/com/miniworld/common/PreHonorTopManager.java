package com.miniworld.common;

import com.miniworld.common.GlobalManager;
import com.miniworld.dao.HonorTopMapper;
import com.miniworld.dao.SeasonMapper;
import com.miniworld.entity.PreHonorTop;
import com.miniworld.entity.Season;
import com.miniworld.entity.Works;
import com.miniworld.entity.WorksHonor;
import com.miniworld.utils.TimeUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PreHonorTopManager {

    private PreHonorTop preHonorTop;

    private ReentrantReadWriteLock preHonorRWLock;

    private Boolean isInit = false;

    @Resource
    private GlobalManager globalManager;

    @Resource
    private SeasonMapper seasonMapper;

    @Resource
    private HonorTopMapper honorTopMapper;

    public PreHonorTopManager() {
        this.preHonorRWLock = new ReentrantReadWriteLock();
        this.preHonorTop = new PreHonorTop();
        ArrayList<PreHonorTop.SeasonInfo> seasonInfoArrayList = new ArrayList<>();
        this.preHonorTop.setSeasonInfoList(seasonInfoArrayList);
    }

    public PreHonorTop getPreHonorTop() {
        try {
            preHonorRWLock.readLock().lock();
            return this.preHonorTop;
        } finally {
            preHonorRWLock.readLock().unlock();
        }
    }

    public void setPreHonorTop(PreHonorTop preHonorTop) {
        try {
            preHonorRWLock.readLock().lock();
            this.preHonorTop = preHonorTop;
        } finally {
            preHonorRWLock.readLock().unlock();
        }
    }

    public void update() {
        try {
            preHonorRWLock.writeLock().lock();
            ArrayList<PreHonorTop.SeasonInfo> seasonInfoArrayList = new ArrayList<>();
            this.preHonorTop.setSeasonInfoList(seasonInfoArrayList);
            synFromMysql();
            this.isInit = true;
        } finally {
            preHonorRWLock.writeLock().unlock();
        }
    }

    public void synFromMysql() {
        List<Season> seasonList = seasonMapper.selectByTime(TimeUtil.getCurrentYearStartTime(), TimeUtil.getCurrentYearEndTime());
        for (Season season : seasonList) {
            PreHonorTop.SeasonInfo seasonInfo = new PreHonorTop.SeasonInfo();
            seasonInfo.setSeason(season);
            if (season.getSeasonLife() != 1) {
                LinkedList<Works> worksLinkedList = honorTopMapper.getWorksInfoBeginRank(season.getId(), 1);
                seasonInfo.setWorks(worksLinkedList);
            }
            preHonorTop.getSeasonInfoList().add(seasonInfo);
        }

    }


    public void clean() {
        try {
            preHonorRWLock.writeLock().lock();
            this.preHonorTop = new PreHonorTop();
            ArrayList<PreHonorTop.SeasonInfo> seasonInfoArrayList = new ArrayList<>();
            this.preHonorTop.setSeasonInfoList(seasonInfoArrayList);
        } finally {
            preHonorRWLock.writeLock().unlock();
        }
    }


    public Boolean getInit() {
        return isInit;
    }

    public void setInit(Boolean init) {
        isInit = init;
    }
}
