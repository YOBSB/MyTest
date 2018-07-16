package com.miniworld;

import com.miniworld.config.RedisConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器 spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring配置文件
@ContextConfiguration({ "classpath:spring/spring-mvc.xml", "classpath:spring/spring-mybatis.xml", "classpath:spring/spring-beans.xml"})
@WebAppConfiguration
public class BaseTest {

}
