package com.miniworld.service.impl;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.miniworld.common.ReMessage;
import com.miniworld.dao.FormReviewMapper;
import com.miniworld.entity.Works;
import com.miniworld.service.FormReviewService;


@Service("formReviewService")
public class FormReviewServiceImpl implements FormReviewService{
	
	@Autowired
	private FormReviewMapper formReviewMapper;
	
	private static List<String> MSGLIST = Arrays.asList("未审核","通过","拒绝","屏蔽");
	
	@Override
	public ReMessage<Map<String, Object>> getAllWorks(String eventId,Integer pageNum,Integer pageSize) {
		List<Works> worksList = new ArrayList<>();
		Map<String, Object> rsMap = new HashMap<String, Object>();
		
		if(eventId.equals("")) {
			PageInfo<Works> p = new PageInfo<Works>(worksList);
	        Integer pages = p.getPages();
	        rsMap.put("worksList", worksList);
	        rsMap.put("pages", pages);
	        return new ReMessage<Map<String, Object>>(2, "当前无开始赛季", rsMap);
		}
		
        PageHelper.startPage(pageNum, pageSize);
		worksList = formReviewMapper.selectAll(eventId);
		PageInfo<Works> p = new PageInfo<Works>(worksList);
        Integer pages = p.getPages();
        rsMap.put("worksList", worksList);
        rsMap.put("pages", pages);
        
        if (worksList == null || worksList.isEmpty()) {
            System.out.println("查询失败！");
            return new ReMessage<Map<String, Object>>(1, "查询失败！", rsMap);
        } else {
            System.out.println("查询成功！");
            return new ReMessage<Map<String, Object>>(0, "查询成功！", rsMap);
        }
        
	}

	@Override
	public ReMessage<Map<String, Object>> getWorksByWorksState(Integer worksState,String eventId,Integer pageNum,Integer pageSize) {
		List<Works> worksList = new ArrayList<>();
        Map<String, Object> rsMap = new HashMap<String, Object>();
		
		if(eventId.equals("")) {
			PageInfo<Works> p = new PageInfo<Works>(worksList);
	        Integer pages = p.getPages();
	        rsMap.put("worksList", worksList);
	        rsMap.put("pages", pages);
	        return new ReMessage<Map<String, Object>>(2, "当前无开始赛季", rsMap);
		}
		
		PageHelper.startPage(pageNum, pageSize);
		worksList = formReviewMapper.selectByWorksState(worksState,eventId);
		PageInfo<Works> p = new PageInfo<Works>(worksList);
        Integer pages = p.getPages();
        rsMap.put("worksList", worksList);
        rsMap.put("pages", pages);
        
        if (worksList == null || worksList.isEmpty()) {
            System.out.println("没有改状态的作品！");
            return new ReMessage<Map<String, Object>>(1, "没有该状态的作品", rsMap);
        } else {
            System.out.println("查询成功！");
            return new ReMessage<Map<String, Object>>(0, "查询成功！", rsMap);
        }
	}

	@Override
	public Works getWorksByWorksId(Integer worksId,String eventId) {
		if(eventId.equals("")) {
			Works works = new Works();
			return works;
		}
		return formReviewMapper.selectByWorksId(worksId,eventId);
	}

	@Override
	public Works getWorksByMiniId(Integer miniId,String eventId) {
		if(eventId.equals("")) {
			Works works = new Works();
			return works;
		}
		return formReviewMapper.selectByMiniId(miniId,eventId);
	}

	@Override
	public Integer updateWorks(Integer miniId, Integer worksState, Long updateTime,String eventId) {
		return formReviewMapper.updateByMiniId(miniId, worksState, updateTime,eventId);
	}

	@Override
	public Integer deleteWorks(Integer miniId, String eventId) {
		return formReviewMapper.deleteWorks(miniId, eventId);
	}

	@Override
	public ReMessage checkState(Integer miniId, Integer worksState, String eventId) {
		
		ReMessage  reMessage = new ReMessage<>();
		Integer baseState = formReviewMapper.checkState(miniId, eventId);
		
		if(worksState > 3 || worksState < 0) {
			reMessage.setCode(1);
			reMessage.setMsg("不存在该状态");
			return reMessage;
		}
		
		if(baseState == null) {
			reMessage.setCode(1);
			reMessage.setMsg("不存在该作品");
			return reMessage;
		}
		
		if(baseState == worksState) {
			reMessage.setCode(3);
			reMessage.setMsg("操作成功");
			return reMessage;
		}
		
		if((worksState == 3 && baseState == 1) || (worksState == 1 && baseState == 3)) {
			reMessage.setCode(0);
			reMessage.setMsg("操作成功");
		}else if(baseState == 0 && (worksState == 1 || worksState == 2)) {
			reMessage.setCode(0);
			reMessage.setMsg("操作成功");
		}else if(baseState == 0 && worksState == 3){
			reMessage.setCode(1);
			reMessage.setMsg("该作品尚未审核");
		}else {
			reMessage.setCode(1);
			reMessage.setMsg("该作品已被其他管理员更改为"+MSGLIST.get(baseState));
		}
		return reMessage;
	}
}

