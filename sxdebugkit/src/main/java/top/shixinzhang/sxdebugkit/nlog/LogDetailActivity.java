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

package top.shixinzhang.sxdebugkit.nlog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

import top.shixinzhang.sxdebugkit.R;
import top.shixinzhang.sxdebugkit.nlog.model.LogDetailEntity;

/**
 * Description:
 * <br> Log 详情页面
 * <p>
 * <br> Created by shixinzhang on 17/7/11.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class LogDetailActivity extends AppCompatActivity {
    private List<LogDetailEntity> mLogList;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_detail);
        getLogListFromLocal();
    }

    /**
     * 从本地读取日志缓存
     */
    private void getLogListFromLocal() {
        mLogList = new LinkedList<>();
        // TODO: 17/7/11
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        if (intent != null){
            // TODO: 17/7/11 将新的日志添加到 list
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
