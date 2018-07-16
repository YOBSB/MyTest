package com.miniworld.bean;

import com.miniworld.common.*;
import com.miniworld.comparator.WorksHeatComparator;
import com.miniworld.comparator.WorksNewestComparator;
import com.miniworld.comparator.WorksWeekComparator;
import com.miniworld.entity.Works;
import com.miniworld.utils.TimeUtil;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Configuration
public class SystemBeans {

    @Value("${worksShow.popular.maxSize}")
    private int popularMaxSize;

    @Value("${worksShow.newest.maxSize}")
    private int newestMaxSize;

    @Value("${worksShow.week.maxSize}")
    private int weekMaxSize;

    @Bean(name = "globalManager")
    public GlobalManager globalManager() {

        ReentrantReadWriteLock heatRWLock = new ReentrantReadWriteLock();
        ReentrantLock honorTopLock = new ReentrantLock();

        if (popularMaxSize == 0) popularMaxSize = 1000;
        if (newestMaxSize == 0) newestMaxSize = 1000;
        if (weekMaxSize == 0) weekMaxSize = 1000;

        WorksTreeSet<Works> populTreeSet = new WorksTreeSet<>(popularMaxSize, new WorksHeatComparator());
        WorksTreeSet<Works> newestTreeSet = new WorksTreeSet<>(newestMaxSize, new WorksNewestComparator());
        WorksTreeSet<Works> weekTreeSet = new WorksTreeSet<>(weekMaxSize, new WorksWeekComparator());
        return new GlobalManager(populTreeSet, newestTreeSet, weekTreeSet, heatRWLock, honorTopLock, TimeUtil.getWeekNum());
    }

    @Bean(name = "taskManager")
    public TaskManager taskManager() {
        return new TaskManager();
    }

    @Bean
    public PreHonorTopManager preHonorTopManager(){
        return new PreHonorTopManager();
    }

    @Bean
    public SeasonManager seasonManager(){
        return new SeasonManager();
    }

}
