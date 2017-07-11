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

package top.shixinzhang.sxdebugkit;

/**
 * Description:
 * <br> Notification Log，将 Log 以单独的页面显示在手机上
 *  类似 LeakCanary 的效果
 * <p>
 * <br> Created by shixinzhang on 17/7/11.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class NLog {
    private NLog() {
        mEnable = !BuildConfig.DEBUG;
    }

    private volatile static NLog mInstance;
    private boolean mEnable;

    public static NLog getInstance() {
        if (mInstance == null){
            mInstance = new NLog();
        }
        return mInstance;
    }

    public static void i(String tag, String msg){
        getInstance().showNotification(tag, msg);
    }

    /**
     * 通知栏提示
     * @param tag
     * @param msg
     */
    private void showNotification(final String tag, final String msg) {
        if (!mEnable){
            return;
        }

        // TODO: 17/7/11 点击跳转到展示 Activity
    }

    public static void destroyInstance() {
        mInstance = null;
    }

}
