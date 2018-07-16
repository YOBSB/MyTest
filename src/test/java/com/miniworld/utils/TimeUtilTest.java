package com.miniworld.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtilTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testGetCurrentYearStartTime(){
          logger.info(TimeUtil.getCurrentYearStartTime()+"");
          logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TimeUtil.getCurrentYearStartTime()));

        logger.info(TimeUtil.getCurrentYearEndTime()+"");
        logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TimeUtil.getCurrentYearEndTime()));
    }


    @Test
    public void testIsInTimeClock() throws Exception{
         logger.info(TimeUtil.isInTimeClock("20:25:00","20:35:00")+"");
         logger.info(TimeUtil.isInTimeClock("00:25:00","00:35:00")+"");
         logger.info(TimeUtil.isInTimeClock("20:24:00","20:26:00")+"");
    }
    @Test
    public void testGetDay() throws Exception{
         logger.info(TimeUtil.getDay());
    }

}
