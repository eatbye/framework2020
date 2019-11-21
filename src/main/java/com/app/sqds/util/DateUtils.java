package com.app.sqds.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by cai on 2016/2/19.
 */
public final class DateUtils {

    private DateUtils() {
    }
    /**
     * 将日期形转为yyyy-MM-dd形式
     *
     * @param date
     * @return
     */
    public static String date2string(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     * 将yyyy-MM-dd 类型的字符串转为日期
     *
     * @param dateStr
     * @return
     */
    public static Date string2date(String dateStr) {
        if (dateStr != null && !"".equals(dateStr)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return sdf.parse(dateStr);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return null;
    }


    /**
     * 自定义格式化模式
     *
     * @param pattern 自定义格式
     * @param date    传入的日期
     * @return
     */
    public static String date2string(String pattern, Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } else {
            return "";
        }
    }


    public static Date getZeroDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
