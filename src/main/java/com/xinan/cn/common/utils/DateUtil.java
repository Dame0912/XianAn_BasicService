package com.xinan.cn.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期转换工具类
 */
public class DateUtil {
    private static Map<String, ThreadLocal<SimpleDateFormat>> threadLocalMap = new HashMap<>();

    public final static String DATEFORMATE_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";

    public final static String DATEFORMATE_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public final static String DATEFORMATE_YYYYMMDD = "yyyyMMdd";

    public final static String DATEFORMATE_YYYYMMDDHHMMSS_SSS = "yyyyMMddHHmmssSSS";

    public final static String DATEFORMATE_YYYY_MM_DD = "yyyy-MM-dd";

    public static SimpleDateFormat getSimpleDateFormat(String formate) {
        ThreadLocal<SimpleDateFormat> threadLocal = threadLocalMap.get(formate);
        if (threadLocal == null) {
            threadLocal = new ThreadLocal<SimpleDateFormat>();
        }
        SimpleDateFormat stf = threadLocal.get();
        if (stf == null) {
            stf = new SimpleDateFormat(formate);
            threadLocal.set(stf);
        }
        return stf;
    }

    /**
     * 返回当前时间的自定义格式String
     */
    public static String getCurTimeStr(String dateFormat) {
        if (null == dateFormat || "".equals(dateFormat.trim())) {
            return "";
        }
        Date currentTime = new Date();
        SimpleDateFormat formatter = getSimpleDateFormat(dateFormat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 返回当前时间的毫秒数
     */
    public static long getCurMillis() {
        return getDateMillis(new Date());
    }

    /**
     * 返回当前时间的秒数
     */
    public static long getCurSeconds() {
        return getDateSeconds(new Date());
    }

    /**
     * 返回指定日期的毫秒数
     */
    public static long getDateMillis(Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * 获取指定日期的秒数
     */
    public static long getDateSeconds(Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis() / 1000;
    }

    /**
     * 返回指定时间的自定义格式String
     */
    public static String getFormatDate(Date date, String dateFormat) {
        if (null == date || null == dateFormat || "".equals(dateFormat.trim())) {
            return "";
        }
        SimpleDateFormat formatter = getSimpleDateFormat(dateFormat);
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 把自定义格式的日期字符串转换成 java.util.Date
     */
    public static Date getDateFromStr(String dateStr, String format) {
        SimpleDateFormat df = getSimpleDateFormat(format);
        Date da = null;
        try {
            da = df.parse(dateStr);
        } catch (ParseException e) {
        }
        return da;
    }


    /**
     * 获取当天的最开始时间 java.util.Date
     */
    public static Date getCurStartTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当天的最结束时间 java.util.Date
     */
    public static Date getCurEndTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * Date转Timestamp
     */
    public static Timestamp getTimestampFromDate(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * Timestamp转Date
     */
    public static Date getDateFromTimestamp(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }

    /**
     * 把自定义格式的日期字符串转换成java.sql.Timestamp
     */
    public static Timestamp getTimestampFromDateStr(String dateStr, String format) {
        SimpleDateFormat df = getSimpleDateFormat(format);
        Date da;
        try {
            da = df.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
        return new Timestamp(da.getTime());
    }

    /**
     * 获取指定时间间隔天数的时间
     */
    public static Date getAddDate(Date startDate, int days) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    /**
     * 获取指定时间间隔分钟的时间
     */
    public static Date getAddDateM(Date startDate, int minutes) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(startDate);
        calendar.add(Calendar.MINUTE, +minutes);
        return calendar.getTime();
    }

    /**
     * 获取指定时间间隔秒的时间
     */
    public static Date getAddDateS(Date startDate, int sec) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(startDate);
        calendar.add(Calendar.SECOND, +sec);
        return calendar.getTime();
    }

    /**
     * 比较2个日期相差的天数
     */
    public static int getIntervalDays(Date beginDate, Date endDate) {
        long msll = endDate.getTime() - beginDate.getTime();
        return (int) (msll / (24 * 60 * 60 * 1000));
    }

    /**
     * 比较2个日期相差的分钟数
     */
    public static int getIntervalMins(Date beginDate, Date endDate) {
        long msll = endDate.getTime() - beginDate.getTime();
        return (int) (msll / (1000 * 60));
    }

    /**
     * 比较2个日期相差的秒数
     */
    public static int getIntervalMills(Date beginDate, Date endDate) {
        long msll = endDate.getTime() - beginDate.getTime();
        return (int) (msll / (1000));
    }

    /**
     * 判断时间是否在指定时间之内
     *
     * @param time 判断的时间
     * @param days 天数
     * @return boolean
     */
    public static boolean isLatestDate(Date time, int days) {
        Calendar calendar = Calendar.getInstance();  //得到日历
        calendar.setTime(new Date());//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -days);  //设置为7天前
        Date beforeDays = calendar.getTime();   //得到7天前的时间
        if (beforeDays.getTime() < time.getTime()) {
            return true;
        } else {
            return false;
        }
    }

}
