package com.miniworld.jdbcTemplatedao;

import com.miniworld.entity.Match;

public interface MatchDao {
	
	
	void insert();

	void createTable();

	Match queryMatchBySeasonId(String seasonId);
}
