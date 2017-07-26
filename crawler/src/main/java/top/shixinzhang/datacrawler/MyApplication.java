package top.shixinzhang.datacrawler;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;

import top.shixinzhang.datacrawler.utils.AlertUtil;
import top.shixinzhang.datacrawler.utils.AppBlockCanaryContext;

/**
 * Description:
 * <br>
 * <p>
 * <br> Created by shixinzhang on 17/5/10.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.init(this);
        AlertUtil.init(this);
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }
}
