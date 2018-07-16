package com.miniworld.common;

import com.miniworld.service.InitialService;
import com.miniworld.systask.TimingTask;
import com.miniworld.utils.RedisUtil;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 执行系统定时任务的类
 */
public class TaskManager {

    /**
     * 定时任务线程池（单线操作）
     */
    private ScheduledExecutorService executorService;


    @Resource
    private GlobalManager globalManager;

    public synchronized void Init() {
        if (globalManager.getInit())
            return;

        globalManager.setInit(true);
        executorService = Executors.newSingleThreadScheduledExecutor();
        TimingTask timingTask = new TimingTask();
        executorService.scheduleAtFixedRate(timingTask, 5, 5, TimeUnit.MINUTES);

    }


    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ScheduledExecutorService executorService) {
        this.executorService = executorService;
    }
}
