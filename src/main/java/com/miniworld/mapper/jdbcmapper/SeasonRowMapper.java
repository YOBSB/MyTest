package com.miniworld.mapper.jdbcmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.miniworld.entity.Season;


public class SeasonRowMapper implements RowMapper<Season> {

	public Season mapRow(ResultSet rs, int rowNum) throws SQLException {

		Season season=new Season();
		
		season.setId(rs.getString("season_id"));
		season.setSeasonName(rs.getString("season_name"));
		season.setSeasonDescription(rs.getString("season_description"));
		season.setSeasonKeyWords(rs.getString("season_key_words"));
		season.setStartTime(rs.getLong("start_time"));
		season.setEndTime(rs.getLong("end_time"));
		season.setCreateTime(rs.getLong("create_time"));
		season.setUpdateTime(rs.getLong("update_time"));
		season.setSeasonLife(rs.getInt("season_life"));
		season.setSubmitStartTime(rs.getLong("submit_start_time"));
		season.setSubmitEndTime(rs.getLong("submit_end_time"));
		return season;
	}
    
}
