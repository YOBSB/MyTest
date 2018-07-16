package com.miniworld.dao;

import com.miniworld.entity.Works;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface WorksDao {
    List<Works> getWorksByLimit(@Param("eventId")String eventId,@Param("offset")int offset, @Param("limit")int limit);
    void upWorksHeat(@Param("eventId")String eventId,@Param("list") ArrayList<Works> list);
    Works getByUid(@Param("eventId")String eventId,@Param("uid") Integer uid);
    boolean submitWorks(HashMap<String ,Object> map);
    Works getWorksIdByMiniId(@Param(value="miniId") Integer miniId,@Param(value="eventId") String eventId);
    Works getWorksByWorksId(@Param(value="worksId") Integer worksId,@Param(value="eventId") String eventId);
}
