package top.shixinzhang.datacrawler.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * com.yuntu.wxcrawler.common.utils
 * Author : erik
 * Email  : erik7@126.com
 * Date   : 17/2/14
 * Version 1.0
 * Description:
 * <p>
 * <p>
 * Copyright (c) 2017 www.yaomaiche.com
 * All Rights Reserved.
 */

public class DateUtils {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static Calendar getCurrentCalendar() {
        Calendar instance = Calendar.getInstance(Locale.CHINA);
        instance.setTimeInMillis(System.currentTimeMillis());
        return instance;
    }

    public static String getMMdd(long time) {
        return new SimpleDateFormat("MMdd", Locale.getDefault()).format(new Date(time));
    }

    public static String getMMddhhmmss(long time) {
        return new SimpleDateFormat("MMddHHmmss", Locale.getDefault()).format(new Date(time));
    }

    public static String getYMdHms(long time) {
        return new SimpleDateFormat(DEFAULT_PATTERN, Locale.getDefault()).format(new Date(time));
    }

    public static long string2Millis(String time, String pattern) {
        try {
            return new SimpleDateFormat(pattern, Locale.getDefault()).parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String millis2String(long millis, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date(millis));
    }


    /**
     * 获取HH:mm的时间mills
     *
     * @param hmString
     * @return
     */
    public static long hmString2Mills(String hmString) {
        String pattern = "yyyy-MM-dd";

        String fullTimeString = DateUtils.millis2String(System.currentTimeMillis(), pattern) + " " +
                hmString;

        pattern += " HH:mm";
        return DateUtils.string2Millis(fullTimeString, pattern);
    }

}
