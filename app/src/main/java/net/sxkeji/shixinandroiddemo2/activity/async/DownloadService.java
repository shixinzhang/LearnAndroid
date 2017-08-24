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

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import top.shixinzhang.utils.DateUtils;

/**
 * Description:
 * <br> 使用 IntentService 实现下载
 * <p>
 * <br> Created by shixinzhang on 17/6/8.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class DownloadService extends IntentService {
    private static final String TAG = "DownloadService";
    public static final String DOWNLOAD_URL = "down_load_url";
    public static final int WHAT_DOWNLOAD_FINISHED = 1;
    public static final int WHAT_DOWNLOAD_STARTED = 2;

    public DownloadService() {
        super(TAG);
    }

    private static Handler mUIHandler;

    public static void setUIHandler(final Handler UIHandler) {
        mUIHandler = UIHandler;
    }

    /**
     * 这个方法运行在子线程
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(final Intent intent) {
        String url = intent.getStringExtra(DOWNLOAD_URL);
        if (!TextUtils.isEmpty(url)) {
            sendMessageToMainThread(WHAT_DOWNLOAD_STARTED, "\n " + DateUtils.getCurrentTime() + " 开始下载任务：\n" + url);
            try {
                Bitmap bitmap = downloadUrlToBitmap(url);
                SystemClock.sleep(1000);    //延迟一秒发送消息
                sendMessageToMainThread(WHAT_DOWNLOAD_FINISHED, bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送消息到主线程
     *
     * @param id
     * @param o
     */
    private void sendMessageToMainThread(final int id, final Object o) {
        if (mUIHandler != null) {
            mUIHandler.sendMessage(mUIHandler.obtainMessage(id, o));
        }
    }

    /**
     * 下载图片
     *
     * @param url
     * @return
     * @throws Exception
     */
    private Bitmap downloadUrlToBitmap(String url) throws Exception {
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
        Bitmap bitmap = BitmapFactory.decodeStream(in);
        urlConnection.disconnect();
        in.close();
        return bitmap;
    }
}
