package com.miniworld.controller;

import com.miniworld.BaseTest;
import com.miniworld.common.GlobalManager;
import com.miniworld.entity.Works;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.resource.spi.work.Work;

import java.util.Iterator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class WorksShowControllerTest extends BaseTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Resource
    private GlobalManager globalManager;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testIncreaseHeat() throws Exception {

        logger.info("点击前数据");
        printTreeSet();
        //每个作品点击1次
        logger.info("作品1000 1001 点击2次");
        mockMvc.perform(get("/worksShow/incrHeat").param("wid", "1000").param("uid", "204798555").param("sign","b011b19984d56b9afd5708ffc5363f8c_1530519679")).andExpect(status().isOk()).andDo(print());
        mockMvc.perform(get("/worksShow/incrHeat").param("wid", "1000").param("uid", "204798556").param("sign","d9e5a64eec94b49661ae2f027acefb96_1530519685")).andExpect(status().isOk()).andDo(print());
        mockMvc.perform(get("/worksShow/incrHeat").param("wid", "1001").param("uid", "204798558").param("sign","44412241a6d1bb63b3b8c96ba1317c8f_1530519706")).andExpect(status().isOk()).andDo(print());
        mockMvc.perform(get("/worksShow/incrHeat").param("wid", "1001").param("uid", "204798559").param("sign","daa42c34fc6bc6d868173c57d8276bdd_1530519716")).andExpect(status().isOk()).andDo(print());

        logger.info("点击后数据数据");
        printTreeSet();

    }


    public void printTreeSet(){
        logger.info("人气列表");
        Iterator<Works> iterator = globalManager.getPopulTreeSet().iterator();
        while (iterator.hasNext()){
            logger.info(iterator.next().toString());
        }

        logger.info("本周最热列表");
       iterator = globalManager.getPopulTreeSet().iterator();
        while (iterator.hasNext()){
            logger.info(iterator.next().toString());
        }

        logger.info("最新列表");
       iterator = globalManager.getPopulTreeSet().iterator();
        while (iterator.hasNext()){
            logger.info(iterator.next().toString());
        }
    }

}
