package org.rency.common.utils.tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.rency.common.utils.exception.CoreException;

public class DateUtils {
	
	public static final String PATTERN_FULLDATETIME_MILLS = "yyyy-MM-dd HH:mm:ss:SSS";
	public static final String PATTERN_FULLDATETIME_MILLS_STR = "yyyyMMddHHmmssSSS";
	public static final String PATTERN_FULLDATETIME = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_FULLDATETIME_STR = "yyyyMMddHHmmss";
	public static final String PATTERN_DATE = "yyyy-MM-dd";
	public static final String PATTERN_DATE_STR = "yyyyMMdd";
	public static final String PATTERN_TIME = "HH:mm:ss";
	public static final String PATTERN_TIME_STR = "HHmmss";
	public static final long    ONE_DAY_MILL_SECONDS = 86400000;

	
	/**
	 * 获取当前时间yyyyMMddHHmmssSSS
	 * @return
	 */
	public static String getNowDateTimeMillsStr(){
		return getNowDateTime(PATTERN_FULLDATETIME_MILLS_STR);
	}
	
	/**
	 * 获取当前时间 yyyy-MM-dd HH:mm:ss:SSS
	 * @return
	 */
	public static String getNowDateTimeMills(){
		return getNowDateTime(PATTERN_FULLDATETIME_MILLS);
	}
	
	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * @return
	 */
	public static String getNowDateTimeStr(){
		return getNowDateTime(PATTERN_FULLDATETIME_STR);
	}
	
	/**
	 * 获取当前时间 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getNowDateTime(){
		return getNowDateTime(PATTERN_FULLDATETIME);
	}
	
	/**
	 * 获取当前日期 yyyyMMdd
	 * @return
	 */
	public static String getNowDateStr(){
		return getNowDateTime(PATTERN_DATE_STR);
	}
	
	/**
	 * 获取当前日期 yyyy-MM-dd
	 * @return
	 */
	public static String getNowDate(){
		return getNowDateTime(PATTERN_DATE);
	}
	
	/**
	 * 获取当前时间 HHmmss
	 * @return
	 * @throws CoreException
	 */
	public static String getNowTimeStr(){
		return getNowDateTime(PATTERN_TIME_STR);
	}
	
	/**
	 * 获取当前时间 HH:mm:ss
	 * @return
	 * @throws CoreException
	 */
	public static String getNowTime(){
		return getNowDateTime(PATTERN_TIME);
	}
	
	/**
	 * 获取当前时间
	 * @param pattern
	 * @return
	 * @throws CoreException
	 */
	public static String getNowDateTime(String pattern){
		return getDateFormat(pattern).format(new Date());
	}
	
	/**
	 * 将日期转换为需要的字符串
	 * @param dateStr
	 * @param pattern
	 * @return
	 * @throws CoreException
	 */
	public static Date parseDate(String dateStr,String pattern) throws ParseException{
		return new SimpleDateFormat(pattern).parse(dateStr);
	}
	
	/**
     * 计算当前时间几小时之后的时间
     * @param date
     * @param hours
     * @return
     */
    public static Date addHours(Date date, long hours) {
        return addMinutes(date, hours * 60);
    }

    /**
     * 计算当前时间几分钟之后的时间
     * @param date
     * @param minutes
     * @return
     */
    public static Date addMinutes(Date date, long minutes) {
        return addSeconds(date, minutes * 60);
    }

    /**
     * 计算当前时间几秒之后的时间
     * @param date
     * @param secs
     * @return
     */
    public static Date addSeconds(Date date, long secs) {
        return new Date(date.getTime() + (secs * 1000));
    }
	
    /**
     * 取得两个日期间隔秒数（日期1-日期2）
     * @param one 日期1
     * @param two 日期2
     * @return 间隔秒数
     */
    public static long getDiffSeconds(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();
        sysDate.setTime(one);
        Calendar failDate = new GregorianCalendar();
        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / 1000;
    }

    /**
     * 取得两个日期间隔分钟（日期1-日期2）
     * @param one
     * @param two
     * @return 间隔分钟
     */
    public static long getDiffMinutes(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();
        sysDate.setTime(one);
        Calendar failDate = new GregorianCalendar();
        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (60 * 1000);
    }
    
    /**
     * 取得两个日期间隔小时（日期1-日期2）
     * @param one
     * @param two
     * @return 间隔小时
     */
    public static long getDiffHours(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();
        sysDate.setTime(one);
        Calendar failDate = new GregorianCalendar();
        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (60 * 60 * 1000);
    }

    /**
     * 取得两个日期的间隔天数
     * @param one
     * @param two
     * @return 间隔天数
     */
    public static long getDiffDays(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();
        sysDate.setTime(one);
        Calendar failDate = new GregorianCalendar();
        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 获得指定时间当天起点时间
     * @param date
     * @return
     */
    public static Date getDayBegin(Date date) {
        DateFormat df = getDateFormat(PATTERN_DATE_STR);
        String dateString = df.format(date);
        try {
            return df.parse(dateString);
        } catch (ParseException e) {
            return date;
        }
    }
    
    /**
     * 获取当前时间的前几天或者后几天的日期
     * @param days
     * @return
     */
    public static Date getDateNearCurrent(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        Date date = calendar.getTime();
        return date;
    }
    
    /**
     * 获取当前日期前几天
     * @param days
     * @return 前几天日期
     */
    public static String getBeforeDayString(int days) {
        Date date = new Date(System.currentTimeMillis() - (ONE_DAY_MILL_SECONDS * days));
        DateFormat dateFormat = getDateFormat(PATTERN_DATE_STR);

        return getDateString(date, dateFormat);
    }
    
    /**
     * 获取日期格式
     * @param pattern
     * @return
     */
    public static DateFormat getDateFormat(String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        df.setLenient(false);
        return df;
    }
    
    /**
     * 获取给定日期、格式的日期字符串
     * @param date 日期
     * @param dateFormat 格式
     * @return
     */
    public static String getDateString(Date date, DateFormat dateFormat) {
        if (date == null || dateFormat == null) {
            return null;
        }
        return dateFormat.format(date);
    }

}