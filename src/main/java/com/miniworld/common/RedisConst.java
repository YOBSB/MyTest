package com.miniworld.common;

public class RedisConst {


    /**
     * 未审核作品集合
     */
    public final static String COLLECTION_UNAUDITED = "UNAUDITED";

    /**
     * 已通过审核的作品集合
     */
    public final static String COLLECTION_AUDITED = "AUDITED";

    /**
     * 已拒绝的作品集合
     */
    public final static String COLLECTION_RESUSED = "RESUSED";

    /**
     * 已屏蔽的作品集合
     */
    public final static String COLLECTION_SHIELDING = "SHIELDING";


    /**
     * 作品热度有改变的集合1
     */
    public final static String COLLECTION_HEAT_CHANGE_1 = "HEAT_CHANGE_1";

    /**
     * 作品热度有改变的集合2
     */
    public final static String COLLECTION_HEAT_CHANGE_2 = "HEAT_CHANGE_2";


    /**
     * 作品集合
     */
    public final static String COLLECTION_WORKS = "WORKS";

    /**
     * 作品被点击信息，存储点击该作品的用户id和最新一次点击的时间
     */
    public final static String COLLECTION_WORKS_HEAT = "WORKS_HEAT";


    /**
     * redis锁前缀
     * 作品热度更新锁
     */
    public final static String PRE_LOCKED = "PRE_LOCKED_";

    /**
     * redis锁前缀
     * 作品被点击的用户id和时间等信息的锁
     */
    public final static String PRE_LOCKED_WORKS_HEAT = "PRE_LOCKED_WORKS_HEAT_";


    /**
     * works属性后缀
     */
    public final static String SUF_WORKS_ID = "_ID";
    public final static String SUF_WORKS_UID = "_UID";
    public final static String SUF_WORKS_NAME= "_NAME";
    public final static String SUF_WORKS_QQ= "_QQ";
    public final static String SUF_WORKS_MAIL= "_MAIL";
    public final static String SUF_WORKS_PHONE= "_PHONE";
    public final static String SUF_WORKS_MATES= "_MATES";
    public final static String SUF_WORKS_MAP_ID= "_MAP_ID";
    public final static String SUF_WORKS_WORKS_NAME= "_WORKS_NAME";
    public final static String SUF_WORKS_INTR= "_INTR";
    public final static String SUF_WORKS_MAIN_IMG= "_MAIN_IMG";
    public final static String SUF_WORKS_MAIN_SMALL_IMG= "_MAIN_SMALL_IMG";
    public final static String SUF_WORKS_IMG_1= "_IMG_1";
    public final static String SUF_WORKS_SMALL_IMG_1= "_SMALL_IMG_1";
    public final static String SUF_WORKS_IMG_2= "_IMG_2";
    public final static String SUF_WORKS_SMALL_IMG_2= "_SMALL_IMG_2";
    public final static String SUF_WORKS_IMG_3= "_IMG_3";
    public final static String SUF_WORKS_SMALL_IMG_3= "_SMALL_IMG_3";
    public final static String SUF_WORKS_STATE= "_STATE";
    public final static String SUF_WORKS_SUBMIT_TIME= "_SUBMIT_TIME";
    public final static String SUF_WORKS_CREATE_TIME= "_CREATE_TIME";
    public final static String SUF_WORKS_UPDATE_TIME= "_UPDATE_TIME";
    public final static String SUF_WORKS_HEAT= "_HEAT";
    public final static String SUF_WORKS_WEEK_HEATE= "_WEEK_HEATE";
    public final static String SUF_WORKS_WEEK_HEATE_TIME= "_WEEK_HEATE_TIME";
}
