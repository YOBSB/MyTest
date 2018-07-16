package com.miniworld.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniworld.dao.SeasonMapper;
import com.miniworld.entity.Season;
import com.miniworld.jdbcTemplatedao.MatchDao;
import com.miniworld.jdbcTemplatedao.SeasonDao;
import com.miniworld.service.SeasonService;

@Service("seasonService")
public class SeasonServiceImpl implements SeasonService {

	@Autowired
	private SeasonDao seasonDao;
	@Autowired
	private MatchDao matchDao;
	@Autowired
	private SeasonMapper seasonMapper;

	
	@Override
	public void createSerson(String seasonId, String seasonName, String description, String keyWords, long startTime,

			long endTime, long createTime,long updateTime,int seasonLife,Long submitStartTime, Long submitEndTime) {
		// TODO Auto-generated method stub

		seasonDao.createSerson(seasonId,seasonName,description,keyWords,startTime,endTime,createTime,updateTime,seasonLife,submitStartTime,submitEndTime);
		
	}

	@Override
	public List querySeason() {
		// TODO Auto-generated method stub
		return seasonDao.querySeason();
	}

	@Override
	public Season querySeasonById(String seasonId) {
		// TODO Auto-generated method stub
		Season season=seasonDao.querySeasonById(seasonId);
		return season;
	}

	@Override
	public String judgeSeasonLife() {
		// TODO Auto-generated method stub
		return seasonDao.querySeasonLife();
	}

	@Override
	public void updateSeason(Season season,String originalSeasonId) {
		// TODO Auto-generated method stub
		seasonDao.updateSeason(season,originalSeasonId);
	}

	@Override
	public void deleteSeasonById(String seasonId) {
		// TODO Auto-generated method stub
		seasonDao.deleteSeasonById(seasonId);
	}

	@Override
	public void stopSeasonById(String seasonId) {
		// TODO Auto-generated method stub
		seasonDao.stopSeasonById(seasonId);
	}

	@Override
	public void startSeasonById(String seasonId) {
		// TODO Auto-generated method stub
		seasonDao.startSeasonById(seasonId);
		
	}

	@Override
	public String queryLastSeason() {
		return seasonMapper.selectLastestSeason();
	}
	
}
