package com.miniworld.systask;

import com.miniworld.BaseTest;
import com.miniworld.InitData;
import com.miniworld.common.GlobalManager;
import com.miniworld.common.ReMessage;
import com.miniworld.dao.TestDao;
import com.miniworld.dao.WorksDao;
import com.miniworld.entity.Works;
import com.miniworld.service.SubmitService;
import com.miniworld.service.WorksShowService;
import com.miniworld.utils.TimeUtil;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class DataSynTaskTest extends BaseTest {
    @Resource
    private GlobalManager globalManager;


    @Resource
    private WorksShowService worksShowService;

    @Autowired
    private SubmitService submitService;

    @Resource
    private WorksDao worksDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    boolean isSetRedis = true;
    boolean isSetMysql = false;

    @Before
    public void init() {
        InitData initData = new InitData(submitService);
        initData.initRedisWorksSet(globalManager, isSetRedis, isSetMysql);
    }

    @Test
    public void testDataSyn() {
        //将190持续点击到201
        logger.info("将1150-1200的作品各点击10次");
        for (int wid = 1150; wid <= 1200; wid++) {
            worksShowService.incrHet(wid, 990);
            worksShowService.incrHet(wid, 991);
            worksShowService.incrHet(wid, 992);
            worksShowService.incrHet(wid, 993);
            worksShowService.incrHet(wid, 994);
            worksShowService.incrHet(wid, 995);
            worksShowService.incrHet(wid, 996);
            worksShowService.incrHet(wid, 997);
            worksShowService.incrHet(wid, 998);
            worksShowService.incrHet(wid, 999);
            worksShowService.incrHet(wid, 1000);
        }

        logger.info("最受欢迎作品：" + worksShowService.getPopularList(5, 1));
        logger.info("本周最热：" + worksShowService.getPopularList(5, 1));
        logger.info("同步数据到数据库");
        new DataSynTask(1).run();

    }

    @Resource
    private TestDao testDao;

    @Test
    public void testGetAll() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("eventId", "2018_works");
        ArrayList<Works> arrayList = testDao.getAll_2("2018_works");
        logger.info(arrayList.toString());
    }

    @Test
    public void testUpdateWorks(){
        ArrayList<Works> worksList =new ArrayList<>();
        worksList.add(getWorks(1000));
        worksList.add(getWorks(1001));
        worksDao.upWorksHeat(globalManager.getEventId(),worksList);
    }

    private Works getWorks(Integer wid){
        Works works = new Works();
        works.setWorksId(wid);
        works.setMiniId(wid);
        works.setName("name_" + wid);
        works.setQq("123456789");
        works.setMail("123@qq.com");
        works.setPhone("18826589658");
        works.setTeamMates("123,1234,156");
        works.setWorksName("worksName_" + wid);
        works.setWorksIntroduce("这是我的作品，希望大家能多多支持");
//        works.setWorksImage("http://xxxxxx.jpg,http://xxxx.jpg");
        works.setWorksMainImage("http://xxx.jpg");
        works.setWorksMapId("12");
        works.setWorksState(1);
        works.setSubmissionTime(1529994414000L);
        works.setCreateTime(1529994414000L);
        works.setUpdateTime(1529994414000L);
        works.setWorksHeat(999);
        works.setWeekHeat(999);
        works.setWeekHeatTime(TimeUtil.getWeekNum());
        return works;
    }
}
