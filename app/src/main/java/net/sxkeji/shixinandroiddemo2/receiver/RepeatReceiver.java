package net.sxkeji.shixinandroiddemo2.receiver;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import net.sxkeji.shixinandroiddemo2.helper.ConfigHelper;
import net.sxkeji.shixinandroiddemo2.util.AlarmManagerUtil;

import java.util.HashMap;
import java.util.Map;

import top.shixinzhang.utils.DateFormatUtil;
import top.shixinzhang.utils.LogUtil;
import top.shixinzhang.utils.SpUtil;

/**
 * <br/> Description: 接受、执行、发送定时任务消息的广播接收者
 * <p>
 * <br/> Created by shixinzhang on 17/4/21.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class RepeatReceiver extends BroadcastReceiver {
    private final String TAG = this.getClass().getSimpleName();

    private volatile static Map<String, Integer> mStateMap = new HashMap<>();

    public static Integer getState(String action) {
        return mStateMap.get(action);
    }

    public static void setState(String action, Integer state) {
        mStateMap.put(action, state);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            LogUtil.w(TAG, "action is null !: ");
            return;
        }
        LogUtil.i(TAG, "action: " + intent.getAction() + DateFormatUtil.getCurrentTime());

        switch (intent.getAction()) {
            case ConfigHelper.ACTION_REPEAT_BROADCAST_RECEIVER:
                doRepeatJob(context);
                break;
            default:
                break;
        }

    }

    /**
     * 进行定时循环任务
     *
     * @param context
     */
    private void doRepeatJob(Context context) {
//        setState(ConfigHelper.ACTION_REPEAT_BROADCAST_RECEIVER, State.ALREADY_STARTED);
        Integer stopTime = SpUtil.getData(context, ConfigHelper.SP_STOP_TIME, -1);
        LogUtil.d(TAG, "doRepeatJob............... " + DateFormatUtil.getCurrentTime() + " ***  stop time: " + stopTime);


        if (System.currentTimeMillis() >= stopTime) {
            AlarmManagerUtil.cancel(context, ConfigHelper.ACTION_REPEAT_BROADCAST_RECEIVER);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            AlarmManagerUtil.startRepeatTask(context, System.currentTimeMillis() + ConfigHelper.DEFAULT_REPEAT_INTERVAL);
        }
    }

    /**
     * 循环任务的状态
     */
    public interface State {
        Integer NOT_START = 1;
        Integer ALREADY_STARTED = 2;
        Integer FINISHED = 0;
    }
}
