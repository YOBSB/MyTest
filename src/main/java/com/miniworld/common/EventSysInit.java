package com.miniworld.common;

import com.miniworld.dao.EventSysDao;
import com.miniworld.dao.SeasonMapper;
import com.miniworld.dao.WorksDao;
import com.miniworld.entity.Season;
import com.miniworld.entity.Works;
import com.miniworld.service.InitialService;
import com.miniworld.utils.RedisUtil;
import com.miniworld.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

/**
 * 实现ApplicationListener并且监控系统初始化事件
 * 使用注解的方式
 * 功能：
 * 系统初始化程序，如果系统中途宕机，重新启动，则要将数据恢复
 * 恢复globalManager实例中存储的数据
 * 恢复redis中存储的数据
 */
@Service
public class EventSysInit implements InitializingBean {

    @Resource
    private EventSysDao eventSysDao;

    @Resource
    private SeasonMapper seasonMapper;

    @Resource
    private WorksDao worksDao;

    @Resource
    private TaskManager taskManager;

    @Resource
    private GlobalManager globalManager;

    @Resource
    private InitialService initialService;

    @Resource
    private SeasonManager seasonManager;


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static int num = 0;

    @Override
    public void afterPropertiesSet() {
        logger.info("PostConstruct---");
        logger.info("第 {} 次初始化", num++);
        RedisUtil.initialPool();
        //启动系统定时任务
        initialService.initialDatabase();
        taskManager.Init();
        Check();
    }

    /**
     * 检测系统是否有初始化
     * 1.判断 admin_user 是否存在
     * 2.判断是否还有在进行的赛季
     * 3.如果赛季未结束，将数据库中的作品数据放入redis中
     */
    public void Check() {
        logger.info("检测系统是否初始化");
        int isExistAdminUser = eventSysDao.isTableExist("admin_user");
        if (isExistAdminUser != 1) {
            logger.error("系统未初始化");
            return;
        }

        isExistAdminUser = eventSysDao.isTableExist("season");
        if (isExistAdminUser != 1) {
            logger.error("season表不存在");
            return;
        }

        logger.info("admin_user表存在，系统已初始化");
        logger.info("检测系统是否还有未结束的赛季");

        Season season = seasonMapper.selectActiveSeason();
        if (season == null) {
            logger.info("当前不存在未结束的赛季");
            return;
        }

        isExistAdminUser = eventSysDao.isTableExist(season.getId() + "_works");
        if (isExistAdminUser != 1) {
            logger.info(season.getId() + "_works 表不存在");
            return;
        }

        logger.info("当前还存在未结束的赛季:{}", season.getId());
        seasonManager.eventStart(season.getId(),season.getSeasonName(),season.getStartTime(),season.getEndTime(),season.getSubmitStartTime(),season.getSubmitEndTime());
    }

}
