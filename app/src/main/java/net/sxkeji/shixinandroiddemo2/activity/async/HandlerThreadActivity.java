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
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import net.sxkeji.shixinandroiddemo2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:
 * <br> HandlerThread 示例程序
 * <p>
 * <br> Created by shixinzhang on 17/6/7.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class HandlerThreadActivity extends AppCompatActivity implements Handler.Callback {

    @BindView(R.id.tv_start_msg)
    TextView mTvStartMsg;
    @BindView(R.id.tv_finish_msg)
    TextView mTvFinishMsg;
    @BindView(R.id.btn_start_download)
    Button mBtnStartDownload;

    private Handler mUIHandler;
    private DownloadThread mDownloadThread;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread_test);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mUIHandler = new Handler(this);
        mDownloadThread = new DownloadThread("下载线程");
        mDownloadThread.setUIHandler(mUIHandler);
        mDownloadThread.setDownloadUrls("http://pan.baidu.com/s/1qYc3EDQ", "http://bbs.005.tv/thread-589833-1-1.html", "http://list.youku.com/show/id_zc51e1d547a5b11e2a19e.html?");

    }

    @OnClick(R.id.btn_start_download)
    public void startDownload() {
        mDownloadThread.start();
        mBtnStartDownload.setText("正在下载");
        mBtnStartDownload.setEnabled(false);
    }

    @Override
    public boolean handleMessage(final Message msg) {
        switch (msg.what) {
            case DownloadThread.TYPE_FINISHED:
                mTvFinishMsg.setText(mTvFinishMsg.getText().toString() + "\n " + msg.obj);
                break;
            case DownloadThread.TYPE_START:
                mTvStartMsg.setText(mTvStartMsg.getText().toString() + "\n " + msg.obj);
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDownloadThread.quitSafely();
    }
}
