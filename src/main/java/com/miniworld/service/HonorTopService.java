package com.miniworld.service;

import com.miniworld.common.ReMessage;
import com.miniworld.entity.WorksHonor;

import java.util.List;

public interface HonorTopService {
    List<WorksHonor> getHonorList(String eventId, int page, int num);

    int getHonorSize(String eventId);

    ReMessage updateHonorTop(Integer wid, Integer rank);

    ReMessage addHonorTop(Integer wid);

    ReMessage delHonorTop(Integer rank);

    ReMessage getPreHonorTopByTime(Long startTime, Long endTime);

    ReMessage getPreHonorTopByEventId(String eventId);

    ReMessage getThisYearHonor();

    void worksStateUpdateByWid(Integer worksId);

    void worksStateUpdateByUid(Integer miniId);
}
