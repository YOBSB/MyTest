package com.miniworld.service.impl;

import com.miniworld.jdbcTemplatedao.JdbcWorksDao;
import com.miniworld.jdbcTemplatedao.impl.JdbcWorksDaoImpl;
import org.junit.Test;

public class JdbcWorksDaoTest {

    @Test
    public void test(){
        JdbcWorksDao jdbcWorksDao = new JdbcWorksDaoImpl();
        jdbcWorksDao.createTableBySeasonId("20180612");
    }
}
