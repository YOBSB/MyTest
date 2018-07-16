package com.miniworld.mapper.jdbcmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.miniworld.entity.Match;

public class MatchRowMapper implements RowMapper<Match> {

	@Override
	public Match mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Match match=new Match();
		//season.setId(rs.getString("season_id"));
		match.setBannerImage(rs.getString("banner_image"));
		match.setCreateTime(rs.getLong("create_time"));
		match.setGameIntroduce(rs.getString("game_introduce"));
		match.setGameIntroduceImage(rs.getString("game_introduce_image"));
		match.setId(rs.getInt("id"));
		match.setJudgeGuest(rs.getString("judge_guest"));
		match.setMobileBannerImage(rs.getString("mobile_banner_image"));
		match.setMobileGameIntroduce(rs.getString("mobile_game_introduce"));
		match.setMobileGameIntroduceImage(rs.getString("mobile_game_introduce"));
		match.setMobileJudgeGuest(rs.getString("mobile_judge_guest"));
		match.setMobilePartner(rs.getString("mobile_partner"));
		match.setMobileReview(rs.getString("mobile_review"));
		match.setMobileReviewImage(rs.getString("mobile_review_image"));
		match.setMobileRewardImage(rs.getString("mobile_reward_image"));
		match.setMobileRule(rs.getString("mobile_rule"));
		match.setMobileRuleImage(rs.getString("mobile_rule_image"));
		match.setMobileSubmissionIntroduce(rs.getString("mobile_submission_introduce"));
		match.setMobileSubmissionIntroduceImage(rs.getString("mobile_submission_introduce_image"));
		match.setMobileThemeImage(rs.getString("mobile_theme_image"));
		match.setMobileTimeImage(rs.getString("mobile_time_image"));
		match.setMobileWorksIntroduce(rs.getString("mobile_works_introduce"));
		match.setMobileWorksIntroduceImage(rs.getString("mobile_works_introduce_image"));
		match.setPartner(rs.getString("partner"));
		match.setReview(rs.getString("review"));
		match.setReviewImage(rs.getString("review_image"));
		match.setRewardImage(rs.getString("reward_image"));
		match.setRule(rs.getString("rule"));
		match.setRuleImage(rs.getString("rule_image"));
		match.setSeasonId(rs.getString("season_id"));
		match.setSubmissionIntroduce(rs.getString("submission_introduce"));
		match.setSubmissionIntroduceImage(rs.getString("submission_introduce_image"));
		match.setThemeImage(rs.getString("theme_image"));
		match.setTimeImage(rs.getString("time_image"));
		match.setUpdateTime(rs.getLong("update_time"));
		match.setWorksIntroduce(rs.getString("works_introduce"));
		match.setWorksIntroduceImage(rs.getString("works_introduce_image"));
		return match;
	}

}
