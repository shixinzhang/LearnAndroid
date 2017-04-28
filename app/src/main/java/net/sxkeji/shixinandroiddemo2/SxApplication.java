package net.sxkeji.shixinandroiddemo2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import com.github.anrwatchdog.ANRError;
import com.github.anrwatchdog.ANRWatchDog;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;

import net.sxkeji.shixinandroiddemo2.hybrid.handler.UIHandler;
import net.sxkeji.shixinandroiddemo2.hybrid.handler.internal.HybridFactory;
import net.sxkeji.shixinandroiddemo2.weex.adapter.DefaultWebSocketAdapterFactory;
import net.sxkeji.shixinandroiddemo2.weex.adapter.ImageAdapter;
import net.sxkeji.shixinandroiddemo2.weex.adapter.PlayDebugAdapter;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import top.shixinzhang.sxframework.utils.ApplicationUtil;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 16/12/21.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class SxApplication extends MultiDexApplication {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        addLifecycleListener();

        registerHybridHandler();

//        initANRWatch();

//        initRealm();

        initWeex();
    }

    private void initANRWatch() {
        new ANRWatchDog().setANRListener(new ANRWatchDog.ANRListener() {
            @Override
            public void onAppNotResponding(ANRError error) {
                System.out.println(error.getMessage() + ", " + System.currentTimeMillis());
                String foregroundAppName = ApplicationUtil.getForegroundAppName(getBaseContext());
                System.out.println(foregroundAppName);
                ApplicationUtil.startApplication(getBaseContext(), "com.tencent.mm");
                System.exit(0);
            }
        }).start();
    }

    private void initWeex() {
        InitConfig config = new InitConfig.Builder()
                .setImgAdapter(new ImageAdapter(this))
                .setDebugAdapter(new PlayDebugAdapter())
                .setWebSocketAdapterFactory(new DefaultWebSocketAdapterFactory())
                .build();
        WXSDKEngine.initialize(this, config);
    }

    private void initRealm() {
        Realm.init(this);
        Realm.setDefaultConfiguration(
                new RealmConfiguration.Builder()
                        .name("shixinzhang.realm")
                        .deleteRealmIfMigrationNeeded()
//                        .inMemory() //数据保存在内存
                        .build()
        );
    }

    private void registerHybridHandler() {
        HybridFactory.getInstance().registerHandlers(UIHandler.class);

    }

    private void addLifecycleListener() {

        //生命周期监听
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {
//                Log.d(TAG, activity.getLocalClassName() + " onActivityPaused");
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
