package net.sxkeji.shixinandroiddemo2.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

import net.sxkeji.shixinandroiddemo2.helper.ConfigHelper;
import net.sxkeji.shixinandroiddemo2.receiver.RepeatReceiver;

import top.shixinzhang.utils.DateFormatUtil;
import top.shixinzhang.utils.LogUtil;

/**
 * <br/> Description: 定时器任务工具类
 * <p>
 * <br/> Created by shixinzhang on 17/4/21.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public final class AlarmManagerUtil {
    private AlarmManagerUtil() {
    }

    private static final String TAG = "AlarmManagerUtil";
    private static final int NO_SENSE_CODE = 0;

    public static void startRepeatTask(Context context, long startTimeMillis) {
        AlarmManagerUtil.repeat(context, ConfigHelper.ACTION_REPEAT_BROADCAST_RECEIVER, startTimeMillis, ConfigHelper.DEFAULT_REPEAT_INTERVAL);
    }

    public static void stopRepeatTask(Context context) {
        AlarmManagerUtil.cancel(context, ConfigHelper.ACTION_REPEAT_BROADCAST_RECEIVER);
    }

    /**
     * 定时执行
     *
     * @param context
     * @param action             约定的 action
     * @param startTimeMillis    开始时间，一般是 System.currentTimeMillis();
     * @param intervalTimeMillis 间隔时间
     */
    public static void repeat(@NonNull Context context, String action, Long startTimeMillis, Long intervalTimeMillis) {
        LogUtil.i(TAG, "repeat " + action + " send at: " + DateFormatUtil.getDateString(startTimeMillis));

        PendingIntent sender = getRepeatSender(context, action);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4 以后必须使用这个方法，不然不起作用
            alarmManager.set(AlarmManager.RTC, startTimeMillis, sender);
        } else {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, startTimeMillis, intervalTimeMillis, sender);
        }
    }

    /**
     * 结束任务
     *
     * @param context
     * @param action
     */
    public static void cancel(@NonNull Context context, @NonNull String action) {
        LogUtil.i(TAG, "cancel " + action);

        PendingIntent sender = getRepeatSender(context, action);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    /**
     * 获得 RepeatReceiver 的 PendingIntent，它用来定时发送
     *
     * @param context
     * @param action
     * @return
     */
    private static PendingIntent getRepeatSender(Context context, String action) {
        Intent intent1 = new Intent(context, RepeatReceiver.class);
        intent1.setAction(action);
        return PendingIntent.getBroadcast(context, NO_SENSE_CODE, intent1, Intent.FILL_IN_SELECTOR);
    }


}
