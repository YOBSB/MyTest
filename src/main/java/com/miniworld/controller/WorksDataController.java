package com.miniworld.controller;

import com.miniworld.common.GlobalManager;
import com.miniworld.common.ReMessage;
import com.miniworld.service.WorksDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import javax.annotation.Resource;

@Controller
@RequestMapping("/worksData")
public class WorksDataController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private WorksDataService worksDataService;
	
	@Resource 
	private GlobalManager globalManager;

	/**
	 * 进入作品数据页面
	 * @param model
	 * @return worksdata.jsp
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView getworksdata(Model model) {
		model.addAttribute("sId", globalManager.getEventId());
		model.addAttribute("sName", globalManager.getEventSysName());
		return new ModelAndView("worksdata");
	}

	/**
	 * 查找已经通过的作品浏览数据
	 */
	@RequestMapping(value = "/getWorksData", method = RequestMethod.GET)
	@ResponseBody
	public ReMessage<Map<String, Object>> getAllWorksData(
			@RequestParam(value = "eventId" ,required = false)String eventId,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {
		logger.info("WorksData");
		if(eventId == null || eventId.isEmpty()) {
			eventId = globalManager.getEventId();
		}
		ReMessage<Map<String, Object>> reMessage = worksDataService.getAllWorksData(pageNum, pageSize,eventId);
		return reMessage;

	}
}
