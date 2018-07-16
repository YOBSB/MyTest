package com.miniworld.utils;


public class ActiveSeasonUtil {
	
	//判断是否存在活跃赛季，存在活跃赛季返回false,不存在返回true
	public static boolean isActiveSeason(String eventId){
		if(eventId == "" || eventId == null){
			return true;
		}else{
			return false;
		}
	}

}
