package net.sxkeji.shixinandroiddemo2;

import android.app.Activity;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

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
import top.shixinzhang.sxframework.statistic.CrashHandler;
import top.shixinzhang.sxframework.utils.ApplicationUtils;

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

        CrashHandler.init(this);

//        initANRWatch();

//        initRealm();

        initWeex();
    }

    private void initANRWatch() {
        new ANRWatchDog().setANRListener(new ANRWatchDog.ANRListener() {
            @Override
            public void onAppNotResponding(ANRError error) {
                System.out.println(error.getMessage() + ", " + System.currentTimeMillis());
                String foregroundAppName = ApplicationUtils.getForegroundAppName(getBaseContext());
                System.out.println(foregroundAppName);
                ApplicationUtils.startApplication(getBaseContext(), "com.tencent.mm");
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

    /**
     * 注册 Hybrid 工厂
     */
    private void registerHybridHandler() {
        HybridFactory.getInstance().registerHandlers(UIHandler.class);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

    }

    private void addLifecycleListener() {

        //生命周期监听
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                initCommonView(activity, savedInstanceState);
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

    /**
     * 初始化通用布局，类似以前在 BaseActivity 干的事
     *
     * @param activity
     * @param savedInstanceState
     */
    private void initCommonView(Activity activity, Bundle savedInstanceState) {

    }
}
