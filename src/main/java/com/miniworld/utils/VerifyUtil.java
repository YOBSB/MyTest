package com.miniworld.utils;

import com.miniworld.exception.ParamException;
import org.apache.commons.codec.digest.Md5Crypt;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.imageio.IIOException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class VerifyUtil {

    private final static String URL = "http://120.24.64.132:8087/miniw/profile?act=getProfile2&json=1&fast=110";
    private final static String ACT = "act";
    private final static String JSON = "json";
    private final static String TIME = "time";
    private final static String UIN = "uin";
    private final static String OP_UIN = "op_uin";
    private final static String AUTH = "auth";
    private final static String FAST = "fast";
    private final static String S2T = "s2t";

    /**
     * @param uid
     * @param sign
     * @return 返回是否验证成功
     * @throws IOException,ParamException
     * @Description : 根据uid和sign以及本地内置的数值来计算auth，并向另一个服务器发送验证
     */
    public static boolean VerifyUid(RestTemplate restTemplate,String url,Integer uid, String sign) throws IOException, ParamException {
        if (uid == null || uid == 0 || sign == null || sign.isEmpty()) throw new ParamException("param is null");
        String[] strarray = sign.split("_", 2);//使用limit，最多分割成2个字符串
        if (strarray.length < 2) return false;
        String s2 = strarray[0];
        String s2t = strarray[1];
        long now = System.currentTimeMillis();
        String auth = CryptMD5(now + s2 + uid);
        return VerifyUid(restTemplate,url,uid,auth,s2t,now);
    }


    /**
     * @param uid
     * @param auth
     * @param s2t
     * @return 返回是否验证成功
     * @throws IOException,ParamException
     * @Description : 根据uid，auth，s2t 向另一个服务器发送验证
     */
    public static boolean VerifyUid(RestTemplate restTemplate,String url,Integer uid, String auth,String s2t,long now) throws IOException, ParamException {
        if (uid == null || s2t==null || s2t.isEmpty() ||auth == null || auth.isEmpty()) throw new ParamException("uid,auth or s2t is null");

        String finalUrl = url + "?act=getProfile2&json=1&fast=110"
                +"&uin=" + uid
                + "&op_uin=" + uid
                + "&auth=" + auth
                + "&time=" + now
                + "&s2t=" + s2t;

        String result = restTemplate.getForObject(finalUrl, String.class);
        if (result == null) return false;

        ObjectMapper mapper = new ObjectMapper(); //转换器
        Map m = mapper.readValue(result, HashMap.class); //json字符串转换成map
        if (m.get("ret") == null) return false;
        if (!m.get("ret").toString().equals("0")) return false;
        return true;
    }

    /**
     * Encodes a string 2 MD5
     *
     * @param str String to encode
     * @return Encoded String
     * @throws IllegalArgumentException
     */
    public static String CryptMD5(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("非法字符串");
        }
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }
}
