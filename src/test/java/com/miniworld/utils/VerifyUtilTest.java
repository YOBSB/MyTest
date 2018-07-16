package com.miniworld.utils;

import com.miniworld.BaseTest;
import com.miniworld.config.SystemConfig;
import com.miniworld.exception.ParamException;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;

public class VerifyUtilTest extends BaseTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SystemConfig systemConfig;

    @Resource
    private RestTemplate restTemplate;

    @Test
    public void testVerifyUid() {
        boolean isOK=false;

        logger.info("正确的参数");
        try {
            isOK = VerifyUtil.VerifyUid(restTemplate,systemConfig.getUsrInfoUrl(),204798547, "54166ba0288f7317b71b09bae0db085f_1530519588");
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        Assert.assertEquals("验证出错",true,isOK);

        logger.info("null参数");
        try {
            isOK = VerifyUtil.VerifyUid(restTemplate,systemConfig.getUsrInfoUrl(),null, null);
        } catch (IOException  | ParamException e) {
            logger.info(e.getMessage());
        }

        try {
            isOK = VerifyUtil.VerifyUid(restTemplate,systemConfig.getUsrInfoUrl(),1213213, null);
        } catch (IOException  | ParamException e) {
            logger.info(e.getMessage());
        }

        try {
            isOK = VerifyUtil.VerifyUid(restTemplate,systemConfig.getUsrInfoUrl(),null, "12313456");
        } catch (IOException  | ParamException e) {
            logger.info(e.getMessage());
        }

        logger.info("sign没有_");
        try {
            isOK = VerifyUtil.VerifyUid(restTemplate,systemConfig.getUsrInfoUrl(),204615380, "88ff53f479ce097cd7bde8db60a1d2611529658788");
        } catch (IOException  | ParamException e) {
            logger.info(e.getMessage());
        }

        logger.info("sign没有_");
        try {
            isOK = VerifyUtil.VerifyUid(restTemplate,systemConfig.getUsrInfoUrl(),204615380, "88ff53f479ce097cd7bde8db60a1d2611529658788");
        } catch (IOException  | ParamException e) {
            logger.info(e.getMessage());
        }
        Assert.assertEquals("验证出错",false,isOK);


        logger.info("sign 多个_");
        try {
            isOK = VerifyUtil.VerifyUid(restTemplate,systemConfig.getUsrInfoUrl(),204615380, "88ff53f479ce097c_d7bd____________e8d_b60a1d261_152965878888");
        } catch (IOException  | ParamException e) {
            logger.info(e.getMessage());
        }
        Assert.assertEquals("验证出错",false,isOK);

    }

    @Test
    public void testGetMD5Crypt() {
        String result;

        try {
            //空字符串
            result = VerifyUtil.CryptMD5("");
            Assert.assertEquals("空字符串出错","",result);
        }catch (IllegalArgumentException e){
            logger.info("空字符串："+e.getMessage());
        }

        try {
            //null值
            result = VerifyUtil.CryptMD5(null);
            Assert.assertEquals("null值出错","",result);
        }catch (IllegalArgumentException e){
            logger.info("null值："+e.getMessage());
        }

        result = VerifyUtil.CryptMD5("1529412742");
        Assert.assertEquals("1529412742出错","1a22663dbff4243e53bca10fa0581399",result);

        result = VerifyUtil.CryptMD5("距离圣诞节放假时代峰峻");
        Assert.assertEquals("距离圣诞节放假时代峰峻","835492699ec8c20518b609a7bbfb5e8d",result);


        result = VerifyUtil.CryptMD5("00000000000000000000");
        Assert.assertEquals("00000000000000000000","cc545187d0745132de1e9941db0ef6ce",result);

    }

    @Test
    public void test11(){

    }
}
