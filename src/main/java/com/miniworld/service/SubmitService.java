package com.miniworld.service;

import java.util.HashMap;

import com.miniworld.entity.Works;

public interface SubmitService {
	boolean submitWorks(HashMap<String ,Object> map);
	Works getWorksIdByMiniId(Integer miniId,String eventID);
}
