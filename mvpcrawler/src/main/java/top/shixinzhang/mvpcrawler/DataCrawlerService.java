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

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import top.shixinzhang.mvpcrawler.helper.MonitorThread;
import top.shixinzhang.mvpcrawler.mvp.CrawlerContract;
import top.shixinzhang.mvpcrawler.mvp.presenter.BasePresenter;
import top.shixinzhang.mvpcrawler.mvp.presenter.CommonPresenter;

import static top.shixinzhang.mvpcrawler.helper.MonitorThread.MSG_MONITOR;
import static top.shixinzhang.mvpcrawler.mvp.CrawlerContract.Model.MODE_START;
import static top.shixinzhang.mvpcrawler.mvp.CrawlerContract.Model.MODE_STOP;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class DataCrawlerService extends AccessibilityService implements Handler.Callback {

    public final String TAG = DataCrawlerService.class.getSimpleName();

    public static final String MODE_KEY = "MODE";
    private static String mCurrentApp;
    public static String sSwipeCmd;
    private int mMode;

    private MonitorThread mMonitorThread;
    private static BasePresenter mPresenter;
    public static Context mContext;


    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        showToast("自动点击服务已开启");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        int screenHeight = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        sSwipeCmd = String.format("input swipe %d %d %d %d", 10, screenHeight - 200, 10, 100);
        mContext = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null != intent) {
            mMode = intent.getIntExtra(MODE_KEY, MODE_STOP);
            Log.i(TAG, "onStartCommand mode:" + mMode);
            if (mMode == MODE_START && mPresenter != null) {
                getPresenter().startApp();
                initMonitorThread();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:  //监听进入界面
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                getPresenter().receiveEvent(getRootInActiveWindow(), event);
                break;
        }
    }

    private void initMonitorThread() {
        mMonitorThread = new MonitorThread("MVP监控");
        mMonitorThread.setUIHandler(new Handler(this));
        mMonitorThread.start();
    }

    private void destoryMonitor() {
        if (mMonitorThread != null) {
            mMonitorThread.quit();
            mMonitorThread = null;
        }
    }


    @NonNull
    public static BasePresenter getPresenter() {
        return mPresenter;
    }

    public static void setPresenter(@NonNull final CrawlerContract.View view, @NonNull final CrawlerContract.Model model) {
        mPresenter = new CommonPresenter(view, model);
        mCurrentApp = view.getAppName();
    }

    public static String getCurrentApp() {
        return mCurrentApp;
    }

    public static void setCurrentApp(final String mCurrentApp) {
        DataCrawlerService.mCurrentApp = mCurrentApp;
    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "onInterrupt:  ");
        showToast("自动点击服务 onInterrupted ");
    }


    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 监听状态线程处理，抛到主线程进行
     *
     * @param msg
     * @return
     */
    @Override
    public boolean handleMessage(Message msg) {
        Log.e(TAG, "!!!!!! not worked !!!!!");
        CrawlerContract.Model model = getPresenter().getModel();
        switch (msg.what) {
            case MSG_MONITOR:
                getPresenter().getView().resolveNotWorked(
                        model.getRootNode(),
                        model.getMode(),
                        model.getCurrentClassName());
                break;
        }
        return true;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        showToast("自动点击服务被系统关闭了，请开启");
        mContext = null;
        return super.onUnbind(intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destoryMonitor();
    }

}
