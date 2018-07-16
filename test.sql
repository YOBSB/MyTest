/*
Navicat MySQL Data Transfer

Source Server         : 10.0.0.95_3306
Source Server Version : 50722
Source Host           : 10.0.0.95:3306
Source Database       : eventSys

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-06-19 11:46:46
*/

SET FOREIGN_KEY_CHECKS=0;

CREATE DATABASE  IF NOT EXISTS eventSys;

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `admin_loginname` varchar(32) NOT NULL DEFAULT '' COMMENT '管理员登陆名',
  `admin_password` varchar(32) NOT NULL COMMENT '登陆密码',
  `admin_real_name` varchar(32) NOT NULL COMMENT '管理员真实姓名',
  `admin_role_id` int(11) NOT NULL COMMENT '角色id',
  `key_chain` varchar(32) DEFAULT NULL COMMENT '秘钥',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for honortop
-- ----------------------------
DROP TABLE IF EXISTS `honortop`;
CREATE TABLE `honortop` (
  `id` int(11) NOT NULL,
  `ranking_number` int(11) NOT NULL COMMENT '排名序号',
  `mini_id` int(11) NOT NULL COMMENT '迷你号',
  `create_time` bigint(20) NOT NULL,
  `update_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for macthconfig
-- ----------------------------
DROP TABLE IF EXISTS `matchconfig`;
CREATE TABLE `matchconfig` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '赛事id',
  `season_id` varchar(32) NOT NULL COMMENT '赛季id',
  `banner_image` varchar(255) DEFAULT NULL COMMENT '顶部图片url',
  `theme_image` varchar(255) DEFAULT NULL COMMENT '主题图片url',
  `time_image` varchar(255) DEFAULT NULL COMMENT '赛事时间图片url',
  `reward_image` varchar(255) DEFAULT NULL COMMENT '奖励机制图片',
  `rule_image` varchar(255) DEFAULT NULL COMMENT '参赛规则背景图',
  `rule` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '参赛规则',
  `review_image` varchar(255) DEFAULT NULL COMMENT '评审机制背景图',
  `review` varchar(500) DEFAULT NULL COMMENT '评审机制',
  `game_introduce_image` varchar(255) DEFAULT NULL COMMENT '赛事介绍背景图',
  `game_introduce` varchar(500) DEFAULT NULL COMMENT '赛事介绍文案',
  `submission_introduce_image` varchar(255) DEFAULT NULL COMMENT '投稿说明背景图',
  `submission_introduce` varchar(500) DEFAULT NULL COMMENT '投稿说明文案',
  `works_introduce_image` varchar(255) DEFAULT NULL COMMENT '作品说明背景图',
  `works_introduce` varchar(255) DEFAULT NULL COMMENT '作品说明文案',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `partner` varchar(255) DEFAULT NULL,
  `judge_guest` varchar(255) DEFAULT NULL,
  `mobile_banner_image` varchar(255) DEFAULT NULL COMMENT '顶部图片url',
  `mobile_theme_image` varchar(255) DEFAULT NULL COMMENT '主题图片url',
  `mobile_time_image` varchar(255) DEFAULT NULL COMMENT '赛事时间图片url',
  `mobile_reward_image` varchar(255) DEFAULT NULL COMMENT '奖励机制图片',
  `mobile_rule_image` varchar(255) DEFAULT NULL COMMENT '参赛规则背景图',
  `mobile_rule` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '参赛规则',
  `mobile_review_image` varchar(255) DEFAULT NULL COMMENT '评审机制背景图',
  `mobile_review` varchar(500) DEFAULT NULL COMMENT '评审机制',
  `mobile_game_introduce_image` varchar(255) DEFAULT NULL COMMENT '赛事介绍背景图',
  `mobile_game_introduce` varchar(500) DEFAULT NULL COMMENT '赛事介绍文案',
  `mobile_submission_introduce_image` varchar(255) DEFAULT NULL COMMENT '投稿说明背景图',
  `mobile_submission_introduce` varchar(500) DEFAULT NULL COMMENT '投稿说明文案',
  `mobile_works_introduce_image` varchar(255) DEFAULT NULL COMMENT '作品说明背景图',
  `mobile_works_introduce` varchar(255) DEFAULT NULL COMMENT '作品说明文案',
  `mobile_partner` varchar(255) DEFAULT NULL COMMENT '合作伙伴',
  `mobile_judge_guest` varchar(255) DEFAULT NULL COMMENT '评审嘉宾',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for season
-- ----------------------------
DROP TABLE IF EXISTS `season`;
CREATE TABLE `season` (
  `season_id` varchar(32) NOT NULL COMMENT '赛季id',
  `season_name` varchar(32) NOT NULL COMMENT '赛季名称',
  `season_description` varchar(500) NOT NULL COMMENT '赛季描述',
  `season_key_words` varchar(32) NOT NULL COMMENT '赛季关键词',
  `start_time` bigint(20) NOT NULL COMMENT '赛季开始时间',
  `end_time` bigint(20) NOT NULL COMMENT '赛季结束时间',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `season_life` int(11) NOT NULL COMMENT '赛季状态',
  `submit_start_time` bigint(20) NOT NULL COMMENT '投稿开始时间',
  `submit_end_time` bigint(20) NOT NULL COMMENT '投稿结束时间',
  PRIMARY KEY (`season_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for works
-- ----------------------------
DROP TABLE IF EXISTS `works`;
CREATE TABLE `works` (
  `id` int(11) NOT NULL COMMENT '作品id' AUTO_INCREMENT,
  `mini_id` int(16) NOT NULL COMMENT '迷你号',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `qq` varchar(20) DEFAULT NULL COMMENT 'qq',
  `mail` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机',
  `team_mates` varchar(80) DEFAULT NULL COMMENT '团队成员',
  `map_id`	varchar(20)  NOT NULL COMMENT '作品地图id',
  `works_name` varchar(64) DEFAULT NULL COMMENT '作品名称',
  `introduce` varchar(1000) DEFAULT NULL COMMENT '作品介绍',
  `main_image` varchar(100) NOT NULL COMMENT '作品主图片',
  `main_small_image` varchar(100) DEFAULT NULL COMMENT '作品主图片缩略图',
  `image_1` varchar(100) NOT NULL COMMENT '作品图片1',
  `image_small_1` varchar(100) DEFAULT NULL COMMENT '作品图片1缩略图',
  `image_2` varchar(100) DEFAULT NULL COMMENT '作品图片2',
  `image_small_2` varchar(100) DEFAULT NULL COMMENT '作品图片2缩略图',
  `image_3` varchar(100) DEFAULT NULL COMMENT '作品图片3',
  `image_small_3` varchar(100) DEFAULT NULL COMMENT '作品图片3缩略图',
  `state` int(11) NOT NULL COMMENT '作品状态',
  `heat`  int(11) DEFAULT NULL COMMENT '点击数',
  `week_heat` int(11) DEFAULT NULL COMMENT '单周点击数',
  `week_heat_time` int(11) DEFAULT NULL COMMENT '单周点击数的周数',
  `submission_time` bigint(20) NOT NULL COMMENT '投稿时间',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1000;
