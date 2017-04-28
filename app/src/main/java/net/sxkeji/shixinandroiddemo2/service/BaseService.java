package net.sxkeji.shixinandroiddemo2.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * <br/> Description:基础服务，输出日志
 * <p>
 * <br/> Created by shixinzhang on 17/4/19.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class BaseService extends Service {

    private final String TAG = this.getClass().getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() executed");
        return null;
    }

    /**
     * 只在第一次创建时调用，后面都会直接调用 onStartCommand
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");

    }

    /**
     * 在 onCreate 后被调用
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO: 17/4/19 启动线程，执行耗时任务
        Log.d(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged() executed");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind() executed");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind() executed");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        // TODO: 17/4/19 关闭任务，清理资源

        Log.d(TAG, "onDestroy() executed");
        super.onDestroy();
    }
}
