package top.shixinzhang.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * <br> Description:日期数据格式化
 * <p>
 * <br> Created by shixinzhang on 17/4/21.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public final class DateFormatUtil {
    private DateFormatUtil() {
    }

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前日期格式化
     *
     * @return
     */
    public static String getCurrentTime() {
        return getDateString(System.currentTimeMillis());
    }

    /**
     * 常用的格式化为：年月日时分秒
     *
     * @param timeMillis
     * @return
     */
    public static String getDateString(long timeMillis) {
        return new SimpleDateFormat(DEFAULT_PATTERN, Locale.getDefault()).format(new Date(timeMillis));
    }

    /**
     * 格式化为：月日
     *
     * @param timeMillis
     * @return
     */
    public static String getMonthDay(long timeMillis) {
        return new SimpleDateFormat("MM-dd", Locale.getDefault()).format(new Date(timeMillis));
    }

    /**
     * 格式化为：月日到小时
     *
     * @param timeMillis
     * @return
     */
    public static String getMonthToHour(long timeMillis) {
        return new SimpleDateFormat("MM-dd HH", Locale.getDefault()).format(new Date(timeMillis));
    }

    /**
     * 格式化为：月日小时分
     *
     * @param timeMillis
     * @return
     */
    public static String getMonthToMinute(long timeMillis) {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()).format(new Date(timeMillis));
    }

    /**
     * 格式化为：月日小时分秒
     *
     * @param timeMillis
     * @return
     */
    public static String getMonthToSeconds(long timeMillis) {
        return new SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(timeMillis));
    }

    /**
     * 获取当天 + 小时和分钟 转换成的毫秒数
     * @param hourOfDay
     * @param minute
     * @return
     */
    public static long getTimeInMillisecond(int hourOfDay, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        return calendar.getTimeInMillis();
    }
}
