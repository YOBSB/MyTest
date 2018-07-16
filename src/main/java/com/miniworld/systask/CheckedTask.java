package com.miniworld.systask;

import com.miniworld.common.GlobalManager;
import com.miniworld.common.RedisConst;
import com.miniworld.common.SpringContextHolder;
import com.miniworld.dao.WorksDao;
import com.miniworld.entity.Works;
import com.miniworld.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统定时任务，负责检测
 */
public class CheckedTask implements Runnable {


    private WorksDao worksDao;

    private GlobalManager globalManager;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public CheckedTask() {
        this.worksDao = SpringContextHolder.getBean("worksDao");
        this.globalManager = SpringContextHolder.getBean("globalManager");
    }


    @Override
    public void run() {
        logger.info("检测作品集id与数据库是否一致");
        if (worksDao == null) {
            logger.error("worksDao or redisTemplate is null");
            return;
        }
        /**
         * 检测作品集id与数据库是否一致
         */
        int offset = 0;
        int limit = 1000;
        while (true) {
            List<Works> worksList = worksDao.getWorksByLimit(globalManager.getEventId(), offset, limit);
            Boolean isExist;
            for (Works works : worksList) {
                switch (works.getWorksState()) {
                    case 0:
                        isExist = RedisUtil.sIsMember(RedisConst.COLLECTION_UNAUDITED, works.getWorksId() + "");
                        if (!isExist) {
                            logger.error("检测redis与mysql数据一致性出错,未审核的作品不在redis中的COLLECTION_UNAUDITED集合中");
                            delWidFromCollet(works);
                            RedisUtil.sAdd(RedisConst.COLLECTION_UNAUDITED, works.getWorksId() + "");
                        }
                        break;
                    case 1:
                        isExist = RedisUtil.sIsMember(RedisConst.COLLECTION_AUDITED, works.getWorksId() + "");
                        if (!isExist) {
                            logger.error("检测redis与mysql数据一致性出错,未审核的作品不在redis中的COLLECTION_AUDITED集合中");
                            delWidFromCollet(works);
                            RedisUtil.sAdd(RedisConst.COLLECTION_AUDITED, works.getWorksId() + "");
                        }
                        break;
                    case 2:
                        isExist = RedisUtil.sIsMember(RedisConst.COLLECTION_RESUSED, works.getWorksId() + "");
                        if (!isExist) {
                            logger.error("检测redis与mysql数据一致性出错,未审核的作品不在redis中的COLLECTION_RESUSED集合中");
                            delWidFromCollet(works);
                            RedisUtil.sAdd(RedisConst.COLLECTION_RESUSED, works.getWorksId() + "");
                        }
                        break;
                    case 3:
                        isExist = RedisUtil.sIsMember(RedisConst.COLLECTION_SHIELDING, works.getWorksId() + "");
                        if (!isExist) {
                            logger.error("检测redis与mysql数据一致性出错,未审核的作品不在redis中的COLLECTION_RESUSED集合中");
                            delWidFromCollet(works);
                            RedisUtil.sAdd(RedisConst.COLLECTION_SHIELDING, works.getWorksId() + "");
                        }
                        break;
                    default:
                        logger.error("检测redis与mysql数据一致性出错，works的state为不在正常范围内：%s", works.toString());
                        break;
                }
            }

            if (worksList.size() < limit) {
                break;
            }
            offset += limit;
        }

        logger.info("检测完毕");
    }

    /**
     * @param works 作品属性
     * @return
     * @description 将该作品id在未审核、已审核、已屏蔽、已拒绝中的集合删除
     */
    private void delWidFromCollet(Works works) {
        RedisUtil.srem(RedisConst.COLLECTION_UNAUDITED, works.getWorksId() + "");
        RedisUtil.srem(RedisConst.COLLECTION_AUDITED, works.getWorksId() + "");
        RedisUtil.srem(RedisConst.COLLECTION_RESUSED, works.getWorksId() + "");
        RedisUtil.srem(RedisConst.COLLECTION_SHIELDING, works.getWorksId() + "");
    }
}
