package com.miniworld.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.miniworld.entity.Works;

public interface FormReviewMapper {

	List<Works> selectAll(@Param(value="eventId")String eventId);
    
	List<Works> selectByWorksState(@Param(value="worksState")Integer worksId,@Param(value="eventId")String eventId);

	Works selectByWorksId(@Param(value="worksId")Integer worksId,@Param(value="eventId")String eventId);

	Works selectByMiniId(@Param(value="miniId")Integer miniId,@Param(value="eventId")String eventId);

	Integer updateByMiniId(@Param("miniId") Integer miniId ,@Param("worksState") Integer worksState,@Param("updateTime") Long updateTime,@Param(value="eventId")String eventId);//要使用@Param注解
	
	List<Works> selectWorksData(@Param(value="eventId")String eventId);
	
	Integer deleteWorks(@Param(value="miniId")Integer miniId,@Param(value="eventId")String eventId);
	
	Integer checkState(@Param(value="miniId")Integer miniId,@Param(value="eventId")String eventId);
}
