package com.smartwifi.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/10
 * @Describe
 */

public class DateUtils {
    /**
     * 时间日期格式化到年月日时分秒.
     */
    public static String dateFormatYMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static String dateFormatYMDHMS_f = "yyyyMMddHHmmss";
    public static String dateFormatMDHM = "MM-dd HH:mm";
    public static String dateFormat = "yyyy-MM-dd HH:mm";
    public static String dateFormatYear = "yyyy";
    /**
     * 时间日期格式化到年月日.
     */
    public static String dateFormatYMD = "yyyy-MM-dd";

    /**
     * 时间日期格式化到年月日时分.中文显示
     */
    public static String dateFormatYMDHMofChinese = "yyyy年MM月dd日 HH:mm";

    /**
     * 时间日期格式化到年月日.中文显示
     */
    public static String dateFormatYMDofChinese = "yyyy年MM月dd日";
    /**
     * 时间日期格式化到月日.中文显示
     */
    public static String dateFormatMDofChinese = "MM月dd日";
    /**
     * 时间日期格式化到月.中文显示
     */
    public static String dateFormatMofChinese = "MM月";
    /**
     * 时间日期格式化到年月.
     */
    public static String dateFormatYM = "yyyy-MM";

    /**
     * 时间日期格式化到年月日时分.
     */
    public static String dateFormatYMDHM = "yyyy-MM-dd HH:mm";

    /**
     * 时间日期格式化到月日.
     */
    public static String dateFormatMD = "MM/dd";
    public static String dateFormatM_D = "MM-dd";

    public static String dateFormatM = "MM月";
    public static String dateFormatD = "dd";
    public static String dateFormatM2 = "MM";

    public static String dateFormatMDHMofChinese = "MM月dd日HH时mm分";
    public static String dateFormatHMofChinese = "HH时mm分";

    /**
     * 时分秒.
     */
    public static String dateFormatHMS = "HH:mm:ss";

    /**
     * 时分.
     */
    public static String dateFormatHM = "HH:mm";


    /**
     * 上午/下午时分
     */
    public static String dateFormatAHM = "aHH:mm";

    public static String dateFormatYMDE = "yyyy/MM/dd E";
    public static String dateFormatYMD2 = "yyyy/MM/dd";

    public static String dateToStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    public static String getStringMilliSecondTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-ddHHmmss", Locale.getDefault());
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String formatData(String dataFormat, long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        return format.format(new Date(timeStamp));
    }

    public static String formatData(String dataFormat, String timeStamp) {
        if (TextUtils.isEmpty(timeStamp)) {
            return "";
        }

        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        return format.format(new Date(timeStamp));
    }

    //获取当前系统前后第几天
    public static String getNextDay(int i, String dateFormat) {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormat);
            Calendar c = new GregorianCalendar();
            c.add(Calendar.DAY_OF_MONTH, i);
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;
    }

    public static int getWeekOfYear(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        Date date = new Date(time);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);

    }

    public static String getWeekOfYearString(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        Date date = new Date(time);
        calendar.setTime(date);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        if (week < 10) {
            return "0" + week;
        }
        return week + "";

    }

    public static String getYearWeekString(long time) {
        return DateUtils.getNowYear(time) + DateUtils.getWeekOfYearString(time);

    }

    /**
     * 描述：获取本周一.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getFirstDayOfWeek(String format) {
        return getDayOfWeek(format, Calendar.MONDAY);
    }

    /**
     * 描述：获取本周五.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getFridayOfWeek(String format) {
        return getDayOfWeek(format, Calendar.FRIDAY);
    }

    private static String getDayOfWeek(String format, int calendarField) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            int week = c.get(Calendar.DAY_OF_WEEK);
            if (week == calendarField) {
                strDate = mSimpleDateFormat.format(c.getTime());
            } else {
                int offectDay = calendarField - week;
                if (calendarField == Calendar.SUNDAY) {
                    offectDay = 7 - Math.abs(offectDay);
                }
                c.add(Calendar.DATE, offectDay);
                strDate = mSimpleDateFormat.format(c.getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：获取本月第一天.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getFirstDayOfMonth(String format) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            //当前月的第一天
            c.set(GregorianCalendar.DAY_OF_MONTH, 1);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：获取本月最后一天.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getLastDayOfMonth(String format) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            // 当前月的最后一天
            c.set(Calendar.DATE, 1);
            c.roll(Calendar.DATE, -1);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    public static String getCurrentQuarterStart(String dateFormat) {

        return formatData(dateFormat, getCurrentQuarterStart());

    }

    public static long getCurrentQuarterStart() {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.MONTH, ((int) startCalendar.get(Calendar.MONTH) / 3) * 3);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);
        return startCalendar.getTimeInMillis();

    }

    public static String getCurrentQuarterEnd(String dateFormat) {
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTimeInMillis(getCurrentQuarterStart());
        endCalendar.add(Calendar.MONTH, 3);
        endCalendar.add(Calendar.DAY_OF_MONTH, -1);
        return formatData(dateFormat, getDayEndTime(endCalendar.getTime()));

    }


    private static void setMinTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    //获取本年的开始时间
    public static String getStartDayOfYear(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        // cal.set
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);

        return formatData(dateFormat, cal.getTimeInMillis());
    }

    //获取本年的结束时间
    public static String getEndDayOfYear(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return formatData(dateFormat, getDayEndTime(cal.getTime()));
    }

    //获取今年是哪一年
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(Calendar.YEAR));
    }

    //获取今年是哪一年
    public static Integer getNowYear(long time) {
        Date date = new Date(time);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(Calendar.YEAR));
    }

    //获取某个日期的开始时间
    public static long getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    //获取某个日期的结束时间
    public static long getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }
}
