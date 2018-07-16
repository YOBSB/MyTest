package com.miniworld.service;


import com.miniworld.entity.Match;

public interface MatchService {

	Integer addInfo(Match match);
	
	Integer updateInfo(Match match);

	Match selectMatchBySeasonId(String seasonId);
}
