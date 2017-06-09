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

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.sxkeji.shixinandroiddemo2.R;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:
 * <br> IntentService 实例
 * <p>
 * <br> Created by shixinzhang on 17/6/9.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class IntentServiceActivity extends AppCompatActivity implements Handler.Callback {

    @BindView(R.id.iv_display)
    ImageView mIvDisplay;
    @BindView(R.id.btn_download)
    Button mBtnDownload;
    @BindView(R.id.tv_status)
    TextView mTvStatus;

    private List<String> urlList = Arrays.asList("https://ws1.sinaimg.cn/large/610dc034ly1fgepc1lpvfj20u011i0wv.jpg",
            "https://ws1.sinaimg.cn/large/d23c7564ly1fg6qckyqxkj20u00zmaf1.jpg",
            "https://ws1.sinaimg.cn/large/610dc034ly1fgchgnfn7dj20u00uvgnj.jpg");
    int mFinishCount;   //完成的任务个数

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        ButterKnife.bind(this);
        DownloadService.setUIHandler(new Handler(this));
    }

    @OnClick(R.id.btn_download)
    public void downloadImage() {
        Intent intent = new Intent(this, DownloadService.class);

        for (String url : urlList) {
            intent.putExtra(DownloadService.DOWNLOAD_URL, url);
            startService(intent);
        }
        mBtnDownload.setEnabled(false);
    }


    @Override
    public boolean handleMessage(final Message msg) {
        if (msg != null) {
            switch (msg.what) {
                case DownloadService.WHAT_DOWNLOAD_FINISHED:
                    mIvDisplay.setImageBitmap((Bitmap) msg.obj);
                    mBtnDownload.setText("完成 " + (++mFinishCount) + "个任务");
                    break;
                case DownloadService.WHAT_DOWNLOAD_STARTED:
                    mTvStatus.setText(mTvStatus.getText() + (String) msg.obj);
                    break;
            }
        }
        return true;
    }
}
