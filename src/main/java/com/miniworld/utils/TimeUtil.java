package com.miniworld.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static sun.security.x509.InvalidityDateExtension.DATE;

public class TimeUtil {

    private static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE = "yyyy-MM-dd";
    private static final String TIME = "HH:mm:dd";
    private static final String YEAR = "yyyy";
    private static final String MONTH = "MM";
    private static final String DAY = "dd";
    private static final String HOUR = "HH";
    private static final String MINUTE = "mm";
    private static final String SEC = "ss";
    private static final String DATETIMECHINESE = "yyyy年MM月dd日 HH时mm分ss秒";
    private static final String DATECHINESE = "yyyy年MM月dd日";
    private static final String SIMPLEDATECHINESE = "MM月dd日";

    /**
     * 获取现在的周次
     *
     * @return
     */
    public static int getWeekNum() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置周一为一周的第一天
        cal.setTime(date);
        int num = cal.get(Calendar.WEEK_OF_YEAR);
        return num;
    }

    /**
     * 判断一个自定义日期是否在一个起止日期内<br/>
     * 例如:判断2018-06-15 00:00:00是否在2018-06-15 00:00:00~2018-06-15 00:00:00
     *
     * @param start_time
     * @param over_time
     * @return (int)&nbsp;在这个时间段内返回1，不在返回0
     * @throws ParseException
     */
    public static int isOutOfDate(String time, String start_time,
                                  String over_time) throws ParseException {
        long timeLong = new SimpleDateFormat(DATETIME).parse(time).getTime();
        long ckStartTimeLong = new SimpleDateFormat(DATETIME).parse(start_time)
                .getTime();
        long ckOverTimeLong = new SimpleDateFormat(DATETIME).parse(over_time)
                .getTime();
        if (timeLong >= ckStartTimeLong && timeLong <= ckOverTimeLong) {
            return 1;
        }
        return 0;
    }

    /**
     * 判断是否在一个在时间段内，单位 小时<br/>
     * 例如:8:00~10:00
     *
     * @param time_limit_start
     * @param time_limit_over
     * @return (int) 1在这个时间段内，0不在这个时间段内
     * @throws ParseException
     */
    public static int isInTimeClock(String time_limit_start, String time_limit_over)
            throws ParseException {
        // 获取当前日期
        String nowDate = new SimpleDateFormat(DATE).format(new Date());
        return isOutOfDate(new SimpleDateFormat(DATETIME).format(new Date()), nowDate + " " + time_limit_start, nowDate + " "
                + time_limit_over);
    }

    //获取今年的开始时间
    public static Long getCurrentYearStartTime(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), 0, 0, 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.YEAR));
        return cal.getTime().getTime();
    }

    //获取今年的结束时间
    public static Long getCurrentYearEndTime(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), 11, 31, 23, 59, 59);
        return cal.getTime().getTime();
    }




    // 获取当前时间
    public static String getDatetime(Timestamp timestamp) {
        return new SimpleDateFormat(DATETIME).format(timestamp);
    }

    // 获取当前时间
    public static String getTime() {
        return new SimpleDateFormat(TIME).format(new Date());
    }

    // 获取当前年
    public static String getYear() {
        return new SimpleDateFormat(YEAR).format(new Date());
    }

    // 获取当前月
    public static String getMonth() {
        return new SimpleDateFormat(MONTH).format(new Date());
    }

    // 获取当前日
    public static String getDay() {
        return new SimpleDateFormat(DAY).format(new Date());
    }

    // 获取当前时
    public static String getHour() {
        return new SimpleDateFormat(HOUR).format(new Date());
    }

    // 获取当前分
    public static String getMinute() {
        return new SimpleDateFormat(MINUTE).format(new Date());
    }

    // 获取当前秒
    public static String getSec() {
        return new SimpleDateFormat(SEC).format(new Date());
    }
}
