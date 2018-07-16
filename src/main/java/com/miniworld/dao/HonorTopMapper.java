package com.miniworld.dao;

import com.miniworld.entity.Works;
import com.miniworld.entity.WorksHonor;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public interface HonorTopMapper {
    List<WorksHonor> getHonorList(@Param("eventId") String eventId, @Param("offset") int offset, @Param("limit") int limit);
    LinkedList<WorksHonor> getHonorListBeginRank(@Param("eventId") String eventId, @Param("rank") int rank);
    LinkedList<Works> getWorksInfoBeginRank(@Param("eventId") String eventId, @Param("rank") int rank);
    WorksHonor getLastHonor(@Param("eventId") String eventId);
    WorksHonor getHonorByWid(@Param("eventId") String eventId,@Param("wid") Integer wid);
    WorksHonor getHonorByRank(@Param("eventId") String eventId,@Param("rank") Integer rank);
    WorksHonor getHonorByMiniId(@Param("eventId") String eventId,@Param("miniId") Integer miniId);
    void updateHonorTop(HashMap<String,Object> queryMap);
    void upHonorRankList(@Param("eventId") String eventId, @Param("list") List<WorksHonor> list);
    void delHonorTopByWid(@Param("eventId") String eventId,@Param("wid") Integer wid);
    void cleanHonorTopByRank(@Param("eventId") String eventId,@Param("rank") Integer rank);
    void addHonorTop(HashMap<String,Object> queryMap);
    int getHonorSize(@Param("eventId") String eventId);
    void delHonorTopByRank(@Param("eventId") String eventId,@Param("rank") Integer rank);

}
