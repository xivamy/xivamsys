package com.xiva.common.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * @author XIVA
 * 
 */
public class DateUtil
{
    public final static String DATE_FORMAT_1 = "yyyy-MM-dd";

    public final static String DATE_FORMAT_2 = "yyyy-MM-dd HH:mm:ss";

    public final static String DATE_FORMAT_3 = "yy-MM-dd";

    public final static String DATE_FORMAT_4 = "$yyyy_MM_dd";

    public final static String DATE_FORMAT_5 = "yyyyMMddHHmmss";

    public final static String TIME_FORMAT = "HH:mm";

    public static java.sql.Date revertDate(java.util.Date dtIn)
    {
        if (dtIn == null)
            return null;
        else
            return new java.sql.Date(dtIn.getTime());
    }

    public static Date getCurrent()
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date getCurrentTime()
    {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    public static Date parse(String str)
    {
        if (str == null)
            return null;
        Date dt = null;
        Calendar minCal = Calendar.getInstance();
        minCal.set(1900, 0, 1);
        Calendar maxCal = Calendar.getInstance();
        maxCal.set(2200, 0, 1);
        try
        {
            dt = DateUtils.parseDate(str, new String[] { DATE_FORMAT_1, DATE_FORMAT_2, DATE_FORMAT_3, DATE_FORMAT_4, DATE_FORMAT_5 });
            if (minCal.getTime().compareTo(dt) > 0 || maxCal.getTime().compareTo(dt) < 0)
            {
                dt = null;
            }
        }
        catch (ParseException pe)
        {
            dt = null;
        }
        catch (IllegalArgumentException iae)
        {
            dt = null;
        }

        return dt;
    }

    public static Date parseDateTime(String str)
    {
        Date dt = null;
        try
        {
            dt = DateUtils.parseDate(str, new String[] { DATE_FORMAT_2 });
        }
        catch (ParseException pe)
        {
            dt = null;
        }
        catch (IllegalArgumentException iae)
        {
            dt = null;
        }

        return dt;
    }

    public static Date parseTime(String str)
    {
        Date dt = null;
        try
        {
            dt = DateUtils.parseDate(str, new String[] { TIME_FORMAT });
        }
        catch (ParseException pe)
        {
            dt = null;
        }
        catch (IllegalArgumentException iae)
        {
            dt = null;
        }

        return dt;
    }

    public static String format(Date dt)
    {
        if (dt == null)
            return "";
        String str = DateFormatUtils.format(dt, DATE_FORMAT_1);
        return str;
    }

    public static String formatDate(Date dt)
    {
        if (dt == null)
            return "";
        String str = DateFormatUtils.format(dt, DATE_FORMAT_4);
        return str;
    }

    public static String format(Date dt, String strFormat, Locale locale)
    {
        if (dt == null)
            return "";
        String str = DateFormatUtils.format(dt, strFormat, locale);
        return str;
    }

    public static String formatDateTime(Date dt)
    {
        if (dt == null)
            return "";
        String str = DateFormatUtils.format(dt, DATE_FORMAT_2);
        return str;
    }

    public static String formatTime(Date dt)
    {
        String str = DateFormatUtils.format(dt, TIME_FORMAT);
        return str;
    }

    public static String getDayEnd(Date dtDate)
    {

        String strDayEnd = "";
        if (dtDate == null)
            dtDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dtDate);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        strDayEnd = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) > 8 ? "" : "0") + (cal.get(Calendar.MONTH) + 1) + "-"
                + cal.get(Calendar.DAY_OF_MONTH) + "_" + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":"
                + cal.get(Calendar.SECOND);
        return strDayEnd;
    }

    /**
     * 返回年份
     * 
     * @param date
     *            日期
     * @return 返回年份
     */
    public static int getYear(java.util.Date date)
    {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.YEAR);
    }

    /**
     * 返回月份
     * 
     * @param date
     *            日期
     * @return 返回月份
     */
    public static int getMonth(java.util.Date date)
    {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.MONTH) + 1;
    }

    /**
     * 返回日份
     * 
     * @param date
     *            日期
     * @return 返回日份
     */
    public static int getDay(java.util.Date date)
    {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回小时
     * 
     * @param date
     *            日期
     * @return 返回小时
     */
    public static int getHour(java.util.Date date)
    {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.HOUR_OF_DAY);
    }

    /**
     * 返回分钟
     * 
     * @param date
     *            日期
     * @return 返回分钟
     */
    public static int getMinute(java.util.Date date)
    {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     * 
     * @param date
     *            日期
     * @return 返回秒钟
     */
    public static int getSecond(java.util.Date date)
    {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.SECOND);
    }

    /**
     * 返回毫秒
     * 
     * @param date
     *            日期
     * @return 返回毫秒
     */
    public static long getMillis(java.util.Date date)
    {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * 日期相加
     * 
     * @param date
     *            日期
     * @param day
     *            天数
     * @return 返回相加后的日期
     */
    public static java.util.Date addDate(java.util.Date date, int day)
    {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
        return c.getTime();
    }

    /**
     * 日期相减
     * 
     * @param date
     *            日期
     * @param date1
     *            日期
     * @return 返回相减后的日期
     */
    public static int diffDate(java.util.Date date, java.util.Date date1)
    {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    public static java.sql.Date parseToSqlDate(String str)
    {
        Date dt = parse(str);

        if (dt != null)
            return util2sqlDate(dt);
        else
            return null;
    }

    public static Time parseToSqlTime(String str)
    {
        Date dt = parseTime(str);

        if (dt != null)
            return util2sqlTime(dt);
        else
            return null;
    }

    public static Timestamp parseToSqlTimestamp(String str)
    {
        Date dt = parse(str);
        if (dt != null)
            return util2sqlTimestamp(dt);
        else
            return null;
    }

    public static java.sql.Date util2sqlDate(Date dt)
    {
        return new java.sql.Date(dt.getTime());
    }

    public static Timestamp util2sqlTimestamp(Date dt)
    {
        return new Timestamp(dt.getTime());
    }

    public static Time util2sqlTime(Date dt)
    {
        return new Time(dt.getTime());
    }

    /**
     * 只取年月日，不取时分秒
     * 
     */
    public static Timestamp util2sqlTimestampDateOnly(Date dt)
    {
        String strObj = formatDate(dt);
        return parseToSqlTimestamp(strObj);
    }

    public static void main(String[] argc)
    {
        Date d1 = DateUtil.parse("2007-6-25 1:0:0");
        Date d2 = DateUtil.parse("2007-7-1 2:0:0");
        System.out.println(DateUtil.diffDate(d2, d1));
        // Locale sysLocale = Locale.getDefault();
        // SimpleDateFormat dateFormater = new SimpleDateFormat("HH:mm",
        // sysLocale);
        // try
        // {
        // Date dt = dateFormater.parse("08:11");
        // Time t = util2sqlTime(dt);
        // System.out.println(t);
        // } catch (ParseException e)
        // {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        //    
        //    
        // try
        // {
        // Date dt1 = DateUtils.parseDate("8:11",new
        // String[]{"yyyy-MM-dd_HH:mm:ss","HH:mm"});
        // Time t1 = util2sqlTime(dt1);
        // System.out.println(t1);
        // } catch (ParseException e)
        // {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

        // System.out.println(parseToSqlTime("08:01"));

    }

    /**
     * 由java.util.Date到java.sql.Date的类型转换
     * 
     * @param date
     * @return Date
     */
    public static Date getSqlDate(java.util.Date date)
    {
        return new Date(date.getTime());
    }

    public static Date nowDate()
    {
        Calendar calendar = Calendar.getInstance();
        return getSqlDate(calendar.getTime());
    }

    /**
     * 获得某一日期的后一天
     * 
     * @param date
     * @return Date
     */
    public static Date getNextDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day + 1);
        return getSqlDate(calendar.getTime());
    }

    /**
     * 获得某一日期的前一天
     * 
     * @param date
     * @return Date
     */
    public static Date getPreviousDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day - 1);
        return getSqlDate(calendar.getTime());
    }

    /**
     * 获得某年某月第一天的日期
     * 
     * @param year
     * @param month
     * @return Date
     */
    public static Date getFirstDayOfMonth(int year, int month)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        return getSqlDate(calendar.getTime());
    }

    /**
     * 获得某年某月最后一天的日期
     * 
     * @param year
     * @param month
     * @return Date
     */
    public static Date getLastDayOfMonth(int year, int month)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1);
        return getPreviousDate(getSqlDate(calendar.getTime()));
    }

    /**
     * 由年月日构建java.sql.Date类型
     * 
     * @param year
     * @param month
     * @param date
     * @return Date
     */
    public static Date buildDate(int year, int month, int date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date);
        return getSqlDate(calendar.getTime());
    }

    /**
     * 取得某月的天数
     * 
     * @param year
     * @param month
     * @return int
     */
    public static int getDayCountOfMonth(int year, int month)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 0);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获得某年某季度的最后一天的日期
     * 
     * @param year
     * @param quarter
     * @return Date
     */
    public static Date getLastDayOfQuarter(int year, int quarter)
    {
        int month = 0;
        if (quarter > 4)
        {
            return null;
        }
        else
        {
            month = quarter * 3;
        }
        return getLastDayOfMonth(year, month);

    }

    /**
     * 获得某年某季度的第一天的日期
     * 
     * @param year
     * @param quarter
     * @return Date
     */
    public static Date getFirstDayOfQuarter(int year, int quarter)
    {
        int month = 0;
        if (quarter > 4)
        {
            return null;
        }
        else
        {
            month = (quarter - 1) * 3 + 1;
        }
        return getFirstDayOfMonth(year, month);
    }

    /**
     * 获得某年的第一天的日期
     * 
     * @param year
     * @return Date
     */
    public static Date getFirstDayOfYear(int year)
    {
        return getFirstDayOfMonth(year, 1);
    }

    /**
     * 获得某年的最后一天的日期
     * 
     * @param year
     * @return Date
     */
    public static Date getLastDayOfYear(int year)
    {
        return getLastDayOfMonth(year, 12);
    }

    /** 当前时间Timestamp **/
    public static Timestamp getNowTimeStamp()
    {
        Date date = new Date();
        Timestamp datenow = new Timestamp(date.getTime());
        return datenow;
    }

    /********** 根据年份获得年底时间（yyyy-MM-dd HH:mm:ss） ***********/
    public static Date getLastYearTime(String year)
    {
        String s = year + "-12-31 23:59:59";
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = new Date();
        try
        {
            dt = formater.parse(s);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return dt;
    }

    /******** 返回月份所在季度 ********/
    public static int getQuarterByMonth(int month)
    {
        int quarter;
        if ((month % 3) > 0)
        {
            quarter = (month / 3) + 1;
        }
        else
        {
            quarter = month / 3;
        }
        return quarter;
    }

    /******* 返回两个时间的组成时间（取第一个时间的date+第二个时间的time） **********/
    public static Date getDateAndTime(Date date1, Date date2)
    {
        String s1 = format(date1) + " " + formatTime(date2) + ":00";
        return parseDateTime(s1);
    }
}
