package com.miniworld.dao;

import com.miniworld.entity.Season;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SeasonMapper {
    int deleteByPrimaryKey(String seasonId);

    int insert(Season record);

    int insertSelective(Season record);

    Season selectByPrimaryKey(String seasonId);

    int updateByPrimaryKeySelective(Season record);

    int updateByPrimaryKey(Season record);

    Season selectActiveSeason();

    List<Season> selectByTime(@Param("startTime") Long startTime, @Param("endTime")Long endTime);
    
    String selectLastestSeason();

    Integer isExistTable(@Param("table") String table);

}