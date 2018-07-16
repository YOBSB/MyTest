package com.miniworld.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.miniworld.common.GlobalManager;
import com.miniworld.common.ReMessage;
import com.miniworld.dao.FormReviewMapper;
import com.miniworld.dao.SeasonMapper;
import com.miniworld.entity.Works;
import com.miniworld.service.WorksDataService;



@Service("worksDataService")
public class WorksDataServiceImpl implements WorksDataService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FormReviewMapper formReviewMapper;
	@Autowired
	private SeasonMapper seasonMapper;
	
	@Resource
	private GlobalManager globalManager;

	@Override
	public ReMessage<Map<String, Object>> getAllWorksData(Integer pageNum, Integer pageSize,String eventId) {
		ReMessage<Map<String, Object>> reMessage = new ReMessage<Map<String, Object>>();
		Map<String, Object> rsMap = new HashMap<String, Object>();
		List<Works> worksData = new ArrayList<>();
		
		if(eventId.equals("")) {
			eventId = seasonMapper.selectLastestSeason();
			if(seasonMapper.isExistTable(eventId)>0) {
				try {
					PageHelper.startPage(pageNum, pageSize);
					worksData = formReviewMapper.selectWorksData(eventId);
				}catch (NullPointerException e) {
					logger.error("数据库内不存在该表  error:%s",e.getMessage());
				}
			}else {
				logger.error("数据库内不存在该表  error");
				PageInfo<Works> p = new PageInfo<Works>(worksData);
				Integer pages = p.getPages();
				rsMap.put("worksList", worksData);
				rsMap.put("pages", pages);
				reMessage.setCode(2);
				reMessage.setMsg("当前无作品表单");
				reMessage.setData(rsMap);
				return reMessage;
			}
			PageInfo<Works> p = new PageInfo<Works>(worksData);
			Integer pages = p.getPages();
			rsMap.put("worksList", worksData);
			rsMap.put("pages", pages);
			reMessage.setCode(0);
			reMessage.setMsg("当前无开始赛季，显示最近赛季作品信息");
			reMessage.setData(rsMap);
			return reMessage;
		}
		
		try {
			PageHelper.startPage(pageNum, pageSize);
			worksData = formReviewMapper.selectWorksData(eventId);
			PageInfo<Works> p = new PageInfo<Works>(worksData);
			Integer pages = p.getPages();
			rsMap.put("worksList", worksData);
			rsMap.put("pages", pages);
		}catch (Exception e) {
			logger.error("formReviewMapper.selectWorksData error:%s",e.getMessage());
		}
		
		if(worksData == null || worksData.isEmpty()) {
			reMessage.setCode(1);
			reMessage.setMsg("当前无已通过作品");
			reMessage.setData(rsMap);
			return reMessage;
		}else {
			reMessage.setCode(0);
			reMessage.setMsg("查询成功");
			reMessage.setData(rsMap);
			return reMessage;
		}
	}
	
	
}

