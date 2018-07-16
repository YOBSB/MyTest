package com.miniworld.jdbcTemplatedao.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.miniworld.entity.Match;
import com.miniworld.entity.Season;
import com.miniworld.jdbcTemplatedao.MatchDao;
import com.miniworld.mapper.jdbcmapper.MatchRowMapper;
import com.miniworld.mapper.jdbcmapper.SeasonRowMapper;

import javax.annotation.Resource;

@Repository("matchDao")
public class MatchDaoImpl implements MatchDao {
    @Resource
    private JdbcTemplate jt;

    @Override
    public void createTable() {
        // TODO Auto-generated method stub
        StringBuffer sb = new StringBuffer("");
        sb.append("CREATE TABLE `" + "matchconfig" + "` (");
        sb.append("`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '赛事id',");
        sb.append("`season_id` varchar(32) NOT NULL COMMENT '赛季id',");
        sb.append("`banner_image` varchar(500) DEFAULT NULL COMMENT 'PC端顶部图片url',");
        sb.append("`theme_image` varchar(500) DEFAULT NULL COMMENT 'PC端主题图片url',");
        sb.append("`time_image` varchar(500) DEFAULT NULL COMMENT 'PC端赛事时间图片url',");
        sb.append("`reward_image` varchar(500) DEFAULT NULL COMMENT 'PC端奖励机制图片',");
        sb.append("`rule_image` varchar(500) DEFAULT NULL COMMENT 'PC端赛事规则背景图',");
        sb.append("`rule` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'PC端赛事规则',");
        sb.append("`review_image` varchar(500) DEFAULT NULL COMMENT 'PC端评审机制背景图',");
        sb.append("`review` varchar(1000) DEFAULT NULL COMMENT 'PC端评审机制',");
        sb.append("`game_introduce_image` varchar(500) DEFAULT NULL COMMENT 'PC端赛事介绍背景图',");
        sb.append("`game_introduce` varchar(1000) DEFAULT NULL COMMENT 'PC端赛事介绍文案',");
        sb.append("`submission_introduce_image` varchar(500) DEFAULT NULL COMMENT 'PC端投稿说明背景图',");
        sb.append("`submission_introduce` varchar(1000) DEFAULT NULL COMMENT 'PC端投稿说明文案',");
        sb.append("`works_introduce_image` varchar(500) DEFAULT NULL COMMENT 'PC端作品说明背景图',");
        sb.append("`works_introduce` varchar(1000) DEFAULT NULL COMMENT 'PC端作品说明文案',");
        sb.append("`create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',");
        sb.append("`update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',");
        sb.append("`partner` varchar(500) DEFAULT NULL COMMENT 'PC端合作伙伴',");
        sb.append("`judge_guest` varchar(500) DEFAULT NULL COMMENT 'PC端评审嘉宾',");
        sb.append("`mobile_banner_image` varchar(500) DEFAULT NULL COMMENT '移动端顶部图片url',");
        sb.append("`mobile_theme_image` varchar(500) DEFAULT NULL COMMENT '移动端主题图片url',");
        sb.append("`mobile_time_image` varchar(500) DEFAULT NULL COMMENT '移动端赛事时间图片url',");
        sb.append("`mobile_reward_image` varchar(500) DEFAULT NULL COMMENT '移动端奖励机制图片',");
        sb.append("`mobile_rule_image` varchar(500) DEFAULT NULL COMMENT '移动端参赛规则背景图',");
        sb.append("`mobile_rule` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '移动端参赛规则',");
        sb.append("`mobile_review_image` varchar(500) DEFAULT NULL COMMENT '移动端评审机制背景图',");
        sb.append("`mobile_review` varchar(1000) DEFAULT NULL COMMENT '移动端评审机制',");
        sb.append("`mobile_game_introduce_image` varchar(500) DEFAULT NULL COMMENT '移动端赛事介绍背景图',");
        sb.append("`mobile_game_introduce` varchar(1000) DEFAULT NULL COMMENT '移动端赛事介绍文案',");
        sb.append("`mobile_submission_introduce_image` varchar(500) DEFAULT NULL COMMENT '移动端投稿说明背景图',");
        sb.append("`mobile_submission_introduce` varchar(1000) DEFAULT NULL COMMENT '移动端投稿说明文案',");
        sb.append("`mobile_works_introduce_image` varchar(500) DEFAULT NULL COMMENT '移动端作品说明背景图',");
        sb.append("`mobile_works_introduce` varchar(1000) DEFAULT NULL COMMENT '移动端作品说明文案',");
        sb.append("`mobile_partner` varchar(500) DEFAULT NULL COMMENT '移动端合作伙伴',");
        sb.append("`mobile_judge_guest` varchar(500) DEFAULT NULL COMMENT '移动端评审嘉宾',");
        sb.append(" PRIMARY KEY (`id`)");
        sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;");

        try {
            jt.execute(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void insert() {
        // TODO Auto-generated method stub

    }

    @Override
    public Match queryMatchBySeasonId(String seasonId) {
        // TODO Auto-generated method stub

        String sql = "select * from matchconfig where season_id = ?";
        Match match = null;
       
        try {
            match = jt.queryForObject(sql, new MatchRowMapper(), seasonId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }


        return match;
    }

}
