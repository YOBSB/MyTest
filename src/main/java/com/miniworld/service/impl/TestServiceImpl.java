package com.miniworld.service.impl;

import com.miniworld.dao.TestDao;
import com.miniworld.entity.TestUser;
import com.miniworld.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service("testService")
public class TestServiceImpl implements TestService {

    @Resource
    private TestDao testDao;

    public TestUser getTestUser(HashMap<String ,Object> map){
        return this.testDao.getTestUser(map);
    }
}
