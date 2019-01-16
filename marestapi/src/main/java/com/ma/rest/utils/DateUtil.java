package com.ma.rest.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 提供两种用法 1.利用public static method进行日期格式的转换 2.利用内容的Calendar object
 * 进行日期的计算，适合有大量的日期计算，用method 1则效率低的场合
 * 
 * @author zhangxiuwei
 */
@SuppressWarnings({ "deprecation" })
public class DateUtil {

	public static final long DAY_MILLI = 24 * 60 * 60 * 1000; // 一天的MilliSecond数

	/**
	 * 要用到的DATE Format的定义
	 */
	public static String DATE_FORMAT_DATEONLY = "yyyy-MM-dd";
	public static String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	
	private static SimpleDateFormat sdfDateOnly = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATEONLY);
	private static SimpleDateFormat sdfDateTime = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATETIME);

	/**
	 * 取得指定日期所在月的最后一天
	 * 
	 * @param timestamp java.sql.Timestamp Object
	 * @return java.sql.Timestamp instance
	 * @since 1.0
	 * @history
	 */
	public static int[] DAY_OF_MONTH_LEAP_YEAR = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	public static int[] DAY_OF_MONTH_NON_LEAP_YEAR = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	/**
	 * 得到年月日形式的日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getDate(String date) {
		if (date == null) {
			return null;
		}
		if (date.length() > 10) {
			return date.substring(0, 10);
		} else {
			return date;
		}
	}

	/*****************************************************
	 * java.util.Date ==> String 的转换函数
	 *****************************************************/
	/**
	 * 利用缺省的Date格式(YYYY/MM/DD)转化String到Date
	 * 
	 * @param sDate
	 *            Date string
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static java.util.Date toDate(String sDate) {
		return toDate(sDate, DateUtil.sdfDateOnly);
	}

	/**
	 * 根据指定的Format转化String到Date
	 * 
	 * @param sDate Date string
	 * @param sFmt Date format , DATE_FORMAT_DATEONLY or DATE_FORMAT_DATETIME
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static java.util.Date toDate(String sDate, String sFmt) {
		if (sFmt.equals(DateUtil.DATE_FORMAT_DATETIME)) { // "YYYY/MM/DD HH24:MI:SS"
			return toDate(sDate, DateUtil.sdfDateTime);
		} else if (sFmt.equals(DateUtil.DATE_FORMAT_DATEONLY)) { // YYYY/MM/DD
			return toDate(sDate, DateUtil.sdfDateOnly);
		}
		
		return null;
	}

	/**
	 * 利用指定SimpleDateFormat instance转化String到Date
	 * 
	 * @param sDate Date string
	 * @param formatter SimpleDateFormat instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	private static java.util.Date toDate(String sDate, SimpleDateFormat formatter) {
		try {
			return formatter.parse(sDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/*****************************************************
	 * String ==> java.util.Date 的转换函数
	 *****************************************************/
	/**
	 * 根据缺省的Format(YYYY/MM/DD)转化java.util.Date到String
	 * 
	 * @param dt java.util.Date instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static String toString(java.util.Date dt) {
		if (dt == null) {
			return "";
		}
		
		return toString(dt, DateUtil.sdfDateOnly);
	}

	/**
	 * 将整数时间格式转换成日期时间格式
	 * 
	 * @param timeLong 整数格式的时间
	 * @return 日期时间格式的时间
	 */
	public static String longToTimeString(long timeLong) {
		return toString(new java.util.Date(timeLong), DateUtil.sdfDateTime);
	}

	/**
	 * 根据指定的Format转化java.util.Date到String
	 * 
	 * @param dt java.util.Date instance
	 * @param sFmt Date format , DATE_FORMAT_DATEONLY or DATE_FORMAT_DATETIME
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static String toString(java.util.Date dt, String sFmt) {
		if (dt == null) {
			return "";
		}
		
		if (sFmt.equals(DateUtil.DATE_FORMAT_DATETIME)) {
			return toString(dt, DateUtil.sdfDateTime);
		}
		
		return toString(dt, DateUtil.sdfDateOnly);
	}

	/**
	 * 利用指定SimpleDateFormat instance转换java.util.Date到String
	 * 
	 * @param dt java.util.Date instance
	 * @param formatter SimpleDateFormat Instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static String toString(java.util.Date dt, SimpleDateFormat formatter) {
		try {
			return formatter.format(dt).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 比较两个java.sql.Timestamp instance 的年月日部分是否相同
	 * 
	 * @param date1 java.sql.Timestamp Object
	 * @param date2 java.sql.Timestamp Object
	 * @return true : 年月日部分相同
	 * @return false : 不同
	 * @since 1.0
	 * @history
	 */
	public static boolean isSameDay(Timestamp date1, Timestamp date2) {
		String s1 = date1.toString().substring(0, DateUtil.DATE_FORMAT_DATEONLY.length());
		String s2 = date2.toString().substring(0, DateUtil.DATE_FORMAT_DATEONLY.length());
		return s1.equalsIgnoreCase(s2);
	}

	/**
	 * 取得指定日期所在周的第一天(Sunday)
	 * 
	 * @param timestamp java.sql.Timestamp Object
	 * @return java.sql.Timestamp instance
	 * @since 1.0
	 * @history
	 */
	public static Timestamp getFirstDayOfWeek(Timestamp timestamp) {
		return DateUtil.addDays(timestamp, 1 - DateUtil.getWeekdayOfTimestamp(timestamp));
	}

	/**
	 * 取得指定日期所在周的最后一天(Saturday)
	 * 
	 * @param timestamp java.sql.Timestamp Object
	 * @return java.sql.Timestamp instance
	 * @since 1.0
	 * @history
	 */
	public static Timestamp getLastDayOfWeek(Timestamp timestamp) {
		return DateUtil.addDays(timestamp, 7 - DateUtil.getWeekdayOfTimestamp(timestamp));
	}

	/**
	 * 取得指定日期所在月的1号所在周的Sunday(可能是上个月的日期)
	 * 
	 * @param timestamp java.sql.Timestamp Object
	 * @return java.sql.Timestamp instance
	 * @since 1.0
	 * @history
	 */
	public static Timestamp getFirstSundayOfMonth(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		return DateUtil.getFirstDayOfWeek(getFirstDayOfMonth(timestamp));
	}

	/**
	 * 取得指定日期所在月的最后一天(如31号)所在周的Saturday(可能是下个月的日期)
	 * 
	 * @param timestamp java.sql.Timestamp Object
	 * @return java.sql.Timestamp instance
	 * @since 1.0
	 * @history
	 */
	public static Timestamp getLastSaturdayOfMonth(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		return DateUtil.getLastDayOfWeek(getLastDayOfMonth(timestamp));
	}

	/**
	 * 取得指定日期所在月的第一天
	 * 
	 * @param timestamp java.sql.Timestamp Object
	 * @return java.sql.Timestamp instance
	 * @since 1.0
	 * @history
	 */
	public static Timestamp getFirstDayOfMonth(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		return DateUtil.addDays(timestamp, 1 - DateUtil.getDayOfTimestamp(timestamp));
	}

	/**
	 * 取得两个日期之间的日数
	 * 
	 * @return t1到t2间的日数，如果t2 在 t1之后，返回正数，否则返回负数
	 */
	public static long daysBetween(Timestamp t1, Timestamp t2) {
		return (t2.getTime() - t1.getTime()) / DAY_MILLI;
	}

	public static long daysBetween(java.util.Date t1, java.util.Date t2) {
		return daysBetween(new Timestamp(t1.getTime()), new Timestamp(t2.getTime()));
	}

	/**
	 * Format year/month/day to YYYY/MM/DD format
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return YYYY/MM/DD format String
	 */
	private static String formatYMD(int year, int month, int day) {
		String temp = String.valueOf(year) + "/";
		if (month < 10) {
			temp += "0" + String.valueOf(month) + "/";
		} else {
			temp += String.valueOf(month) + "/";
		}
		if (day < 10) {
			temp += "0" + String.valueOf(day);
		} else {
			temp += String.valueOf(day);
		}
		return temp;
	}

	public static Timestamp getLastDayOfMonth(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		// out = (java.sql.Timestamp )timestamp.clone() ;
		// day = DateUtil.getDayOfTimestamp(timestamp);
		// int month = DateUtil.getMonthOfTimestamp(timestamp);
		GregorianCalendar obj = DateUtil.convertTimestampToCalendar(timestamp);
		// month : 0 -- 11
		int day = 0;
		int year = obj.get(GregorianCalendar.YEAR);
		int month = obj.get(GregorianCalendar.MONTH) + 1;
		if (obj.isLeapYear(obj.get(GregorianCalendar.YEAR))) {
			day = DateUtil.DAY_OF_MONTH_LEAP_YEAR[month - 1];
		} else {
			day = DateUtil.DAY_OF_MONTH_NON_LEAP_YEAR[month - 1];
		}
		/*
		 * modified by ChenJP 2000/11/16 String temp = String.valueOf(year) +
		 * "/"; if( month < 10 ){ temp += "0" + String.valueOf(month) + "/";
		 * }else{ temp += String.valueOf(month)+ "/"; } if( day < 10 ){ temp +=
		 * "0" + String.valueOf(day); }else{ temp += String.valueOf(day); }
		 * //cat.debug("temp=" + temp); out = DateUtil.toSqlTimestamp(temp);
		 */
		Timestamp out = DateUtil.toSqlTimestamp(DateUtil.formatYMD(year, month, day));

		/*
		 * obj.set(GregorianCalendar.DAY_OF_MONTH , day) ; out = new
		 * java.sql.Timestamp(obj.getTimeInMillis()); out =
		 * DateUtil.addDays(timestamp, 10) ; out =
		 * DateUtil.getFirstDayOfWeek(out);
		 */
		return out;
	}

	/*****************************************************
	 * 把java.sql.Timestamp Object 取出各部分的值
	 *****************************************************/
	/**
	 * 从java.sql.Timestamp Object 中取出Year value
	 * 
	 * @param timestamp java.sql.Timestamp Object
	 * @return year value
	 * @since 1.0
	 * @history
	 */
	public static int getYearOfTimestamp(Timestamp timestamp) {
		return DateUtil.convertTimestampToCalendar(timestamp).get(GregorianCalendar.YEAR);
	}

	/**
	 * 从java.sql.Timestamp Object 中取出month value
	 * 
	 * @param timestamp java.sql.Timestamp Object
	 * @return month value(1 -- 12 )
	 * @since 1.0
	 * @history
	 */
	public static int getMonthOfTimestamp(Timestamp timestamp) {
		return DateUtil.convertTimestampToCalendar(timestamp).get(GregorianCalendar.MONTH) + 1;
	}

	/**
	 * 从java.sql.Timestamp Object 中取出day-of-month value
	 * 
	 * @param timestamp java.sql.Timestamp Object
	 * @return day value
	 * @since 1.0
	 * @history
	 */
	public static int getDayOfTimestamp(Timestamp timestamp) {
		return DateUtil.convertTimestampToCalendar(timestamp).get(GregorianCalendar.DAY_OF_MONTH);
	}

	/**
	 * 从java.sql.Timestamp Object 中取出Weekday value
	 * 
	 * @param timestamp java.sql.Timestamp Object
	 * @return weekday value
	 * @since 1.0
	 * @history
	 */
	public static int getWeekdayOfTimestamp(Timestamp timestamp) {
		return DateUtil.convertTimestampToCalendar(timestamp).get(GregorianCalendar.DAY_OF_WEEK);
	}

	/**
	 * 返回当天零时的Timestamp值
	 * 
	 * @param timestamp java.sql.Timestamp Object
	 * @return Zero time Timestamp
	 * @since 1.0
	 * @history
	 */
	public static Timestamp getZeroTime(Timestamp timestamp) {
		return DateUtil.toSqlTimestamp(timestamp.toString().substring(0, 10));
	}

	/**
	 * 从java.sql.Timestamp Object 中取出hour:minute
	 * 
	 * @param timestamp java.sql.Timestamp Object
	 * @return Hour:Minute format string
	 * @since 1.0
	 * @history
	 */
	public static String getHourAndMinuteString(Timestamp timestamp) {
		String out = null;
		GregorianCalendar obj = DateUtil.convertTimestampToCalendar(timestamp);
		int hour = obj.get(GregorianCalendar.HOUR_OF_DAY);
		int minute = obj.get(GregorianCalendar.MINUTE);
		if (minute < 10) {
			out = String.valueOf(hour) + ":0" + String.valueOf(minute);
		} else {
			out = String.valueOf(hour) + ":" + String.valueOf(minute);
		}
		return out;
	}

	/**
	 * 从java.sql.Timestamp Object 中取出hour value
	 * 
	 * @param timestamp java.sql.Timestamp Object
	 * @return 24小时制的hour value
	 * @since 1.0
	 * @history
	 */
	public static int getHourOfTimestamp(Timestamp timestamp) {
		return DateUtil.convertTimestampToCalendar(timestamp).get(GregorianCalendar.HOUR_OF_DAY);
	}

	/**
	 * 从java.sql.Timestamp Object 中取出minute value
	 * 
	 * @param timestamp java.sql.Timestamp Object
	 * @return minute value
	 * @since 1.0
	 * @history
	 */
	public static int getMinuteOfTimestamp(Timestamp timestamp) {
		return DateUtil.convertTimestampToCalendar(timestamp).get(GregorianCalendar.MINUTE);
	}

	/**
	 * 从java.sql.Timestamp Object 中取出second value
	 * 
	 * @param timestamp java.sql.Timestamp Object
	 * @return minute value
	 * @since 1.0
	 * @history
	 */
	public static int getSecondOfTimestamp(Timestamp timestamp) {
		return DateUtil.convertTimestampToCalendar(timestamp).get(GregorianCalendar.SECOND);
	}

	/**
	 * 把java.sql.Timestamp Object 转换为java.util.GregorianCalendar Object
	 * 
	 * @param timestamp java.sql.Timestamp Object
	 * @return java.util.GregorianCalendar Object
	 * @since 1.0
	 * @history
	 * @deprecated please use confertToCalendar(java.sql.Timestamp)
	 */
	public static GregorianCalendar convertTimestampToCalendar(Timestamp timestamp) {
		return convertToCalendar(timestamp);
	}

	/**
	 * 把java.sql.Timestamp Object 转换为java.util.GregorianCalendar Object
	 * 
	 * @param timestamp java.sql.Timestamp Object
	 * @return java.util.GregorianCalendar Object
	 * @since 1.0
	 * @history
	 */
	public static GregorianCalendar convertToCalendar(Timestamp timestamp) {
		GregorianCalendar obj = new GregorianCalendar();
		// Modified by ChenJP 2000/11/17
		obj.setTime(DateUtil.convertTimestampToDate(timestamp));
		// 下面的method不能用，long ==>　int 精度不对
		// obj.set(GregorianCalendar.MILLISECOND , (int)timestamp.getTime() );
		return obj;
	}

	/**
	 * 把java.sql.Timestamp Object 转换为java.util.Date Object
	 * 
	 * @param timestamp java.sql.Timestamp Object
	 * @return java.util.Date Object
	 * @since 1.0
	 * @history
	 */
	public static java.util.Date convertTimestampToDate(Timestamp timestamp) {
		/*
		 * modified by ChenJP 2000/11/17 String temp = null; temp =
		 * timestamp.toString (); temp = temp.substring
		 * (0,DateUtil.DATE_FORMAT_DATETIME.length ()); temp = temp.replace
		 * ('-','/');
		 * 
		 * try{ date = DateUtil.sdfDateTime.parse (temp); }catch(Exception e){
		 * e.printStackTrace(); date = null; }
		 */
		return new Date(timestamp.getTime());
	}

	/*****************************************************
	 * 取系统日期、时间的函数
	 *****************************************************/
	/**
	 * 返回long型的SYSDATE
	 * 
	 * @return long型的SYSDATE
	 * @since 1.0
	 * @history
	 */
	public static long getSysDateLong() {
		return System.currentTimeMillis();
	}

	/**
	 * 返回java.sql.Timestamp型的SYSDATE
	 * 
	 * @return java.sql.Timestamp型的SYSDATE
	 * @since 1.0
	 * @history
	 */
	public static Timestamp getSysDateTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	/**
	 * 获取当前时间
	 * @return
	 */
	public static Date getSysSqlDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 以YYYY/MM/DD格式返回系统日期
	 * 
	 * @return 系统日期
	 * @since 1.0
	 * @history
	 */
	public static String getSysDateString() {
		return toString(new java.util.Date(System.currentTimeMillis()), DateUtil.sdfDateOnly);
	}

	/**
	 * 以YYYY-MM-DD格式返回系统日期
	 * 
	 * @return 系统日期
	 * @since 1.0
	 * @history
	 */
	public static String getOtherSysDateString() {
		return toString(new java.util.Date(System.currentTimeMillis()), DateUtil.sdfDateOnly);
	}

	/**
	 * 以YYYY/MM/DD HH24:MI:SS格式返回系统日期时间
	 * 
	 * @return 系统日期时间
	 * @since 1.0
	 * @history
	 */
	public static String getSysDateTimeString() {
		return toString(new java.util.Date(System.currentTimeMillis()), DateUtil.sdfDateTime);
	}

	/*****************************************************
	 * java.sql.Date ==> String 的转换函数
	 *****************************************************/
	/**
	 * 利用缺省的Date格式(YYYY/MM/DD)转化String到java.sql.Date
	 * 
	 * @param sDate Date string
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static Date toSqlDate(String sDate) {
		// return toSqlDate( sDate, this.DATE_FORMAT_DATEONLY );
		// java.sql.Date.value() 要求的格式必须为YYYY-MM-DD
		// System.out.println("sDate :"+sDate);
		if (sDate == null || sDate.equals("")) {
			return null;
		}
		if (sDate.equals("1899-12-30")) {
			return null;
		}
		return Date.valueOf(sDate.replace('/', '-'));
	}

	/*****************************************************
	 * String ==> java.sql.Date 的转换函数
	 *****************************************************/
	/**
	 * 转换java.sql.Date到String，g格式为YYYY/MM/DD
	 * 
	 * @param dt java.sql.Date instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static String toSqlDateString(Date dt) {
		return dt.toString().replace('-', '/');
	}

	/*****************************************************
	 * java.sql.Timestamp ==> String 的转换函数
	 *****************************************************/
	/**
	 * 转换GregorianCalendar Object到java.sql.Timestamp
	 * 
	 * @param gcal
	 *            GregorianCalendar Object
	 * @return java.sql.Timestamp object
	 * @since 1.0
	 * @history
	 */
	/*
	 * public static java.sql.Timestamp toSqlTimestamp(GregorianCalendar gcal){
	 * return new
	 * java.sql.Timestamp((long)gcal.get(GregorianCalendar.MILLISECOND) ); }
	 */
	/**
	 * 利用缺省的Date格式(YYYY/MM/DD)转换String到java.sql.Timestamp
	 * 
	 * @param sDate
	 *            Date string
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static Timestamp toSqlTimestamp(String sDate) {
		if (sDate == null) {
			return null;
		}
		if (sDate.length() != DateUtil.DATE_FORMAT_DATEONLY.length()) {
			return null;
		}
		return toSqlTimestamp(sDate, DateUtil.DATE_FORMAT_DATEONLY);
	}

	/**
	 * 利用缺省的Date格式(YYYY/MM/DD hh:mm:ss)转化String到java.sql.Timestamp
	 * 
	 * @param sDate Date string
	 * @param sFmt Date format DATE_FORMAT_DATEONLY/DATE_FORMAT_DATETIME
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static Timestamp toSqlTimestamp(String sDate, String sFmt) {
		String temp = null;
		if (sDate == null || sFmt == null) {
			return null;
		}
		if (sDate.length() != sFmt.length()) {
			return null;
		}
		if (sFmt.equals(DateUtil.DATE_FORMAT_DATETIME)) {
			temp = sDate.replace('/', '-');
			temp = temp + ".000000000";
		} else if (sFmt.equals(DateUtil.DATE_FORMAT_DATEONLY)) {
			temp = sDate.replace('/', '-');
			temp = temp + " 00:00:00.000000000";
			// }else if( sFmt.equals (DateUtil.DATE_FORMAT_SESSION )){
			// //Format: 200009301230
			// temp =
			// sDate.substring(0,4)+"-"+sDate.substring(4,6)+"-"+sDate.substring(6,8);
			// temp += " " + sDate.substring(8,10) + ":" +
			// sDate.substring(10,12) +
			// ":00.000000000";
		} else {
			return null;
		}
		// java.sql.Timestamp.value() 要求的格式必须为yyyy-mm-dd hh:mm:ss.fffffffff
		return Timestamp.valueOf(temp);
	}

	/*****************************************************
	 * String ==> java.sql.Date 的转换函数
	 *****************************************************/
	/**
	 * 转换java.sql.Timestamp到String，g格式为YYYY/MM/DD
	 * 
	 * @param dt java.sql.Timestamp instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static String toSqlTimestampString(Timestamp dt) {
		if (dt == null) {
			return null;
		}
		return toSqlTimestampString(dt, DateUtil.DATE_FORMAT_DATEONLY);
	}

	/**
	 * 转换java.sql.Timestamp到String，格式为YYYY/MM/DD HH24:MI
	 * 
	 * @param dt java.sql.Timestamp instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static String toSqlTimestampString2(Timestamp dt) {
		if (dt == null) {
			return null;
		}
		return toSqlTimestampString(dt, DateUtil.DATE_FORMAT_DATETIME).substring(0, 16);
	}

	public static String toString(Timestamp dt) {
		return dt == null ? "" : toSqlTimestampString2(dt);
	}

	/**
	 * 将指定的java.sql.Timestamp类型的转变为指定的中文日期格式
	 */
	public static String convertTimestampToChinaCalendar(Timestamp timestamp) {
		if (timestamp == null) {
			return "&nbsp";
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(DateUtil.getYearOfTimestamp(timestamp));
		sb.append("年");
		sb.append(DateUtil.getMonthOfTimestamp(timestamp));
		sb.append("月");
		sb.append(DateUtil.getDayOfTimestamp(timestamp));
		sb.append("日");
		sb.append(" ");
		sb.append(DateUtil.getHourOfTimestamp(timestamp));
		sb.append(":");
		if (DateUtil.getMinuteOfTimestamp(timestamp) < 10) {
			sb.append(0);
			sb.append(DateUtil.getMinuteOfTimestamp(timestamp));
		} else {
			sb.append(DateUtil.getMinuteOfTimestamp(timestamp));
		}
		sb.append(":");
		if (DateUtil.getSecondOfTimestamp(timestamp) < 10) {
			sb.append(0);
			sb.append(DateUtil.getSecondOfTimestamp(timestamp));
		} else {
			sb.append(DateUtil.getSecondOfTimestamp(timestamp));
		}
		return sb.toString();
	}

	/**
	 * 根据指定的格式转换java.sql.Timestamp到String
	 * 
	 * @param dt java.sql.Timestamp instance
	 * @param sFmt Date格式，DATE_FORMAT_DATEONLY/DATE_FORMAT_DATETIME/DATE_FORMAT_SESSION
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static String toSqlTimestampString(Timestamp dt, String sFmt) {
		if (dt == null || sFmt == null) {
			return null;
		}
		String temp = dt.toString();
		if (sFmt.equals(DateUtil.DATE_FORMAT_DATETIME) || sFmt.equals(DateUtil.DATE_FORMAT_DATEONLY)) {
			temp = temp.substring(0, sFmt.length());
			// out = temp.replace('-', '/');
			// }else if( sFmt.equals (DateUtil.DATE_FORMAT_SESSION ) ){
			// //Session
			// out =
			// temp.substring(0,4)+temp.substring(5,7)+temp.substring(8,10);
			// out += temp.substring(12,14) + temp.substring(15,17);
		}
		return temp;
	}

	/**
	 * 转换java.sql.Timestamp到HH24:MI String
	 * 
	 * @param dt java.sql.Timestamp instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static String toHourMinString(Timestamp dt) {
		return dt.toString().substring(11, 16);
	}

	/*****************************************************
	 * java.sql.Timestamp +/- 几天的计算函数
	 *****************************************************/
	/**
	 * 判断指定的日期是否是一个月的最后一天
	 * @param obj GregorianCalendar object
	 */
	private static boolean isLastDayOfMonth(GregorianCalendar obj) {
		int year = obj.get(GregorianCalendar.YEAR);
		int month = obj.get(GregorianCalendar.MONTH) + 1;
		int day = obj.get(GregorianCalendar.DAY_OF_MONTH);
		if (obj.isLeapYear(year)) {
			if (day == DateUtil.DAY_OF_MONTH_LEAP_YEAR[month - 1]) {
				return true;
			}
		} else {
			if (day == DateUtil.DAY_OF_MONTH_NON_LEAP_YEAR[month - 1]) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 加月
	 * @param date
	 * @param mon
	 * @return
	 */
	public static java.util.Date addMonths(java.util.Date date, int mon) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = toString(date);
		try {
			java.util.Date now = sdf.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.MONTH, mon);
			dateStr = sdf.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return toDate(dateStr);
	}
	
	/**
	 * 在java.sql.Timestamp Object上增加/减少几个月
	 * 
	 * @param timestamp java.sql.Timestamp instance
	 * @param mon 增加/减少的月数
	 * @return java.sql.Timestamp Object
	 * @since 1.0
	 * @history
	 */
	public static Timestamp addMonths(Timestamp timestamp, int mon) {
		GregorianCalendar obj = DateUtil.convertTimestampToCalendar(timestamp);
		// month : 0 -- 11
		int year = obj.get(GregorianCalendar.YEAR);
		int month = obj.get(GregorianCalendar.MONTH) + 1;
		int day = obj.get(GregorianCalendar.DAY_OF_MONTH);
		month += mon;

		while (month < 1) {
			month += 12;
			year--;
		}

		while (month > 12) {
			month -= 12;
			year++;
		}

		if (isLastDayOfMonth(obj)) {
			if (obj.isLeapYear(year)) {
				day = DateUtil.DAY_OF_MONTH_LEAP_YEAR[month - 1];
			} else {
				day = DateUtil.DAY_OF_MONTH_NON_LEAP_YEAR[month - 1];
			}
		}
		return DateUtil.toSqlTimestamp(DateUtil.formatYMD(year, month, day));
	}

	/**
	 * 在java.sql.Timestamp Object上增加/减少几天
	 * 
	 * @param timestamp java.sql.Timestamp instance
	 * @param days 增加/减少的天数
	 * @return java.sql.Timestamp Object
	 * @since 1.0
	 * @history
	 */
	public static Timestamp addDays(Timestamp timestamp, int days) {
		return new Timestamp(DateUtil.convertTimestampToDate(timestamp).getTime() + DateUtil.DAY_MILLI * days);
	}

	/**
	 * 增加小时
	 * @param timestamp
	 * @param hour
	 * @return
	 */
	public static Timestamp addHour(Timestamp timestamp, int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.convertTimestampToDate(timestamp));
		cal.add(Calendar.HOUR_OF_DAY, 1);// 24小时制
		return new Timestamp(cal.getTime().getTime());
	}

	/**
	 * 增加小时
	 * 
	 * @param date
	 * @param hour
	 * @return
	 */
	public static java.util.Date addHour(java.util.Date date, int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, 1);// 24小时制
		return cal.getTime();
	}

	/**
	 * 在java.util.DateObject上增加/减少几天
	 * 
	 * @param date java.util.Date instance
	 * @param days 增加/减少的天数
	 * @return java.util.Date Object
	 * @since 1.0
	 * @history
	 */
	public static java.util.Date addDays(java.util.Date date, int days) {
		return new java.util.Date(date.getTime() + DateUtil.DAY_MILLI * days);
	}

	/**
	 * 根据生日得到年龄
	 * 
	 * @param birthday
	 *            String 生日
	 * @return int 年龄
	 * @since 1.0
	 * @history
	 */
	public static int getAge(String birthday) {
		if (birthday == null || birthday.equals("")) {
			return 0;
		}
		
		return Integer.parseInt(getSysDateString().substring(0, 4)) - Integer.parseInt(birthday.substring(0, 4));
	}

	/**
	 * 判断是否过期
	 */
	public static boolean isBefore(String date) {
		return new java.util.Date(System.currentTimeMillis()).before(new java.util.Date(date));
	}
	
	public static boolean isAfter(java.util.Date date) {
		return new java.util.Date(System.currentTimeMillis()).after(date);
	}

	/**
	 * 判断日期一是否早于日期二
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isBefore(String date1, String date2) {
		date1 = getDateString(date1);
		date2 = getDateString(date2);
		if (date1 == null || date1.equals("")) {
			return false;
		}
		if (date1.indexOf("-") > -1) {
			date1 = date1.replaceAll("-", "/");
		}
		if (date2.indexOf("-") > -1) {
			date2 = date2.replaceAll("-", "/");
		}
		return new java.util.Date(date1).before(new java.util.Date(date2));
	}

	/**
	 * 判断日期一是否后于日期二
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isAfter(String date1, String date2) {
		date1 = getDateString(date1);
		date2 = getDateString(date2);
		if (date1 == null || date1.equals("")) {
			return false;
		}
		if (date1.indexOf("-") > -1) {
			date1 = date1.replaceAll("-", "/");
		}
		if (date2.indexOf("-") > -1) {
			date2 = date2.replaceAll("-", "/");
		}
		return new java.util.Date(date1).after(new java.util.Date(date2));
	}

	public static String getSystemWeekdayString() {
		int day = getWeekdayOfTimestamp(new Timestamp(System.currentTimeMillis()));
		if (day == 1)
			return ("星期日");
		if (day == 2)
			return ("星期一");
		if (day == 3)
			return ("星期二");
		if (day == 4)
			return ("星期三");
		if (day == 5)
			return ("星期四");
		if (day == 6)
			return ("星期五");
		if (day == 7)
			return ("星期六");
		return "";
	}


	/**
	 * 处理sqlserver数据库中的日期 如果为null。或为1900－1－1均返回空
	 * @param date
	 */
	public static String getNotNullDate(String date) {
		if (date == null || date.startsWith("1900") || date.equals("")) {
			return "";
		} else {
			return date.substring(0, 10);
		}
	}

	/**
	 * 获取一个时间的所在年 hongzhi
	 */
	public static int getYearOfDate(java.util.Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获系统时间 yyyy年MM月dd日 主要用于页面显示 cb4u
	 */
	public static String getNowDate() {
		return new SimpleDateFormat("yyyy年MM月dd日").format(new java.util.Date(System.currentTimeMillis()));
	}

	/**
	 * 获系统日期（时间） cb4u
	 */
	public static String getNowDate(String dateFormate) {
		return new SimpleDateFormat(dateFormate).format(new java.util.Date());
	}

	/**
	 * 过滤mysql数据中的空数据
	 * 
	 * @param date
	 * @return
	 */
	public static String mysqlNullDateFilter(String date) {
		if (date == null || "".equals(date)) {
			date = "1900-01-01";
		}
		return date;
	}

	public static String mysqlNullDateFilter(Date date) {
		if (date == null) {
			return "1900-01-01";
		}
		return date.toString();
	}

	/**
	 * 获取当前时间的前n个月时间（施龙飞）
	 * 
	 * @return
	 */
	public static long getSysDateBeforeMonth(int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -n);
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取前n个月的第一天日期（施龙飞）
	 * 
	 * @return
	 */
	public static Timestamp getFirstDayOfBeforeMonth(int n) {
		return getFirstDayOfMonth(new Timestamp(getSysDateBeforeMonth(n)));
	}

	/**
	 * 获取前n个月的最后一天日期（张秀伟）
	 * 
	 * @return
	 */
	public static Timestamp getLastDayOfBeforeMonth(int n) {
		return getLastDayOfMonth(new Timestamp(getSysDateBeforeMonth(n)));
	}

	/**
	 * 将数据库中取出的日期字符串格式化 1、只要日期，不要时间 2、如果时1900年，说明日期为空
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateString(String date) {
		if (date == null) {
			return "";
		}
		if (date != null && date.length() > 10) {
			date = date.substring(0, 10);
		}
		if (date.startsWith("1900") || date.startsWith("0000")) {
			date = "";
		}
		return date;
	}

	/**
	 * 获取当前日期的前一月
	 * 
	 * @return
	 */
	public static String getCurDateMinusMonth() {
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.MONTH, -1);

		return new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime());
	}
	
	/**
	 * 获取当前年度
	 * @return
	 */
	public static int getCurYear(){
		Calendar ca = Calendar.getInstance();
		return ca.get(Calendar.YEAR);
	}
	
	/**
	 * 获取当前月
	 * @return
	 */
	public static int getCurMonth(){
		Calendar ca = Calendar.getInstance();
		return ca.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 获取年度的第一天
	 * @param year
	 * @return
	 */
	public static java.util.Date getFirstDayYear(String year){
		String date = year+"-01"+"-01";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取年度的最后一天
	 * @param year
	 * @return
	 */
	public static java.util.Date getLastDayYear(String year){
		String date = year+"-12"+"-31";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将Timestamp类型的日期转换为字符串
	 * @param date
	 * @return
	 */
	public static String formatTimestampToString(Timestamp date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 获取string类型的yyMMdd格式的日期
	 * @return
	 */
	public static String getCurrentDateString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date(System.currentTimeMillis()));
	}
	
	/**
	 * 获取string类型的MM-dd格式的日期
	 * @return
	 */
	public static String getNowMonthDay(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		return sdf.format(new Date(System.currentTimeMillis()));
	}
	
	/**
	 * 获取string类型的MM-dd格式的日期
	 * @return
	 */
	public static String getTimestampMonthDay(Timestamp date){
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		return sdf.format(date);
	}
	
	/**
	 * 获取string类型的MM-dd格式的日期
	 * @return
	 */
	public static String getDateMonthDay(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		return sdf.format(date);
	}
	
	/**
	 * 获取最近时间，以 几小时前 几分钟前 刚刚 几天前 等字眼体现
	 * @param date
	 * @return
	 */
	public static String getRecentlyDate(Timestamp date){
		if(null == date){
			return "";
		}
		Long now  = System.currentTimeMillis();
		Long recentlyLong = (now-date.getTime())/(1000*60*60*24);
		String suffix;
		if(recentlyLong==0){
			recentlyLong = (now-date.getTime())/(1000*60*60);
			suffix="小时前";
			if(recentlyLong==0){
				recentlyLong = (now-date.getTime())/(1000*60);
				suffix="分钟前";
				if(recentlyLong==0){
					suffix="刚刚";
				}
			}
		}else{
			suffix="天前";
		}
		return (recentlyLong==0?"":recentlyLong)+suffix;
	}
	
	public static void main(String[] args) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
      //  String tsStr = "2018-06-01 18:17:59";
        try {
           // ts = Timestamp.valueOf(tsStr);
            System.out.println(getRecentlyDate(ts));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}