package com.miniworld.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.miniworld.common.ReMessage;
import com.miniworld.controller.WorksController;
import com.miniworld.exception.ParamException;
import org.apache.commons.codec.digest.Md5Crypt;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.imageio.IIOException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class SubmitMapUtil {
    
    private static Logger logger= LoggerFactory.getLogger(WorksController.class);

   /**
    * 
    * @param url
    * @param uin
    * @param sign
    * @param time
    * @param startTime
    * @param worksMapId
    * @return 返回是否验证成功
    * @throws IOException
    * @throws ParamException
    * @Description ： 根据uin和sign获取地图列表，判断上传的地图ID是否在内
    */
    public static boolean VerifyMap(RestTemplate restTemplate,String url, Integer uin, String sign,String time,Long startTime,String worksMapId) throws IOException, ParamException {
        if (uin == null || uin == 0 || sign == null || sign.isEmpty() || 
        		time == null || time.isEmpty() || startTime == null || startTime == 0 ||
        		worksMapId == null || worksMapId.isEmpty()) throw new ParamException("param is null");
        String[] signParts = sign.split("_", 2);//使用limit，最多分割成2个字符串
        if (signParts.length < 2) return false;
        String s2 = signParts[0];
        String s2t = signParts[1];

        String auth = VerifyUtil.CryptMD5(time + s2 + uin);
        
    	ReMessage<ArrayList<Map<String, Object>>> reMessage = SubmitMapUtil.getMapList(restTemplate,url, time, uin, auth, s2t, startTime);
        if(reMessage.getData() == null ) {
        	return false;
        }
    	ArrayList<Map<String, Object>> list = reMessage.getData();
        for(Map<String, Object> map:list) {
        	if(worksMapId.equals(map.get("mapId"))){
        		return true;
        	}
        }
        return false;
    }


    /**
     * 
     * @param url
     * @param uin
     * @param auth
     * @param s2t
     * @param time
     * @param startTime
     * @param worksMapId
     * @return 返回验证结果
     * @throws IOException
     * @throws ParamException
     * @Description ： 根据uin和auth、s2t获取地图列表，判断上传的地图ID是否在内
     */
    public static boolean VerifyMap(RestTemplate restTemplate,String url,Integer uin, String auth,String s2t,String time,Long startTime,String worksMapId) throws IOException, ParamException {
    	if (uin == null || uin == 0 || auth == null || auth.isEmpty() || s2t == null || s2t.isEmpty() ||
        		time == null || time.isEmpty() || startTime == null || startTime == 0 ||
        		worksMapId == null || worksMapId.isEmpty()) throw new ParamException("param is null");
    	ReMessage<ArrayList<Map<String, Object>>> reMessage = SubmitMapUtil.getMapList(restTemplate,url, time, uin, auth, s2t, startTime);
    	if(reMessage.getData() == null ) {
        	return false;
        }
    	ArrayList<Map<String, Object>> list = reMessage.getData();
        for(Map<String, Object> map:list) {
        	if(worksMapId.equals(map.get("mapId"))){
        		return true;
        	}
        }
        return false;
    }
    
    /**
     * 
     * @param url
     * @param time
     * @param uin
     * @param auth
     * @param s2t
     * @param startTime
     * @return	返回可用的地图列表
     * 
     * @Description ： 根据输入的参数通过接口获取地图列表，并返回List
     */
    public static ReMessage<ArrayList<Map<String, Object>>> getMapList(RestTemplate restTemplate,String url, String time,Integer uin,String auth,String s2t,Long startTime){
    	
	    	String param="?act="+"search_user_maps2"+
	    				"&json="+1+
	    				"&time="+time+
	    				"&uin="+uin+
	    				"&op_uin="+uin+
	    				"&auth="+auth+
	    				"&min_t="+startTime/1000+
	    				//"&min_t="+1531152000+
	    				"&s2t="+s2t+
	    				"&smp="+1;
    	ObjectMapper objectMapper = new ObjectMapper();
    	String  mapResult = restTemplate.getForObject(url+param,String.class);
    	//HashMap<Integer,HashMap<String, Object>> mapGroup = new HashMap<Integer,HashMap<String, Object>>();
    	ArrayList<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>();
    	
    	try {
			Map<String, Map<String, Map<String, Object>>> maps = objectMapper.readValue(mapResult, Map.class);
			
			if(maps.isEmpty()) {
				return new ReMessage<>(1,"没有可用作品");
			}
			
			Set<String> mapIdSet =maps.get("map_info_list").keySet();
			
			for(String id : mapIdSet) {
				HashMap<String,Object> mapIdAndName =new HashMap<String, Object>();
				mapIdAndName.put("mapId", id);
                mapIdAndName.put("mapName",maps.get("map_info_list").get(id).get("name"));
                mapList.add(mapIdAndName);
			}
			
		} catch (JsonParseException e) {
			logger.error("objectMapper.readValue JsonParseException error :%s\n",e.getMessage());
			return new ReMessage(-1,mapResult);
		} catch (JsonMappingException e) {
			logger.error("objectMapper.readValue JsonMappingException error :%s\n",e.getMessage());
			return new ReMessage(-1,mapResult);
		} catch (IOException e) {
			logger.error("objectMapper.readValue IOException error :%s\n",e.getMessage());
			return new ReMessage(-1,mapResult);
		} catch (NullPointerException e) {
			logger.error("maps.get(\"map_info_list\").keySet() NullPointerException error :%s\n",e.getMessage());
			return new ReMessage<>(-1,mapResult);
		}
    	
    	System.err.println(mapResult);
        return new ReMessage<ArrayList<Map<String, Object>>>(0,"查询成功",mapList);
    	
    }
}
