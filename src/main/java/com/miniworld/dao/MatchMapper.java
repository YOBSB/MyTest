package com.miniworld.dao;

import com.miniworld.entity.Match;

public interface MatchMapper {

	Integer insertSelective(Match match);

	Integer updateByPrimaryKeySelective(Match match);
	
	Match selectMatchBySeasonId(String seasonId);

}
