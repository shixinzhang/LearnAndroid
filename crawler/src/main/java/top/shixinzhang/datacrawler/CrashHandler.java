package top.shixinzhang.datacrawler;

import android.content.Context;
import android.content.Intent;

import java.util.LinkedList;
import java.util.List;

import top.shixinzhang.datacrawler.utils.AlertUtil;


/**
 * <br> Description:
 * <p> 错误捕获
 * <p>
 * <br> Created by shixinzhang on 17/4/24.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Context mContext;
    public boolean openUpload = true;
    private static final String DEFAULT_LOG_DIR = "log";
    private static final String FILE_NAME_SUFFIX = ".log";
    private static volatile CrashHandler sInstance = null;
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private static volatile List<OnCrashCallback> mCrashCallbackList = new LinkedList<>();

    /**
     * 奔溃回调观察者
     */
    public interface OnCrashCallback {
        void onCrash(Throwable throwable);
    }

    private CrashHandler(Context cxt) {
        this.mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.mContext = cxt.getApplicationContext();
    }

    public static synchronized CrashHandler init(Context cxt) {
        if (sInstance == null) {
            sInstance = new CrashHandler(cxt);
        }
        return sInstance;
    }

    public static void registerCallback(OnCrashCallback callback) {
        if (callback != null) {
            mCrashCallbackList.add(callback);
        }
    }

    public static void unRegisterCallback(OnCrashCallback callback) {
        if (callback != null) {
            mCrashCallbackList.remove(callback);
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        notifyObservers(ex);

        try {
            AlertUtil.toastShort(mContext, "啊偶，奔溃了");
//
//            Intent launchIntentForPackage = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
//            if (launchIntentForPackage != null) {
//                launchIntentForPackage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                launchIntentForPackage.putExtra(Config.RESUME_FROM_CRASH, true);
//                mContext.startActivity(launchIntentForPackage);
//            }
//            System.exit(0);

        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    /**
     * 发送奔溃通知
     *
     * @param ex
     */
    private void notifyObservers(Throwable ex) {
        for (OnCrashCallback onCrashCallback : mCrashCallbackList) {
            onCrashCallback.onCrash(ex);
        }
    }


}