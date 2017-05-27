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

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Description:
 * <br> OkHttp3 的拦截器，在这里可以做一些日志输出、请求信息加密
 * <p>
 * <br> Created by shixinzhang on 17/5/27.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */
public class SxHttpInterceptor implements Interceptor {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        HttpUrl.Builder urlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());

        //是否需要加密内容
        RequestBody requestBody = encryptRequest(oldRequest);
        if (requestBody == null) {
            requestBody = oldRequest.body();
        }

        Request.Builder requestBuilder = oldRequest.newBuilder()
                .method(oldRequest.method(), requestBody)
                .cacheControl(oldRequest.cacheControl())
                .headers(oldRequest.headers())
                .url(urlBuilder.build());

        addRequestHeader(requestBuilder);

        Request request = requestBuilder.build();
        Log.d(TAG, "request Header+++++++++: " + request.headers().toString());
        Log.d(TAG, "request url+++++++++: " + request.url().toString());

        Response response = chain.proceed(request);
//        SafelyResponseBody safelyResponseBody = new SafelyResponseBody(response.body());
//        String safelyResponse = safelyResponseBody.string();

        if (response.body() != null) {
            Log.d(TAG, "response+++++++++: " + response.body().string());
        }

        return response;
    }

    private void addRequestHeader(Request.Builder requestBuilder) {

        requestBuilder
                .addHeader("Host", requestBuilder.build().url().host())
                .addHeader("Connection", "Keep-Alive")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.5")
                .addHeader("Accept-Charset", "utf-8;q=0.7,*;q=0.7")
//                .addHeader("Accept-Encoding", "gzip")
                .addHeader("Content-Type", "application/json; charset=UTF-8")
                .addHeader("User-Agent", "E-Mobile/6.5.3 (Linux;U;Android 2.2.1;zh-CN;Nexus One Build.FRG83) AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1")
                .addHeader("Cookie", "userid=186; userKey=abc4vPejWNAIYblmR7CJv; ClientUDID=868362021581736; ClientToken=; ClientVer=6.5.3; ClientType=android; ClientLanguage=zh; ClientCountry=CN; ClientMobile=; setClientOS=HonorCHM-TL00H; setClientOSVer=4.4.2; Pad=false; JSESSIONID=abc4vPejWNAIYblmR7CJv")
                .addHeader("Cookie2", "$Version=1")
        ;
    }

    /**
     * 加密请求
     *
     * @param request
     * @return
     */
    private RequestBody encryptRequest(Request request) {
        // TODO: 16/12/8 加密
        return request.body();
    }
}
