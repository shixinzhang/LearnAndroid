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

package top.shixinzhang.hook_sample;

import android.util.Log;

/**
 * Description:
 * <br> 堆栈检查
 * <p>
 * <br> Created by shixinzhang on 17/8/23.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class StackTraceUtils {
    private static final String TAG = "Utils";

    /**
     * 输出当前线程的堆栈
     */
    static void printStackTrace() {
//        Log.d(TAG, Log.getStackTraceString(new Throwable()));
//        Log.wtf(TAG, "this a fake error");

        StackTraceElement st[] = Thread.currentThread().getStackTrace();
        for (int i = 0; i < st.length; i++) {
            System.out.println(i + ":" + st[i]);
        }
    }
}
