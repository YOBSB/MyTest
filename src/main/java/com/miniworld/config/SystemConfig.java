package com.miniworld.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemConfig {

    @Value("${is.official}")
    private String isOfficial;

    @Value("${worksShow.popular.maxSize}")
    private String popularMaxSize;

    @Value("${worksShow.newest.maxSize}")
    private String newestMaxSize;

    @Value("${worksShow.week.maxSize}")
    private String weekMaxSize;

    @Value("${click.time.interval}")
    private String clickTimeInterval;

    @Value("${url.mini.login.test}")
    private String miniLoginUrlTest;

    @Value("${url.mini.login.official_1}")
    private String miniLoginUrlOfficial_1;


    @Value("${url.mini.login.official_2}")
    private String miniLoginUrlOfficial_2;

    @Value("${url.mini.login.official_3}")
    private String miniLoginUrlOfficial_3;

    @Value("${url.mini.login.official_4}")
    private String miniLoginUrlOfficial_4;

    @Value("${url.title.get.test}")
    private String getTitleUrlTest;

    @Value("${url.title.get.official}")
    private String getTitleUrlOfficial;

    @Value("${url.title.login.test}")
    private String miniLoginTitleUrlTest;

    @Value("${url.title.login.official}")
    private String miniLoginTitleUrlOfficial;

    @Value("${url.email.test}")
    private String emailUrlTest;

    @Value("${url.email.official}")
    private String emailUrlOfficial;


    @Value("${url.photo.test}")
    private String photoUrlTest;

    @Value("${url.photo.official}")
    private String photoUrlOfficial;

    @Value("${url.map.test}")
    private String mapUrlTest;

    @Value("${url.map.official}")
    private String mapUrlOfficial;

    @Value("${url.user.info.test}")
    private String userInfoUrlTest;

    @Value("${url.user.info.official}")
    private String userInfoUrlOfficial;

    public String getUsrInfoUrl(){
          if(isOfficial.equals("0")) return userInfoUrlTest;
          else return userInfoUrlOfficial;
    }

    public String getMapUrl(){
        if(isOfficial.equals("0")) return mapUrlTest;
        else return mapUrlOfficial;
    }

    public String getEmailUrl(){
        if(isOfficial.equals("0")) return emailUrlTest;
        else return emailUrlOfficial;
    }

    public String getIsOfficial(){
        return this.isOfficial;
    }

    public String getPopularMaxSize(){
        return this.popularMaxSize;
    }

    public String getNewestMaxSize(){
        return this.newestMaxSize;
    }

    public String getWeekMaxSize(){
        return this.weekMaxSize;
    }
}
