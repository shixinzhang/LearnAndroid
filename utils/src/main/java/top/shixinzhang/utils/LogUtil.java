package top.shixinzhang.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * <br> Description: 日志输出类
 * <p>
 * <br> Created by shixinzhang on 17/4/21.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public final class LogUtil {
    private static final String TAG = "LogUtil";

    /**
     * Priority constant for the println method
     */
    private static final int VERBOSE = 2;
    private static final int DEBUG = 3;
    private static final int INFO = 4;
    private static final int WARN = 5;
    private static final int ERROR = 6;

    /**
     * 是否显示日志，默认为 true
     */
    private volatile static boolean isDebug = true;

    private static boolean isDebug() {
        return isDebug;
    }

    private static void setDebug(boolean isDebug) {
        LogUtil.isDebug = isDebug;
    }

    private LogUtil() {
    }

    public static void v(String log) {
        v(TAG, log);
    }

    public static void d(String log) {
        d(TAG, log);
    }

    public static void i(String log) {
        i(TAG, log);
    }

    public static void w(String log) {
        w(TAG, log);
    }

    public static void e(String log) {
        e(TAG, log);
    }

    public static void v(String tag, String log) {
        println(tag, log, VERBOSE);
    }

    public static void d(String tag, String log) {
        println(tag, log, DEBUG);
    }

    public static void i(String tag, String log) {
        println(tag, log, INFO);
    }

    public static void w(String tag, String log) {
        println(tag, log, WARN);
    }

    public static void e(String tag, String log) {
        println(tag, log, ERROR);
    }

    public static void println(String tag, String log, int type) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(log)) {
            return;
        }

        if (!isDebug){
            // TODO: 17/4/21 当不输出日志时，可以考虑将日志文件保存，上传
            return;
        }

        switch (type) {
            default:
            case VERBOSE:
                Log.v(tag, log);
                break;
            case DEBUG:
                Log.d(tag, log);
                break;
            case INFO:
                Log.i(tag, log);
                break;
            case WARN:
                Log.w(tag, log);
                break;
            case ERROR:
                Log.e(tag, log);
                break;
        }

    }
}
