package net.sxkeji.shixinandroiddemo2.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.activity.ServiceTestActivity;
import net.sxkeji.shixinandroiddemo2.helper.ConfigHelper;
import net.sxkeji.shixinandroiddemo2.receiver.RepeatReceiver;
import net.sxkeji.shixinandroiddemo2.util.AlarmManagerUtil;

import top.shixinzhang.utils.DateFormatUtil;
import top.shixinzhang.utils.LogUtil;

/**
 * <br/> Description: 服务
 * https://developer.android.com/reference/android/app/Service.html
 * https://developer.android.com/guide/topics/manifest/service-element.html
 * http://blog.csdn.net/guolin_blog/article/details/11952435
 * <p>
 * <br/> Created by shixinzhang on 16/3/20.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class SxService extends BaseService {

    private static final int NOTIFICATION = 11;
    public long mRepeatTaskStartTime;

    private final String TAG = this.getClass().getSimpleName();

    private SxBinder mBinder = new SxBinder();
    private NotificationManager mNotificationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return mBinder;
    }

    /**
     * 只在第一次创建时调用，后面都会直接调用 onStartCommand
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        showNotification();

//        if (RepeatReceiver.State.ALREADY_STARTED.equals(RepeatReceiver.getState(ConfigHelper.ACTION_REPEAT_BROADCAST_RECEIVER))) {
//            //如果之前开始了，立刻执行定时任务
//            AlarmManagerUtil.startRepeatTask(this, System.currentTimeMillis());
//        } else if (mRepeatTaskStartTime >= System.currentTimeMillis()) {
            //如果有计划的任务
            AlarmManagerUtil.startRepeatTask(this, mRepeatTaskStartTime);
//        }
    }

    /**
     * 在 onCreate 后被调用
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO: 17/4/19 启动线程，执行耗时任务

        if (intent != null) {
            String action = intent.getAction();
            if (ConfigHelper.ACTION_START_DAEMON_SERVICE.equals(action)) {   //守护服务被关闭了，重新开启
                Intent serviceIntent = new Intent(this, DaemonService.class);
                startService(serviceIntent);
            }
        }


        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // TODO: 17/4/19 关闭任务，清理资源

        mNotificationManager.cancel(NOTIFICATION);
        AlarmManagerUtil.cancel(this, ConfigHelper.ACTION_REPEAT_BROADCAST_RECEIVER);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, DaemonService.class);
        intent.setAction(ConfigHelper.ACTION_START_SX_SERVICE);
        startService(intent);
    }

    /**
     * 执行耗时任务还是要新建线程
     */
    public class SxBinder extends Binder {
        private final String TAG = this.getClass().getSimpleName();

        public void doSomething() {
            LogUtil.d(TAG, "doSomething");
        }

        public void startRepeatTaskAtTime(long timeMillis) {
            LogUtil.d(TAG, "定时任务执行时间：" + DateFormatUtil.getDateString(timeMillis));

            mRepeatTaskStartTime = timeMillis;
            AlarmManagerUtil.startRepeatTask(getBaseContext(), timeMillis);
        }
    }


    private void showNotification() {
        CharSequence text = getText(R.string.local_service_started);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, ServiceTestActivity.class), 0);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.icon_ez)
                .setTicker(text)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(getText(R.string.local_service_label))
                .setContentText(text)
                .setContentIntent(contentIntent)
                .build();

        mNotificationManager.notify(NOTIFICATION, notification);
    }

}
