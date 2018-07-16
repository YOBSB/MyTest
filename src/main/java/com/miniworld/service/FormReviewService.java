package com.miniworld.service;

import com.miniworld.common.ReMessage;
import com.miniworld.entity.Works;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface FormReviewService {
	ReMessage<Map<String, Object>> getAllWorks(String eventId,Integer pageNum,Integer pageSize);
    
	ReMessage<Map<String, Object>> getWorksByWorksState(Integer worksState,String eventId,Integer pageNum,Integer pageSize);
    
    Works getWorksByWorksId(Integer worksId,String eventId);
    
    Works getWorksByMiniId(Integer miniId,String eventId);
    
    Integer updateWorks(Integer miniId,Integer worksState,Long updateTime,String eventId);
    
    Integer deleteWorks(Integer miniId,String eventId);
    
    ReMessage checkState(Integer miniId, Integer worksState,String eventId);
}
