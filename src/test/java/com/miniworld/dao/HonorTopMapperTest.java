package com.miniworld.dao;

import com.miniworld.BaseTest;
import com.miniworld.common.GlobalManager;
import com.miniworld.entity.Works;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.LinkedList;

public class HonorTopMapperTest extends BaseTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private GlobalManager globalManager;

    @Resource
    private HonorTopMapper honorTopMapper;

    @Before
    public void init(){
        globalManager.setEventId("20180713");
    }


    @Test
    public void getWorksInfoBeginRankTest(){
        LinkedList<Works> worksLinkedList = honorTopMapper.getWorksInfoBeginRank(globalManager.getEventId(),1);

        logger.info(worksLinkedList.toString());
    }

}
