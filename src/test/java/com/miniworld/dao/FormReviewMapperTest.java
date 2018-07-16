package com.miniworld.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.miniworld.BaseTest;
import com.miniworld.common.GlobalManager;

public class FormReviewMapperTest extends BaseTest{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    
	@Resource
    private GlobalManager globalManager;
	
	@Resource
	private FormReviewMapper formReviewMapper;
	
	@Test
	public void checkStateTest() {
		Integer count = formReviewMapper.checkState(1231, "20180713");
		System.out.println(count);
		
	}
	    

}
