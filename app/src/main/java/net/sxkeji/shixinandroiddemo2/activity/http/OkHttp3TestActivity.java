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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.network.okhttp3.SafeCallBack;
import net.sxkeji.shixinandroiddemo2.network.okhttp3.SxHttpClient;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.shixinzhang.sxframework.config.Config;

/**
 * Description:
 * <br> 测试 OkHttp3：
 * GET  同步/异步
 * POST
 * 文件下载
 * 文件上传
 * 日志
 * 解析响应
 * 缓存
 * <p>
 * <br> Created by shixinzhang on 17/5/27.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class OkHttp3TestActivity extends BaseActivity {
    private final String TAG = this.getClass().getSimpleName();
    private final String GET_URL = "http://www.391k.com/api/xapi.ashx/info.json?key=bd_hyrzjjfb4modhj&size=10&page=1";
    private final String POST_URL = "";
    public final String FILE_POST_URL = "https://api.github.com/markdown/raw";

    @BindView(R.id.tv_response)
    TextView mTvResponse;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        ButterKnife.bind(this);

        get();
    }

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

        SxHttpClient.newCall(request).enqueue(new SafeCallBack() {
            @Override
            protected void onUIFailure(final Call call, final IOException e) {
                mTvResponse.setText(e.getMessage());
            }

            @Override
            protected void onUISuccess(final Call call, final Response response) throws IOException {
                mTvResponse.setText(response.body().string());
            }
        });
    }

    private void post() {
        FormBody formBody = new FormBody.Builder()
                .add("account", "shixinzhang")
                .addEncoded("password", "123456")
                .build();

        Request request = new Request.Builder()
                .url(POST_URL)
                .post(formBody)
                .build();

        SxHttpClient.newCall(request).enqueue(new SafeCallBack() {
            @Override
            protected void onUIFailure(final Call call, final IOException e) {

            }

            @Override
            protected void onUISuccess(final Call call, final Response response) throws IOException {

            }
        });
    }

    private void uploadFile() {
        File file = new File(Config.EXTERNAL_DIR);
        Request request = new Request.Builder()
                .url(FILE_POST_URL)
                .post(RequestBody.create(SxHttpClient.MEDIA_TYPE_MARKWODN, file))
                .build();

        SxHttpClient.newCall(request)
                .enqueue(new SafeCallBack() {
                    @Override
                    protected void onUIFailure(final Call call, final IOException e) {

                    }

                    @Override
                    protected void onUISuccess(final Call call, final Response response) throws IOException {

                    }
                });
    }

    @Override
    public void initViews() {

    }

    @Override
    public void loadData() {

    }

    public void addListeners() {

    }
//
//    public static void main(String[] args) {
//        OkHttp3TestActivity activity = new OkHttp3TestActivity();
//        activity.get();
//    }
}
