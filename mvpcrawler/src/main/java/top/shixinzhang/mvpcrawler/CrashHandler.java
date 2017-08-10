/*
 * Copyright (c) 2017. shixinzhang (shixinzhang2016@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.shixinzhang.mvpcrawler;

import android.content.Context;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

import top.shixinzhang.utils.AlertUtils;


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
            AlertUtils.toastShort(mContext, "啊偶，奔溃了");
//
//            Intent launchIntentForPackage = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
//            if (launchIntentForPackage != null) {
//                launchIntentForPackage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                launchIntentForPackage.putExtra(Config.RESUME_FROM_CRASH, true);
//                mContext.startActivity(launchIntentForPackage);
//            }
//            System.exit(0);
            dumpCrashInfo(ex);

            mDefaultCrashHandler.uncaughtException(thread, ex);

        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    /**
     * 保存奔溃日志
     *
     * @param ex
     */
    private void dumpCrashInfo(final Throwable ex) {
        if (ex == null) {
            return;
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        if (BuildConfig.DEBUG) {
            // TODO: 17/7/24  跳转到 Crash 详情页面
        }
        FLog.i(writer.toString(), Config.CRASH_LOG_PATH);
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