package com.miniworld.jdbcTemplatedao.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.miniworld.jdbcTemplatedao.AdminUserDao;

import javax.annotation.Resource;


@Repository("adminUserDao")
public class AdminUserDaoImpl implements AdminUserDao {

    @Resource
    private JdbcTemplate jt;

    @Resource
    private JdbcTemplate jdbcTemplateParent;
	@Override
	public void createTable(String username, String password, String realname, String keychain, int roleId, long createTime) {
		// TODO Auto-generated method stub
        
        //2、根据id获取对象
		
		//"CREATE TABLE pepole("
        //+ "name varchar(10) not null,"
        //+ "age int(4) not null"
        //+ ")charset=utf8;";
        StringBuffer sb = new StringBuffer("");  
        sb.append("CREATE TABLE `" + "admin_user" + "` (");  
        sb.append(" `admin_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',");          
        sb.append("`admin_loginname` varchar(32) NOT NULL COMMENT '管理员登陆名',");   //MD5加密后的密码长度为32，所以这边定义varchar最大长度需要大于或等于32
        sb.append("`admin_password` varchar(32) NOT NULL COMMENT '登陆密码',");
        sb.append("`admin_real_name` varchar(32) NOT NULL COMMENT '管理员真实姓名',");
        sb.append("`admin_role_id` int(11) NOT NULL COMMENT '角色id',");
        sb.append("`key_chain` varchar(32) COMMENT '秘钥',");
        sb.append("`create_time` bigint(20) NOT NULL COMMENT '创建时间',");
        sb.append("`update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',");
        sb.append(" PRIMARY KEY (`admin_id`)");  
        sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;");  
       
        try {  
            jt.execute(sb.toString());
            jt.update("insert into admin_user(admin_loginname,admin_password,admin_real_name,key_chain,admin_role_id,create_time) values(?,?,?,?,?,?)",new Object[]{username,password,realname,keychain,roleId,createTime});
            
        } catch (Exception e) {  
            e.printStackTrace();  
        }
	}

	@Override
	public String getTokenById(int adminId) {
		// TODO Auto-generated method stub
		 //2、根据id获取对象
	     String token=jt.queryForObject("select token from admin_user where admin_id=?", new Object[]{adminId}, String.class);
	     return token;
	}

	@Override
	public void initialDatabase() {
		// TODO Auto-generated method stub
        String sb = "CREATE DATABASE  IF NOT EXISTS eventSys CHARACTER SET utf8 COLLATE utf8_general_ci";
        jdbcTemplateParent.execute(sb);
	}

	@Override
	public boolean isExistsDatabase() {
		String sb = "SELECT count(SCHEMA_NAME) as SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME='eventSys'";
        Integer result = jdbcTemplateParent.queryForObject(sb, Integer.class);
        //存在数据库，返回1，不存在返回0
        if(result == 0) {
        	return true;
        }else {
        	return false;
        }
        
	}

	
}
