package com.miniworld.jdbcTemplatedao.impl;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.miniworld.jdbcTemplatedao.HonorTopDao;

@Repository("honorTopDao")
public class HonorTopDaoImpl implements HonorTopDao {

	@Resource
	private JdbcTemplate jt;
	
	@Override
	public void createTableBySeasonId(String seasonId) {
		// TODO Auto-generated method stub
		
        String tablename=seasonId+"_honortop";

        StringBuffer sb = new StringBuffer("");
        String sql="DROP TABLE IF EXISTS `"+tablename+"`;";
        
        sb.append("CREATE TABLE `" + tablename + "` (");  
        
        sb.append("`rank` int(11) NOT NULL,");
        sb.append("`works_id` int(11) NOT NULL COMMENT '作品id',");          
        sb.append("`mini_id` int(11) NOT NULL COMMENT '迷你号',");
        sb.append("`create_time` bigint(20) NOT NULL COMMENT '创建时间',");
        sb.append("`update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',");
        sb.append(" PRIMARY KEY (`rank`)");  
        sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;");  
        try { 
        	jt.execute(sql);
            jt.execute(sb.toString());
            
        } catch (Exception e) {  
            e.printStackTrace();  
        }
	}
	
	

}
	
