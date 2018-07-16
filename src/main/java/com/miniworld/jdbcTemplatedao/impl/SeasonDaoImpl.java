package com.miniworld.jdbcTemplatedao.impl;


import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.miniworld.entity.Season;
import com.miniworld.jdbcTemplatedao.SeasonDao;
import com.miniworld.mapper.jdbcmapper.SeasonRowMapper;

import javax.annotation.Resource;


@Repository("seasonDao")
public class SeasonDaoImpl implements SeasonDao {

	@Resource
	private JdbcTemplate jt;

	@Override
	public void createSerson(String seasonId, String seasonName, String description, String keyWords, long startTime,
			long endTime, long createTime,long updateTime,int seasonLife,Long submitStartTime, Long submitEndTime) {
		// TODO Auto-generated method stub
        String sql="INSERT INTO eventSys.season(season_id,season_name,season_description,season_key_words,start_time,end_time,create_time,update_time,season_life,submit_start_time,submit_end_time) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        jt.update(sql,new Object[]{seasonId,seasonName,description,keyWords,startTime,endTime,createTime,updateTime,seasonLife,submitStartTime,submitEndTime});
        
	}

	@Override
	public List querySeason() {
		// TODO Auto-generated method stub
        List<Season> list = jt.query("select * from season", new SeasonRowMapper());
		return list;
	}

	@Override
	public void insertSeason(Season season) {
		// TODO Auto-generated method stub
        jt.update("insert into eventSys.season values(?,?,?,?,?,?,?,?,?,?)",new Object[]{season.getId(),season.getSeasonName(),season.getSeasonDescription(),
        		season.getSeasonKeyWords(),season.getStartTime(),season.getEndTime(),season.getCreateTime(),season.getUpdateTime(),season.getSubmitStartTime(),season.getSubmitEndTime()});
	}

	@Override
	public void createTable() {
		// TODO Auto-generated method stub
        StringBuffer sb = new StringBuffer("");  
        sb.append("CREATE TABLE `"+"season"+"` (");
        sb.append("`season_id` varchar(32) NOT NULL COMMENT '赛季id',");          
        sb.append("`season_name` varchar(32) NOT NULL COMMENT '赛季名称',");
        sb.append("`season_description` varchar(500) NOT NULL COMMENT '赛季描述',");
        sb.append("`season_key_words` varchar(32) NOT NULL COMMENT '赛季关键词',");
        sb.append("`start_time` bigint(20) NOT NULL COMMENT '赛季开始时间',");
        sb.append("`end_time` bigint(20) NOT NULL COMMENT '赛季结束时间',");
        sb.append("`create_time` bigint(20) NOT NULL COMMENT '创建时间',");
        sb.append("`update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',");
        sb.append("`season_life` int(11) NOT NULL COMMENT '赛季状态,活跃状态为1,结束状态为0',");
        sb.append("`submit_start_time` bigint(20) NOT NULL COMMENT '投稿开始时间',");
        sb.append("`submit_end_time` bigint(20) NOT NULL COMMENT '投稿结束时间',");
        sb.append(" PRIMARY KEY (`season_id`)");  
        sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;");  
       
        try {  
            jt.execute(sb.toString());   
        } catch (Exception e) {  
            e.printStackTrace();  
        }
	}

	@Override
	public Season querySeasonById(String seasonId) {
		// TODO Auto-generated method stub

		String sql="select * from season where season_id = ?";
		Season season=null;
        try{
        	season=jt.queryForObject(sql, new SeasonRowMapper(),seasonId );
        }catch(EmptyResultDataAccessException e){
        	return null;
        }
        
        
		return season;
		
	}

	@Override
	public String querySeasonLife() {

        List<Season> list = jt.query("select * from season where season_life= 1", new SeasonRowMapper());

        if(list.size()!=0){
        	return list.get(0).getId();
        }
		return null;   //返回空值表示当前没有赛季生存
	}

	
	/**
	 * 
	 */
	@Override
	public void updateSeason(Season season,String originalSeasonId) {
		// TODO Auto-generated method stub

        String sql="update season set season_id =?,season_name =?,season_description =?,season_key_words =?,start_time =?,end_time =?,update_time =?,submit_start_time=?,submit_end_time=? where season_id =?";
        Object args[]={season.getId(),season.getSeasonName(),season.getSeasonDescription(),season.getSeasonKeyWords(),season.getStartTime(),season.getEndTime(),season.getUpdateTime(),season.getSubmitStartTime(),season.getSubmitEndTime(),originalSeasonId};
        jt.update(sql,args);
        
	}
	

	@Override
	public void deleteSeasonById(String seasonId) {
		// TODO Auto-generated method stub
        String sql="delete from season where season_id=?";
        Object args[]={seasonId};
        jt.update(sql, args);
        
	}

	@Override
	public void stopSeasonById(String seasonId) {
		// TODO Auto-generated method stub
        String sql="update season set season_life =? where season_id =?";
        Object args[]={0,seasonId};
        jt.update(sql, args);
	}

	@Override
	public void startSeasonById(String seasonId) {
		// TODO Auto-generated method stub
		String sql="update season set season_life =? where season_id =?";
        Object args[]={1,seasonId};
        jt.update(sql, args);
	}
}


