package com.miniworld.service.impl;

import com.miniworld.dao.WorksDao;
import com.miniworld.entity.Works;
import com.miniworld.service.SubmitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service("submitService")
public class SubmitServiceImpl implements SubmitService {

    @Resource
    private WorksDao worksDao;
    
	@Override
	public boolean submitWorks(HashMap<String, Object> map) {
		return this.worksDao.submitWorks(map);
	}

	@Override
	public Works getWorksIdByMiniId(Integer miniId,String eventId) {
		return this.worksDao.getWorksIdByMiniId(miniId,eventId);
	}
}
