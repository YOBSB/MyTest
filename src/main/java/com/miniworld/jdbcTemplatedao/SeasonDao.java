package com.miniworld.jdbcTemplatedao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.miniworld.entity.Season;

public interface SeasonDao {

	void createSerson(String seasonId, String seasonName, String description, String keyWords, long startTime,
			long endTime, long createTime,long updateTime,int seasonLife, Long submitStartTime, Long submitEndTime);

	List querySeason();
	
	void insertSeason(Season season);
	
	void createTable();

	Season querySeasonById(String seasonId);

	String querySeasonLife();

	void updateSeason(Season season,String originalSeasonId);

	void deleteSeasonById(String seasonId);

	void stopSeasonById(String seasonId);

	void startSeasonById(String seasonId);

	
	
	
	
}
