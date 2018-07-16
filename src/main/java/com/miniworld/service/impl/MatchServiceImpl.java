package com.miniworld.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniworld.dao.MatchMapper;
import com.miniworld.entity.Match;
import com.miniworld.service.MatchService;

@Service("matchService")
public class MatchServiceImpl implements MatchService{
	
	@Autowired 
	private MatchMapper matchMapper;

	public Integer addInfo(Match match) {
		return matchMapper.insertSelective(match);
	}


	public Integer updateInfo(Match match) {
		return matchMapper.updateByPrimaryKeySelective(match);
	}


	public Match selectMatchBySeasonId(String seasonId) {
		return matchMapper.selectMatchBySeasonId(seasonId);
	}


}
