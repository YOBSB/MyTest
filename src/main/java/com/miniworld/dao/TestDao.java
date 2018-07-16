package com.miniworld.dao;

import com.miniworld.entity.TestUser;
import com.miniworld.entity.Works;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;

public interface TestDao {
    void addTestUser(TestUser user);
    TestUser getTestUser(HashMap<String ,Object> map);
    ArrayList<Works> getAll(HashMap<String,Object> map);
    ArrayList<Works> getAll_2(@Param("tableName") String tableName);
}

