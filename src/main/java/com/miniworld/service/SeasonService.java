package com.miniworld.service;


import java.util.List;

import com.miniworld.entity.Season;

public interface SeasonService {

	void createSerson(String seasonId, String seasonName, String description, String keyWords, long startTime,
			long endTime, long createTime, long updateTime,int seasonLife, Long submitStartTime1, Long submitEndTime1);

	List querySeason();

	Season querySeasonById(String seasonId);

	String judgeSeasonLife();
	void updateSeason(Season season,String originalSeasonId);

	void deleteSeasonById(String seasonId);
	void stopSeasonById(String seasonId);

	void startSeasonById(String seasonId);
	
	String queryLastSeason();
	
}
