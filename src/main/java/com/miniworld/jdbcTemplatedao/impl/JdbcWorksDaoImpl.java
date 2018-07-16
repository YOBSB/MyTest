package com.miniworld.jdbcTemplatedao.impl;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.miniworld.jdbcTemplatedao.JdbcWorksDao;


@Repository("jdbcWorksDao")
public class JdbcWorksDaoImpl implements JdbcWorksDao {

	@Resource
	private JdbcTemplate jt;
	@Override
	public void createTableBySeasonId(String seasonId) {
		// TODO Auto-generated method stub



        String tablename=seasonId+"_works";
        String sql="DROP TABLE IF EXISTS `"+tablename+"`;";
        StringBuffer sb = new StringBuffer(""); 
        
        sb.append("CREATE TABLE `" + tablename + "` (");  
        sb.append("`id` int(11) NOT NULL COMMENT '作品id' AUTO_INCREMENT,");
        sb.append("`mini_id` int(16) NOT NULL COMMENT '迷你号',");          
        sb.append("`name` varchar(20) DEFAULT NULL COMMENT '姓名',");
        sb.append("`qq` varchar(20) DEFAULT NULL COMMENT 'qq',");
        sb.append("`mail` varchar(20) DEFAULT NULL COMMENT '邮箱',");
        sb.append("`phone` varchar(20) DEFAULT NULL COMMENT '手机',");
        sb.append("`team_mates` varchar(80) DEFAULT NULL COMMENT '团队成员',");
        sb.append("`map_id`	varchar(20)  NOT NULL COMMENT '作品地图id',");
        sb.append("`works_name` varchar(64) DEFAULT NULL COMMENT '作品名称',");
        sb.append("`introduce` varchar(1000) DEFAULT NULL COMMENT '作品介绍',");
        sb.append("`main_image` varchar(1000) NOT NULL COMMENT '作品主图片',");
        sb.append("`main_small_image` varchar(1000) DEFAULT NULL COMMENT '作品主图片缩略图',");
        sb.append("`image_1` varchar(1000) NOT NULL COMMENT '作品图片1',");
        sb.append("`image_small_1` varchar(1000) DEFAULT NULL COMMENT '作品图片1缩略图',");
        sb.append("`image_2` varchar(1000) DEFAULT NULL COMMENT '作品图片2',");
        sb.append("`image_small_2` varchar(1000) DEFAULT NULL COMMENT '作品图片2缩略图',");
        sb.append("`image_3` varchar(1000) DEFAULT NULL COMMENT '作品图片3',");
        sb.append("`image_small_3` varchar(1000) DEFAULT NULL COMMENT '作品图片3缩略图',");
        sb.append("`state` int(11) NOT NULL COMMENT '作品状态',");
        sb.append("`heat`  int(11) DEFAULT NULL COMMENT '点击数',");
        sb.append("`week_heat` int(11) DEFAULT NULL COMMENT '单周点击数',");
        sb.append("`week_heat_time` int(11) DEFAULT NULL COMMENT '单周点击数的周数',");
        sb.append("`submission_time` bigint(20) NOT NULL COMMENT '投稿时间',");
        sb.append("`create_time` bigint(20) NOT NULL COMMENT '创建时间',");
        sb.append("`update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',");
        sb.append(" PRIMARY KEY (`id`)");  
        sb.append(") ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;");  
       
        try { 
        	jt.execute(sql);
            jt.execute(sb.toString());
            
        } catch (Exception e) {  
            e.printStackTrace();  
        }
	}

}
