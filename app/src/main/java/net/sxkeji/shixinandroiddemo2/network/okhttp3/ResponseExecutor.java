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

package net.sxkeji.shixinandroiddemo2.network.okhttp3;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * Description:
 * <br> 将响应抛到主线程
 * <p>
 * <br> Created by shixinzhang on 17/5/27.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class ResponseExecutor implements Executor {
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private static ResponseExecutor mInstance = new ResponseExecutor();

    public static ResponseExecutor getInstance() {
        return mInstance;
    }

    @Override
    public void execute(Runnable r) {
        mHandler.post(r);
    }
}
