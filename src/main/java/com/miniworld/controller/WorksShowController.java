package com.miniworld.controller;

import com.miniworld.InitData;
import com.miniworld.common.GlobalManager;
import com.miniworld.common.ReMessage;
import com.miniworld.config.SystemConfig;
import com.miniworld.entity.Works;
import com.miniworld.exception.ParamException;
import com.miniworld.service.WorksShowService;
import com.miniworld.utils.VerifyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/worksShow")
public class WorksShowController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static Integer DEFAULT_NUM = 8;
    private final static Integer DEFAULT_PAGE = 1;
    private final static String IS_VERIFY = "isVerify";
    private final static String UID = "uid";

    @Resource
    private WorksShowService worksShowService;

    @Resource
    private SystemConfig systemConfig;

    @Resource
    private RestTemplate restTemplate;

    /**
     * @param num  :请求的作品数量，默认8个
     * @param page :请求的作品页数，默认第一页
     * @param type :请求类型
     *             1.最新的作品列表
     *             2.人气最高的作品列表
     *             3.周人气最高作品列表
     * @return :返回是否操作成功
     * code:0 操作成功
     * code:1 作品列表类型未正确声明
     * code:-1 服务器错误
     * @MethodName : GetWorksList
     * @Description : 获取作品集
     */
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    @ResponseBody
    public ReMessage GetWorksList(@RequestParam(value = "num", required = false) Integer num,
                                  @RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "type", required = false) Integer type) {
        logger.info("GetList,num={},page={},type={}", num, page, type);

        if (type == null) {
            return new ReMessage(1, "请声明要的获取作品列表的类型");
        }

        if (num == null || num <= 0) {
            num = DEFAULT_NUM;
        }

        if (num > 30) {
            num = 30;
        }

        if (page == null || page <= 0) {
            page = DEFAULT_PAGE;
        }

        try {
            ReMessage reMessage;
            switch (type) {
                case 1:
                    reMessage = worksShowService.getNewestList(num, page);
                    break;
                case 2:
                    reMessage = worksShowService.getPopularList(num, page);
                    break;
                case 3:
                    reMessage = worksShowService.getWeekList(num, page);
                    break;
                default:
                    logger.info("数据不完整");
                    return new ReMessage(1, "请声明要的获取作品列表的类型");
            }

            return reMessage;
        } catch (RuntimeException e) {
            logger.error("获取列表错误：,num={},page={},type={},error：{}", num, page, type, e.getMessage());
            return new ReMessage(-1, "系统错误");
        }

    }


    /**
     * @param num :请求的作品数量，默认8个
     * @return :返回是否操作成功
     * code:0 操作成功
     * code:1 wid或者token为空
     * code:-1 服务器错误
     * @MethodName : GetWorksDefault
     * @Description : 获取默认的作品集
     */
    @RequestMapping(value = "/getDef", method = RequestMethod.GET)
    @ResponseBody
    public ReMessage GetWorksDefault(@RequestParam(value = "num", required = false) Integer num) {
        logger.info("GetWorksDefault,num={}", num);

        if (num == null || num <= 0) {
            num = DEFAULT_NUM;
        }
        if (num > 30) {
            num = 30;
        }

        ReMessage reMessage;
        try {
            reMessage = worksShowService.getDefaultList(num);
        } catch (ParamException e) {
            logger.error("getWorksDefault error:{}\n", e.getMessage());
            return new ReMessage(-1, e.getMessage());
        }

        return reMessage;
    }

    /**
     * @param uid :迷你号
     * @return :返回是否操作成功
     * code:0 操作成功
     * code:1 wid或者token为空
     * code:-1 服务器错误
     * @MethodName : GetWroksByUid
     * @Description : 获取默认的作品集
     */
    @RequestMapping(value = "/getByUid", method = RequestMethod.GET)
    @ResponseBody
    public ReMessage GetWroksByUid(@RequestParam(value = "uid", required = false) Integer uid) {
        logger.info("GetWorksDefault");

        if (uid == null || uid == 0) {
            return new ReMessage(1, "请带上迷你号");
        }

        try {
            Works works = worksShowService.getByUid(uid);
            if(works.getWorksState()!=1){
                return new ReMessage(2,"作品不是已审核状态");
            }
            ArrayList<Works> list = new ArrayList<>(1);
            list.add(works);
            Map<String, Object> map = new HashMap<>();
            map.put("works", list);
            return new ReMessage(0, "操作成功", map);
        } catch (RuntimeException e) {
            logger.error("服务器出错：{}", e.getMessage());
            return new ReMessage(-1, "服务器错误");
        }
    }


    /**
     * @param wid     :客户端传来的数据，作品id
     * @param uid     :客户端传来的数据，点击该作品用户的id
     * @param sign    :验证迷你号的参数
     * @param auth    :验证迷你号的参数
     * @param s2t     :验证迷你号的参数
     * @param session :该连接的session
     * @return :返回是否操作成功
     * code:0 操作成功
     * code:1 参数缺失
     * code:-1 服务器错误
     * @MethodName : IncreaseHeat
     * @Description : 作品点击量增加
     */
    @RequestMapping(value = "/incrHeat", method = RequestMethod.GET)
    @ResponseBody
    public ReMessage IncreaseHeat(@RequestParam(value = "wid", required = false) Integer wid,
                                  @RequestParam(value = "uid", required = false) Integer uid,
                                  @RequestParam(value = "sign", required = false) String sign,
                                  @RequestParam(value = "auth", required = false) String auth,
                                  @RequestParam(value = "s2t", required = false) String s2t,
                                  @RequestParam(value = "time", required = false) Long time,
                                  HttpSession session) {
        logger.info("作品点击:wid={},uid={},sign={},auth={},s2t={}", wid, uid, sign, auth, s2t);

        if (wid == null || wid == 0 || uid == null || uid == 0) {
            return new ReMessage(1, "参数缺失");
        }

        ReMessage reMessage = new ReMessage(-1, "服务器错误");

        if (session.getAttribute(IS_VERIFY) == null || !(Boolean) session.getAttribute(IS_VERIFY)) {
            //还未验证
            try {
                Boolean isVerify = false;
                if (sign != null && !sign.isEmpty()) {
                    logger.info("身份验证1：sign={},uid={}", sign, uid);
                    isVerify = VerifyUtil.VerifyUid(restTemplate, systemConfig.getUsrInfoUrl(), uid, sign);
                } else if (auth != null && !auth.isEmpty() && s2t != null && !s2t.isEmpty() && time != null && time!=0) {
                    logger.info("身份验证2：uid={},auth={},s2t={},time={}", uid, auth, s2t,time);
                    isVerify = VerifyUtil.VerifyUid(restTemplate, systemConfig.getUsrInfoUrl(), uid, auth, s2t, time);
                }

                if (!isVerify) {
                    logger.info("身份验证不通过");
                    reMessage.setCode(2);
                    reMessage.setMsg("身份验证不通过");
                    return reMessage;
                }
                session.setAttribute(IS_VERIFY, true);
                session.setAttribute(UID, uid);
            } catch (IOException e) {
                e.printStackTrace();
                return reMessage;
            }
        }

        try {
            uid = (Integer) session.getAttribute("uid");
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            return reMessage;
        }

        try {
            reMessage = worksShowService.incrHet(wid, uid);
        } catch (RuntimeException e) {
            logger.error("作品点击出错：{}", e.getMessage());
            return new ReMessage(-1, "服务器错误");
        }
        return reMessage;
    }


    @Resource
    private GlobalManager globalManager;


    /**
     * @MethodName : TestAddData
     * @Description : 为treeSet添加默认数据，作为测试
     */
    @RequestMapping(value = "/addData", method = RequestMethod.GET)
    @ResponseBody
    public ReMessage TestAddData(@RequestParam(value = "isSetRedis", required = false) Boolean isSetRedis,
                                 @RequestParam(value = "isSetMysql", required = false) Boolean isSetMysql) {
        logger.info("TestAddData");

        if (isSetRedis == null) {
            isSetRedis = false;
        }
        if (isSetMysql == null) {
            isSetMysql = false;
        }
        InitData initData = new InitData();
//        initData.initRedisWorksSet(globalManager, isSetRedis, isSetMysql);

        return new ReMessage(0, "操作成功");
    }
}
