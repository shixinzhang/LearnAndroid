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
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import java.util.List;

import top.shixinzhang.mvpcrawler.mvp.CrawlerContract;
import top.shixinzhang.mvpcrawler.mvp.presenter.CommonPresenter;
import top.shixinzhang.utils.DateUtils;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class DataCrawlerService extends AccessibilityService implements Handler.Callback {

    public static final String MODE_KEY = "MODE";
    public final String TAG = DataCrawlerService.class.getSimpleName();
    private String mCurrentCarName;
    private int mScreenHeight;
    public static String sSwipeCmd;
    private String mSwipeCmdUp; //上滑

    private String mCurrentSeriesName;  //当前查看的车系名称
    private String mFileTimeStamp = DateUtils.getMMddhhmmss(System.currentTimeMillis());

    private int mMode;
    public static final int MODE_STOP = -1; //停止
    public static final int MODE_SELECT_BRAND = 1;    //首页，选择品牌
    private final int MODE_SELECT_CAR_SERIES = 2;   //选择车系
    private final int MODE_SELECT_CAR_MODEL = 3;    //选择车款
    private final int MODE_CLICK_PHONE_NODE = 4;
    private final int MODE_GET_NUMBER = 5;
//    private boolean mFirstOpen = true;

    public static CrawlerContract.Presenter mPresenter;


    private static List<String> mWhiteClassNameList;    //白名单

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        showToast("自动点击服务已开启");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mScreenHeight = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        sSwipeCmd = String.format("input swipe %d %d %d %d", 10, mScreenHeight - 250, 10, 100);
        mSwipeCmdUp = String.format("input swipe %d %d %d %d", 10, 100, 10, mScreenHeight - 100);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null != intent) {
            mMode = intent.getIntExtra(MODE_KEY, MODE_SELECT_BRAND);
            Log.i(TAG, "onStartCommand mode:" + mMode);
            if (mMode == MODE_SELECT_BRAND && mPresenter != null) {
                getPresenter().startApp();
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


    @NonNull
    public static CrawlerContract.Presenter getPresenter() {
        return mPresenter;
    }

    public static void setPresenter(@NonNull final CrawlerContract.View view, @NonNull final CrawlerContract.Model model) {
        mPresenter = new CommonPresenter(view, model);
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
        Toast.makeText(getApplicationContext(), "你为什么又不动了？", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        showToast("自动点击服务被系统关闭了，请开启");
        return super.onUnbind(intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
