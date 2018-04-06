package com.wan.sys.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang.time.DateFormatUtils;


/**   
 * 文件名称： 日期工具类
 * 内容摘要： 
 * 创建人： huangfei
 * 创建日期： 2015年5月11日
 * 版本号： v1.0.0
 * 公  司：亚德科技股份有限公司
 * 版权所有： (C)2001-2015     
 * 修改记录1 
 * 修改日期：
 * 版本号：
 * 修改人：
 * 修改内容：  
 **/ 
public class DateUtil {
	
	//日期格式
	public static final String Y_M_D = "yyyy-MM-dd";  
    public static final String Y_M_D_HM = "yyyy-MM-dd HH:mm";  
    public static final String Y_M_D_HMS = "yyyy-MM-dd HH:mm:ss";  
    public static final String YMD = "yyyyMMdd";  
    public static final String YMDHM = "yyyyMMddHHmm";  
    public static final String YMDHMS = "yyyyMMddHHmmss";  
    public static final String ymd = "yyyy/MM/dd";  
    public static final String ymd_HM = "yyyy/MM/dd HH:mm";  
    public static final String ymd_HMS = "yyyy/MM/dd HH:mm:ss";  
    public static final String DATE_PATTERN = "HH:mm";

	/**
	 * @Description 将Date类型转换为字符串
	 * @param date 日期类型
	 * @return String 日期字符串,格式：yyyy-MM-dd HH:mm:ss
	 */
	public static String format(Date date) {
		return format(date, Y_M_D_HMS);
	}

	/**
	 * @Description 将Date类型转换为指定格式字符串
	 * @param date 日期类型
	 * @param pattern 字符串格式 默认yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		if (StringUtil.isEmpty(pattern) || StringUtil.isBlank(pattern) || StringUtil.isEquals("null", pattern)) {
			pattern = Y_M_D_HMS;
		}
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * 按照pattern所指示的样式格式化日期,以String类型返回
	 * 
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static String format1(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * @Description 将yyyy-MM-dd HH:mm:ss格式字符串转换成日期
	 * @param dateString
	 * @return Date
	 */
	public static Date format(String dateString) {
		return format(dateString, null);
	}

	/**
	 * @Description 将字符串转换为Date类型
	 * @param dateString 日期字符串
	 * @param pattern 格式
	 * @return Date
	 */
	public static Date format(String dateString, String pattern) {
		if (StringUtil.isEmpty(pattern) || StringUtil.isBlank(pattern) || StringUtil.isEquals("null", pattern)) {
			pattern = Y_M_D_HMS;
		}
		if (StringUtil.isEmpty(dateString) || StringUtil.isBlank(dateString) || StringUtil.isEquals("null", dateString)) {
			return null;
		}
		Date d = null;
		try {
			d = new SimpleDateFormat(pattern).parse(dateString);
		} catch (ParseException pe) {
			throw new RuntimeException("日期转换异常："+dateString);
		}
		return d;
	}
	/**
	 * @Description 获取当前时间，格式:yyyyMMddHHmmss
	 * @return String
	 */
	public static String getCurrDateTime() {
		return DateFormatUtils.format(System.currentTimeMillis(),YMDHMS);
	}
	/**
	 * @Description 获取当前日期，格式:yyyy-MM-dd
	 * @return String
	 */
	public static String getCurrDate() {
		return DateFormatUtils.format(System.currentTimeMillis(),Y_M_D);
	}
	/** 
     * 智能将日期转成字符串 
     * @param date 
     * @return 
     */  
    public static String smartFormat(Date date) {  
        String dateStr = null;  
        if (date == null) {  
            dateStr = "";  
        } else {  
            try {  
                dateStr = format(date, Y_M_D_HMS);  
                //时分秒  
                if (dateStr.endsWith(" 00:00:00")) {  
                    dateStr = dateStr.substring(0, 10);  
                }  
                //时分  
                else if (dateStr.endsWith("00:00")) {  
                    dateStr = dateStr.substring(0, 16);  
                }  
                //秒  
                else if (dateStr.endsWith(":00")) {  
                    dateStr = dateStr.substring(0, 16);  
                }  
            } catch (Exception ex) {  
                throw new IllegalArgumentException("转换日期失败: " + ex.getMessage(), ex);  
            }  
        }  
        return dateStr;  
    }  
  
    /** 
     * 智能将字符串转成日期
     * @param text 
     * @return 
     */  
    public static Date smartFormat(String text) {  
        Date date = null;  
        try {  
            if (text == null || text.length() == 0) {  
                date = null;  
            } else if (text.length() == 10) {  
                date = format(text, Y_M_D);  
            } else if (text.length() == 13) {  
                date = new Date(Long.parseLong(text));  
            } else if (text.length() == 16) {  
                date = format(text, Y_M_D_HM);  
            } else if (text.length() == 19) {  
                date = format(text, Y_M_D_HMS);  
            } else {  
                throw new IllegalArgumentException("日期长度不符合要求!");  
            }  
        } catch (Exception e) {  
            throw new IllegalArgumentException("日期转换失败!");  
        }  
        return date;  
    }  
    /**
	 * 以long型时间生成日历,以Calendar对象返回
	 * 
	 * @param timeStamp
	 * @return Calendar
	 */
	static public java.util.Calendar getCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	/**
	 * 获取指定日期的往后或往前i天的日期
	 * @param bizDate 
	 * @param i 
	 * @return
	 */
	public static Date getDateByIndex(Date bizDate,int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(bizDate);
		calendar.add(Calendar.DATE, i);
		return calendar.getTime();
	}
	
	/**
	 * 获取指定日期的往后或往前i月的日期
	 * @param bizDate 
	 * @param i 
	 * @return
	 */
	public static Date getMonthByIndex(Date bizDate,int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(bizDate);
		calendar.add(Calendar.MONTH, i);
		return calendar.getTime();
	}
	
	

	/**
	 * 获得上个月的第一天
	 * 
	 * @param bizDate
	 * @return
	 */
	public static Date getFirstDayOfLastMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * 获得当月的第一天
	 * 
	 * @param bizDate
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获得上一个月的最后一天
	 * 
	 * @param bizDate
	 * @return
	 */
	public static Date getLastDayOfLastMonth(Date bizDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(bizDate);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}
	
	
	/**
	 * 获得上当月的最后一天
	 * 
	 * @param bizDate
	 * @return
	 */
	public static Date getLastDayOfMonth(Date bizDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(bizDate);
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}
	/**
	 * 获得上当月的数量
	 * 
	 * @param bizDate
	 * @return
	 */
	public static int getLastDayOfMonth1(Date bizDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(bizDate);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	
	/**
	 * 获得上当月的数量
	 * 
	 * @param bizDate
	 * @return
	 */
	public static int getMonth(Date bizDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(bizDate);
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	
	/**
	 * 获得上当月的数量
	 * 
	 * @param bizDate
	 * @return
	 */
	public static Date getMonth1(Date bizDate,int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(bizDate);
		calendar.set(Calendar.MONTH, i-1);
		return calendar.getTime();
	}

	
	/**
	 * 返回是否超过某时间
	 * @param bizDate 
	 * @param i 
	 * @return
	 */
	public static boolean getDateByCheck(Date date) {
		boolean result= false;
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(new Date());
		Calendar calendar1 = Calendar.getInstance(Locale.CHINA);
	    calendar1.setTime(date);
		if(calendar1.getTime().getTime()>calendar.getTime().getTime()){
			result=true;
		}
		return result;
	}
	
	  /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    } 
    /**
	 * 获得当前系统的系统日期时间,以TimeStamp对象返回结果
	 * 
	 * @return Timestamp对象
	 */
	public static Timestamp getSysTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
    
    /**  
     * 计算两个日期之间相差小时数
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int hoursBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    } 
    
    /**
	 * 获得当前系统的系统日期,以Date类型返回
	 * 
	 * @return Date对象
	 */
	public static Date getSysDate() {
		return new Date();
	}
	
  
}
