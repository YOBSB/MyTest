package com.miniworld.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

public class EventSysPreDestroy {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private TaskManager taskManager;

    @PostConstruct
    public void preDestroy() {
        logger.info("项目结束前执行代码");
        logger.info("关闭定时任务");
        taskManager.getExecutorService().shutdown();
    }
}
