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

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import top.shixinzhang.sxframework.network.third.okhttp3.Call;
import top.shixinzhang.sxframework.network.third.okhttp3.Cookie;
import top.shixinzhang.sxframework.network.third.okhttp3.CookieJar;
import top.shixinzhang.sxframework.network.third.okhttp3.HttpUrl;
import top.shixinzhang.sxframework.network.third.okhttp3.MediaType;
import top.shixinzhang.sxframework.network.third.okhttp3.OkHttpClient;
import top.shixinzhang.sxframework.network.third.okhttp3.Request;

/**
 * Description:
 * <br> OkHttpClient 的封装
 * <p>
 * <br> Created by shixinzhang on 17/5/27.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class SxHttpClient {

    private static final long OKHTTP_CACHE_SIZE = 10 * 1024 * 1024;
    public static final MediaType MEDIA_TYPE_MARKWODN = MediaType.parse("text/x-markdown; charset=utf-8");

    private volatile static OkHttpClient mClient;
    private Executor mMainThreadExecutor;
    private Call mCall;

    private SxHttpClient() {
    }

    private static OkHttpClient initOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new SxHttpInterceptor())
//                .addNetworkInterceptor(new StethoInterceptor())
//                .cache(new Cache(new File(Config.APP_FOLDER), OKHTTP_CACHE_SIZE))
                .hostnameVerifier(new SxHostnameVerifier())
//                .cookieJar(new SxCookieJar())
                .connectTimeout(10_000L, TimeUnit.MILLISECONDS)
                .readTimeout(10_000L, TimeUnit.MILLISECONDS)
                .build();
    }

    private static OkHttpClient getOkHttpClient() {
        if (mClient == null) {
            synchronized (SxHttpClient.class) {
                assert Thread.holdsLock(SxHttpClient.class);
                if (mClient == null) {
                    mClient = initOkHttpClient();
                }
            }
        }
        return mClient;
    }


    public static Call newCall(final Request request) {
        return getOkHttpClient().newCall(request);
    }

    /**
     * TLS/SSL 验证
     */
    private static class SxHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(final String hostname, final SSLSession session) {
            return true;
        }
    }

    private static class SxCookieJar implements CookieJar {
        @Override
        public void saveFromResponse(final HttpUrl url, final List<Cookie> cookies) {

        }

        @Override
        public List<Cookie> loadForRequest(final HttpUrl url) {
            return null;
        }
    }
}
