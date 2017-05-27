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

package net.sxkeji.shixinandroiddemo2.activity.http;

import net.sxkeji.shixinandroiddemo2.network.okhttp3.SxHttpClient;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.shixinzhang.sxframework.utils.LogUtils;

/**
 * Description:
 * <br>
 * <p>
 * <br> Created by shixinzhang on 17/5/27.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class Test {
    private final String TAG = this.getClass().getSimpleName();
    private final String GET_URL = "http://www.391k.com/api/xapi.ashx/info.json?key=bd_hyrzjjfb4modhj&size=10&page=1";

    /**
     * OkHttp performs best when you create a single {@code OkHttpClient} instance and reuse it for
     * all of your HTTP calls. This is because each client holds its own connection pool and thread
     * pools. Reusing connections and threads reduces latency and saves memory. Conversely, creating a
     * client for each request wastes resources on idle pools.
     */
    private void get() {
        //单例，全局共用一个
        OkHttpClient okHttpClient1 = new OkHttpClient();     //直接用默认设置

//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor( new SxHttpInterceptor())
//                .hostnameVerifier()
//                .

        Request request = new Request.Builder()
                .get()
                .url(GET_URL)
                .build();

        SxHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                if (response != null) {
                    LogUtils.d(TAG, "response: " + response);
                }
            }
        });
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.get();
    }
}
