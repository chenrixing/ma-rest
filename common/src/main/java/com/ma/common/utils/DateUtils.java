package com.ma.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

/* *
 *类名：UtilDate
 *功能：自定义订单类
 *详细：工具类，可以用作获取系统日期、订单编号等
 *版本：3.3
 *日期：2018-05-28
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
public class DateUtils {

    /** 年月日时分秒(无下划线) yyyyMMddHHmmss */
    public static final String dtLong = "yyyyMMddHHmmss";

    /** 完整时间 yyyy-MM-dd HH:mm:ss */
    public static final String simple = "yyyy-MM-dd HH:mm:ss";

    /** 年月日(无下划线) yyyyMMdd */
    public static final String dtShort = "yyyyMMdd";

    /** 年月日(有下划线) yyyyMMdd */
    public static final String dtMiddle = "yyyy-MM-dd";
 
    /**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
     * 
     * @return 以yyyyMMddHHmmss为格式的当前系统时间
     */
    public static String getOrderNum() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(dtLong);
        return df.format(date);
    }

    /**
     * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
     * 
     * @return
     */
    public static String getDateFormatter() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(simple);
        return df.format(date);
    }

    /**
     * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
     * 
     * @return
     */
    public static Date getDateFormatter(String dateFormate) throws ParseException {
        DateFormat df = new SimpleDateFormat(simple);
        return df.parse(dateFormate);
    }

    /**
     * 获取系统当期年月日(精确到天)，格式：yyyyMMdd
     * 
     * @return
     */
    public static String getDate() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(dtShort);
        return df.format(date);
    }

    /**
     * 获取系统当期前一天年月日(精确到天)，格式：yyyy-MM-dd
     * 
     * @return
     */
    public static String getDateMiddle() {
        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(new Date());// 把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
        Date dBefore = calendar.getTime(); // 得到前一天的时间
        DateFormat df = new SimpleDateFormat(dtMiddle);
        return df.format(dBefore);
    }

    /**
     * 产生随机的三位数
     * 
     * @return
     */
    public static String getThree() {
        Random rad = new Random();
        return rad.nextInt(1000) + "";
    }

    /**
     * 获取当前时间的上个月时间
     * 
     * @return
     */
    public static Date getLastMonthTime() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        return c.getTime();
    }

    /**
     * 获得当天的23:59:59秒的时间
     * 
     * @auth:李光明
     * @date:2018年05月28日
     * @return Long
     */
    public static Long getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        return todayEnd.getTime().getTime();
    }

    /**
     * 获得当天的23:59:59秒的时间到当前时间的差
     * 
     * @auth:李光明
     * @date:2018年05月28日
     * @return int
     */
    public static int getExpire() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        return (int) (todayEnd.getTime().getTime() - System.currentTimeMillis()) / 1000;
    }
    
    /**
     * 获得当前时间到三小时的差
     * 
     * @auth:李光明
     * @date:2018年05月28日
     * @return int
     */
    public static int getThreeHour() {
        return 3600*3;
    }

    /**
     * 验证当前时间是否大于开始时间
     * 
     * @auth:李光明
     * @date:2018年05月28日
     * @param beforeDate
     * @return boolean
     */
    public static boolean beforeDate(String beforeDate) {
        if (StringUtils.isBlank(beforeDate)) {
            return false;
        }
        if (System.currentTimeMillis() > getTime(beforeDate)) {
            return false;
        }
        return true;
    }

    /**
     * 将字符串转为时间戳
     * 
     * @auth:李光明
     * @date:2018年05月28日
     * @param UserInfo_time
     * @return long
     */
    private static long getTime(String UserInfo_time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(UserInfo_time);
            long l = d.getTime();
            return l;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 
     *
     * @param monthNum
     *            有效月数
     * @return 有效日期
     */
    public static Date addMonths(Date currentDate, int monthNum) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MONTH, monthNum);
        return cal.getTime();
    }

    /**
     * 功能：当前时间增加天数。
     * 
     * @param days
     *            正值时时间延后，负值时时间提前。
     * @return Date
     */
    public static Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, c.get(Calendar.DATE) + days);
        return new Date(c.getTimeInMillis());
    }

    private static ThreadLocal<Map<String, SimpleDateFormat>> dfThreadLocal = new ThreadLocal<Map<String, SimpleDateFormat>>() {
        protected synchronized Map<String, SimpleDateFormat> initialValue() {
            SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat datetimeNumFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            Map<String, SimpleDateFormat> map = new HashMap<String, SimpleDateFormat>();
            map.put("datetime", datetimeFormat);
            map.put("date", dateFormat);
            map.put("time", timeFormat);
            map.put("datetimeNum", datetimeNumFormat);
            return map;
        }
    };

    private static SimpleDateFormat getDatetimeDF() {
        return dfThreadLocal.get().get("datetime");
    }

    /**
     * 将字符串日期时间转换成java.util.Date类型
     * <p>
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     * 
     * @param datetime
     * @return
     * @throws ParseException
     */
    public static Date parseDatetime(String datetime) throws ParseException {
        return getDatetimeDF().parse(datetime);
    }

    /**
     * 比较日期大小
     * 
     * @param strdate1
     * @param strdate2
     * @return
     */
    public static boolean compareDate(String strdate1, String strdate2, String dateFromat) {
        boolean bflag = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
            Date date1 = sdf.parse(strdate1);
            Date date2 = sdf.parse(strdate2);
            bflag = date1.after(date2);
            if (date1.equals(date2)) {
                bflag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bflag;
    }

    /**
     * 日期转字符串
     * 
     * @param date
     * @return
     */
    public static String DateToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    /**
     * 把日期转化成字符串日期
     * 
     * @param dateFormat
     *            (日期格式，例如：MM/ dd/yyyy HH:mm:ss)
     * @param
     *            日期
     * @return
     */
    public static String transferDateToString(String dateFormat, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    /**
     * 返回当前日期（yyyy-MM-dd）
     * 
     * @auth:李光明
     * @date:2018年3月27日
     * @return String
     */
    public static String getStringDateDay() {
        SimpleDateFormat sdf = new SimpleDateFormat(dtMiddle);
        return sdf.format(new Date());
    }

    /**
     * 获取上周第一天
     * 
     * @auth:李光明
     * @date:2018年6月26日
     * @return String
     */
    public static String getLastWeekFirstDay() {
        SimpleDateFormat sdf = new SimpleDateFormat(dtMiddle); // 设置时间格式
        String imptimeBegin = sdf.format(getCalendar().getTime());
        return imptimeBegin + " 00:00:00";
    }

    /**
     * 查询上周最后一天的日期
     * 
     * @auth:李光明
     * @date:2018年6月26日
     * @return String
     */
    public static String getLastWeekEndDay() {
        SimpleDateFormat sdf = new SimpleDateFormat(dtMiddle); // 设置时间格式
        Calendar calendar = getCalendar();
        calendar.add(Calendar.DATE, 6);
        String imptimeBegin = sdf.format(calendar.getTime());
        return imptimeBegin + " 23:59:59";
    }

    private static Calendar getCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -7); // 设置为前一天
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        return cal;
    }
    /**
     * 获取上个月第一天
     *@auth:李光明
     *@date:2018年05月28日
     *@return String
     */
    public static String getLastMonthFirstDay(){
        SimpleDateFormat sdf = new SimpleDateFormat(dtMiddle); // 设置时间格式
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String imptimeBegin = sdf.format(calendar.getTime());
        return imptimeBegin + " 00:00:00";
    }
    /**
     * 获取上个月最后一天
     *@auth:李光明
     *@date:2018年6月19日
     *@return String
     */
    public static String getLastMonthEndDay(){
        SimpleDateFormat sdf = new SimpleDateFormat(dtMiddle); // 设置时间格式
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        String imptimeBegin = sdf.format(calendar.getTime());
        return imptimeBegin + " 23:59:59";
    }

    /**
     * 获取后天的日期
     * 
     * @param date 返回该日期后天的日期。可以为null，视作以当前时间开始推算
     * @param keepTime 是否保留返回的后天日期的时间部分。如置为false，时、分、秒、毫秒都置为0
     * @return Date实例。是date参数两天后的日期
     */
    public static Date getDayAfterTomorrow(Date date, boolean keepTime) {
        Calendar cal = Calendar.getInstance();
        if (date != null)
            cal.setTime(date);
        cal.add(Calendar.DATE, 2);
        if (!keepTime) {
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        }
        return cal.getTime();
    }

}
