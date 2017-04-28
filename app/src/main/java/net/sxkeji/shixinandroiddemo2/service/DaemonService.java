package net.sxkeji.shixinandroiddemo2.service;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import net.sxkeji.shixinandroiddemo2.helper.ConfigHelper;

/**
 * <br/> Description:守护服务
 * <p>
 * <br/> Created by shixinzhang on 17/4/19.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class DaemonService extends BaseService {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        startSxService(null);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            String action = intent.getAction();
            if (ConfigHelper.ACTION_START_SX_SERVICE.equals(action)) {   //后台服务被关闭了，重新开启
                startSxService(null);
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startSxService(ConfigHelper.ACTION_START_DAEMON_SERVICE);
    }


    private void startSxService(String action) {
        Intent serviceIntent = new Intent(this, SxService.class);
        if (!TextUtils.isEmpty(action)){
            serviceIntent.setAction(action);
        }
        startService(serviceIntent);
    }
}
