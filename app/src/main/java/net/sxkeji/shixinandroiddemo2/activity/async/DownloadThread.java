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

package net.sxkeji.shixinandroiddemo2.activity.async;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import top.shixinzhang.utils.DateUtils;

/**
 * Description:
 * <br> 使用 HandlerThread 实现下载
 * <p>
 * <br> Created by shixinzhang on 17/6/7.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class DownloadThread extends HandlerThread implements Handler.Callback {

    private final String TAG = this.getClass().getSimpleName();
    private final String KEY_URL = "url";
    public static final int TYPE_START = 1;
    public static final int TYPE_FINISHED = 2;

    private Handler mWorkerHandler;
    private Handler mUIHandler;
    private List<String> mDownloadUrlList;

    public DownloadThread(final String name) {
        super(name);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mWorkerHandler = new Handler(getLooper(), this);
        if (mUIHandler == null) {
            throw new IllegalArgumentException("Not set UIHandler!");
        }

        for (String url : mDownloadUrlList) {
            Message message = mWorkerHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString(KEY_URL, url);
            message.setData(bundle);
            mWorkerHandler.sendMessage(message);
        }
    }

    public void setDownloadUrls(String... urls) {
        mDownloadUrlList = Arrays.asList(urls);
    }

    public Handler getUIHandler() {
        return mUIHandler;
    }

    public DownloadThread setUIHandler(final Handler UIHandler) {
        mUIHandler = UIHandler;
        return this;
    }

    /**
     * 子线程中执行任务，完成后发送消息到主线程
     *
     * @param msg
     * @return
     */
    @Override
    public boolean handleMessage(final Message msg) {
        if (msg == null || msg.getData() == null) {
            return false;
        }

        String url = (String) msg.getData().get(KEY_URL);


        //下载开始，通知主线程
        Message startMsg = mUIHandler.obtainMessage(TYPE_START, "\n 开始下载 @" + DateUtils.getCurrentTime() + "\n" + url);
        mUIHandler.sendMessage(startMsg);

        SystemClock.sleep(2000);    //模拟下载

        //下载完成，通知主线程
        Message finishMsg = mUIHandler.obtainMessage(TYPE_FINISHED, "\n 下载完成 @" + DateUtils.getCurrentTime() + "\n" + url);
        mUIHandler.sendMessage(finishMsg);

        return true;
    }

    @Override
    public boolean quitSafely() {
        mUIHandler = null;
        return super.quitSafely();
    }
}
