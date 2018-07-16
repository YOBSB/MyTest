package com.miniworld.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.miniworld.BaseTest;
import com.miniworld.common.GlobalManager;
import com.miniworld.common.ReMessage;
import com.miniworld.config.SystemConfig;
import com.miniworld.service.SubmitService;
import com.miniworld.utils.SubmitMapUtil;

public class SubmitWorksControllerTest extends BaseTest{
	
	@Resource
    private SubmitService submitService;

    @Resource
    private GlobalManager globalManager;

    @Resource
    private SystemConfig systemConfig;
    
    @Test
    public void submitTest() {
//    	Long startTime=globalManager.getEventSysStrartTime();
//    	String url=systemConfig.getMapUrl();
//    	String time="1531194869";
//    	Integer uin=291352165;
//    	String newauth="28746f4341eef6d8fdfa02fc7b23f628";
//    	String s2t="1531194992";
//    	ReMessage<ArrayList<Map<String, Object>>> reMessage = SubmitMapUtil.getMapList(url, time, uin, newauth, s2t, startTime);
//    	System.out.println(reMessage.toString());
    }
	
}
