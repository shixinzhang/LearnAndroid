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

package top.shixinzhang.mvpcrawler.helper;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import top.shixinzhang.mvpcrawler.DataCrawlerService;
import top.shixinzhang.mvpcrawler.mvp.presenter.BasePresenter;

/**
 * Description:
 * <br> 监控 Thread
 * <p>
 * <br> Created by shixinzhang on 17/8/10.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class MonitorThread extends HandlerThread {
    private final String TAG = this.getClass().getSimpleName();
    public final static int MSG_MONITOR = 11;

    public MonitorThread(final String name) {
        super(name);
    }

    private Handler mWorkerHandler;
    private Handler mUIHandler;
    private AtomicBoolean mRunnable = new AtomicBoolean(false);
    private int mLastNumber;

    public Handler getUIHandler() {
        return mUIHandler;
    }

    public MonitorThread setUIHandler(final Handler UIHandler) {
        mUIHandler = UIHandler;
        return this;
    }

    @Override
    public synchronized void start() {
        mRunnable.set(true);
        super.start();
    }

    @Override
    protected void onLooperPrepared() {
        mWorkerHandler = new Handler(getLooper());
        final BasePresenter presenter = DataCrawlerService.getPresenter();
        final Map<String, String> phoneMap = presenter.getModel().getPhoneMap();

        mWorkerHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "monitor 开启");

                while (mRunnable.get()) {
                    SystemClock.sleep(20_000);  //每隔 20s 检查一次

                    if (getLastNumber() != phoneMap.size()) {
                        setLastNumber(phoneMap.size());
                    } else {
                        notifyUIThread();
                    }
                }
            }
        });
    }

    /**
     * 通知卡了
     */
    private void notifyUIThread() {
        if (mUIHandler != null) {
            mUIHandler.sendEmptyMessage(MSG_MONITOR);
        }
    }

    public int getLastNumber() {
        return mLastNumber;
    }

    public MonitorThread setLastNumber(final int lastNumber) {
        Log.i(TAG, "Running normal, current size is " + lastNumber);
        mLastNumber = lastNumber;
        return this;
    }


    @Override
    public boolean quit() {
        mRunnable.set(false);
        mWorkerHandler.removeMessages(MSG_MONITOR);
        setUIHandler(null);
        return super.quit();
    }
}
