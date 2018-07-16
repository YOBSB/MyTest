package com.miniworld.service;

import java.util.Map;

import com.miniworld.common.ReMessage;

public interface WorksDataService {
	ReMessage<Map<String, Object>> getAllWorksData(Integer pageNum, Integer pageSize,String eventId);
}