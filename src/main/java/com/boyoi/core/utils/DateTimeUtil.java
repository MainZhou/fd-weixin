package com.boyoi.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间操作的帮助类
 *
 * @author Chenj
 */
public class DateTimeUtil {
    private static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat yyyyMMddNoSeparator = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat yyyyMMddHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat yyyyMMddHHmmssNoSeparator = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final SimpleDateFormat yyyyMMddHHmmssSSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final SimpleDateFormat yyyyMMddHHmmssSSSNoSeparator = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static final SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyy-MM");
    private static final SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");

    // 2286年11月21号01：46：39
    private static final long L9999999999999 = 9999999999999l;

    /**
     * 倒序排列时间。以2289年11月21号01：46：39的时间减去给出的时间
     * @return 倒序时间
     */
    public static long compareDate9999999999999(Date date){
        return L9999999999999 - date.getTime();
    }
    /**
     * 比较二个日期，返回第多少位，两个日期不相同
     * @param date1 日期1
     * @param date2 日期2
     * @return 日期相同的部份
     */
    public static String compareDate(Date date1, Date date2){

        String d1 = yyyyMMddHHmmssNoSeparator(date1);
        String d2 = yyyyMMddHHmmssNoSeparator(date2);

        for (int i=0; i<14; i++){
            if (d1.charAt(i) != d2.charAt(i)){
                return d1.substring(0, i);
            }
        }
        return d1;
    }
    /**
     * 比较二个日期，返回第多少位，两个日期不相同
     * @param date1 日期1
     * @param date2 日期2
     * @return 日期相同的部份
     */
    public static int compareDate(String date1, String date2){
        if (null != date1 && null != date2 && date1.length() == date2.length()){
            for (int i=0; i<date1.length(); i++){
                if (date1.charAt(i) != date2.charAt(i)){
                    return  date1.charAt(i) - date2.charAt(i);
                }
            }
        }
        return 0;
    }

    /**
     * 时间添加或相减
     * 格式:yyyy-MM-dd
     * @param date date对象
     * @param addType 添加的类型。如：Calendar.MINUTE
     * @param addNum 添加的值
     * @return yyyy-MM-dd
     */
    public static Date timeAdd(Date date, int addType, int addNum){
        if(null != date){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(addType, addNum);
            return calendar.getTime();

        }
        return null;
    }

    /**
     * 时间相加或相减 +-1
     * 只针对当前类型。总的时间不会改变。
     * 格式:yyyy-MM-dd
     * @param date date对象
     * @param subtractType 相减的类型。如：Calendar.MINUTE
     * @param up 相减或相减，true,相同，false相减
     * @return Date
     */
    public static Date timeRoll(Date date, int subtractType, boolean up){
        if(null != date){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.roll(subtractType, up);
            return calendar.getTime();

        }
        return null;
    }

    /**
     * 时间相加或相减 +-1
     * 只针对当前类型。总的时间不会改变。
     * 格式:yyyy-MM-dd
     * @param date date对象
     * @param subtractType 相减的类型。如：Calendar.MINUTE
     * @param amount 相减或相减
     * @return Date
     */
    public static Date timeRoll(Date date, int subtractType, int amount){
        if(null != date){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            calendar.roll(subtractType, amount);
            return calendar.getTime();

        }
        return null;
    }

    /**
     * 设置时间
     * @param date date
     * @param setType 设置类型
     * @param setValue 设置的值
     * @return 时间
     */
    public static Date timeSet(Date date, int setType, int setValue){
        if(null != date){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(setType, setValue);
            return calendar.getTime();

        }
        return null;
    }

    /**
     * 设置时间
     * @param date date
     * @param setType 设置类型
     * @param setValue 设置的值
     * @return 时间
     */
    public static Date timeSet(Date date, int[] setType, int[] setValue){
        if(null != date && setType.length == setValue.length){

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            for (int i=0; i< setType.length;i++){
                calendar.set(setType[i], setValue[i]);
            }
            return calendar.getTime();
        }
        return null;
    }

    /**
     * 日期转字符串
     * 格式:yyyy-MM-dd
     * @param date date对象
     * @return yyyy-MM-dd
     */
    public static String yyyyMMdd(Date date){
        if(null != date)
            return yyyyMMdd.format(date);
        return null;
    }

    /**
     * 字符转日期
     * 格式:yyyy-MM-dd
     * @param date yyyy-MM-dd
     * @return date对象
     */
    public static Date yyyyMMdd(String date){
        try {
            return yyyyMMdd.parse(date);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 日期转字符串
     * 格式:yyyy-MM-dd
     * @param date date对象
     * @return yyyy-MM-dd
     */
    public static String yyyyMMddNoSeparator(Date date){
        if(null != date)
            return yyyyMMddNoSeparator.format(date);
        return null;
    }

    /**
     * 字符转日期
     * 格式:yyyy-MM-dd
     * @param date yyyy-MM-dd
     * @return date对象
     */
    public static Date yyyyMMddNoSeparator(String date){
        try {
            return yyyyMMddNoSeparator.parse(date);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 日期转字符串
     * 格式:yyyy-MM-dd HH:mm
     * @param date date对象
     * @return yyyy-MM-dd hh:mm
     */
    public static String yyyyMMddHHmm(Date date){
        if(null != date)
            return yyyyMMddHHmm.format(date);
        return null;
    }

    /**
     * 字符转日期
     * 格式:yyyy-MM-dd hh:mm
     * @param date yyyy-MM-dd hh:mm
     * @return date对象
     */
    public static Date yyyyMMddHHmm(String date){
        try {
            return yyyyMMddHHmm.parse(date);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 日期转字符串
     * 格式:yyyy-MM-dd hh:mm:ss
     * @param date date对象
     * @return yyyy-MM-dd hh:mm:ss
     */
    public static String yyyyMMddhhmmss(Date date){
        if(null != date)
            return yyyyMMddHHmmss.format(date);
        return null;
    }

    /**
     * 字符转日期
     * 格式:yyyy-MM-dd hh:mm:ss
     * @param date yyyy-MM-dd hh:mm:ss
     * @return date对象
     */
    public static Date yyyyMMddhhmmss(String date){
        try {
            return yyyyMMddHHmmss.parse(date);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 日期转字符串
     * 格式:yyyyMMddHHmmss
     * @param date date对象
     * @return yyyyMMddHHmmss
     */
    public static String yyyyMMddHHmmssNoSeparator(Date date){
        if(null != date)
            return yyyyMMddHHmmssNoSeparator.format(date);
        return null;
    }

    /**
     * 字符转日期
     * 格式:yyyyMMddHHmmss
     * @param date yyyyMMddHHmmss
     * @return date对象
     */
    public static Date yyyyMMddHHmmssNoSeparator(String date){
        try {
            return yyyyMMddHHmmssNoSeparator.parse(date);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 日期转字符串
     * 格式:yyyyMMddHHmmssSSS
     * @param date date对象
     * @return yyyyMMddHHmmss
     */
    public static String yyyyMMddHHmmssSSS(Date date){
        if(null != date)
            return yyyyMMddHHmmssSSS.format(date);
        return null;
    }

    /**
     * 字符转日期
     * 格式:yyyyMMddHHmmss
     * @param date yyyyMMddHHmmss
     * @return date对象
     */
    public static Date yyyyMMddHHmmssSSS(String date){
        try {
            return yyyyMMddHHmmssSSS.parse(date);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 日期转字符串
     * 格式:yyyyMMddHHmmssSSS
     * @param date date对象
     * @return yyyyMMddHHmmss
     */
    public static String yyyyMMddHHmmssSSSNoSeparator(Date date){
        if(null != date)
            return yyyyMMddHHmmssSSSNoSeparator.format(date);
        return null;
    }

    /**
     * 字符转日期
     * 格式:yyyyMMddHHmmss
     * @param date yyyyMMddHHmmss
     * @return date对象
     */
    public static Date yyyyMMddHHmmssSSSNoSeparator(String date){
        try {
            return yyyyMMddHHmmssSSSNoSeparator.parse(date);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 日期转字符串
     * 格式:yyyy-MM
     * @param date date对象
     * @return yyyy-MM
     */
    public static String yyyyMM(Date date){
        if(null != date)
            return yyyyMM.format(date);
        return null;
    }

    /**
     * 字符转日期
     * 格式:yyyy-MM
     * @param date yyyy-MM
     * @return date对象
     */
    public static Date yyyyMM(String date){
        try {
            return yyyyMM.parse(date);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 日期转字符串
     * 格式:yyyy
     * @param date date对象
     * @return yyyy
     */
    public static String yyyy(Date date){
        if(null != date)
            return yyyy.format(date);
        return null;
    }

    /**
     * 字符转日期
     * 格式:yyyy
     * @param date yyyy
     * @return date对象
     */
    public static Date yyyy(String date){
        try {
            return yyyy.parse(date);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 获取两个date相差秒数 1000一秒多少豪秒
     * @param date1 日期1
     * @param date2 日期2
     * @return 相差秒数
     */
    public static Long getSecond(Date date1,Date date2){
        if (null != date1 && null != date2)
            return Math.abs(date1.getTime() - date2.getTime()) / 1000;
        else
            return 0l;
    }

    /**
     * 获取两个date相差天数 86400000一天多少豪秒
     * @param date1 日期1
     * @param date2 日期2
     * @return 相差天数
     */
    public static Long getDays(Date date1,Date date2){
        if (null != date1 && null != date2)
            return Math.abs(date1.getTime() - date2.getTime()) / 86400000l;
        else
            return 0l;
    }

    /**
     * 获取两个date相差月数 259200000=一天多少秒*30
     * @param date1 日期1
     * @param date2 日期2
     * @return 相差月数
     */
    public static Long getMonths(Date date1,Date date2) {
        if (null != date1 && null != date2)
            return Math.abs(date1.getTime() - date2.getTime()) / 2592000000l;
        return null;
    }

    /**
     * 获取两个date相差年份 31104000000 = 一天多少秒*30*12
     * @param date1 日期1
     * @param date2 日期2
     * @return 相差月数
     */
    public static Long getYears(Date date1,Date date2) {
        if (null != date1 && null != date2)
            return Math.abs(date1.getTime() - date2.getTime()) / 31104000000l;
        return null;
    }

}
