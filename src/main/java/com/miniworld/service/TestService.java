package com.miniworld.service;

import com.miniworld.entity.TestUser;

import java.util.HashMap;

public interface TestService {
    TestUser getTestUser(HashMap<String,Object> map);
}
